/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fau.amos4.web;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Denomination;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.service.EmployeeService;
import de.fau.amos4.util.TokenGenerator;

@Controller
public class EmployeeFormController
{
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    /*
    Constructor called at program start.
     */
    @Autowired
    public EmployeeFormController(EmployeeRepository employeeRepository, ClientRepository clientRepository, ClientService clientService, EmployeeService employeeService)
    {
    	this.clientService = clientService;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.employeeService = employeeService;
    }

    /*
    EmployeeEdit handles employee/edit.html
    It is invoked by the edit button in the client/dashboard.html
    The client can edit the prefilled fields of one respective employee entry in the dashboard.
     */
    @RequestMapping("/employee/edit")
    public ModelAndView EmployeeEdit(HttpServletResponse response, @RequestParam(value = "id") long employeeId, Principal principal, Model model) throws IOException
    {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("employee/edit");
        Employee employee = employeeRepository.findOne(employeeId);
        mav.addObject("id", employeeId);
        mav.addObject("employee", employee);
        mav.addObject("allDisabled", Disabled.values());
        mav.addObject("allMarital", MaritalStatus.values());
        mav.addObject("allSex", Sex.values());
        mav.addObject("allDenomination", Denomination.values());
        return mav;
    }
    /*
    EmployeeEditSubmit is invoked by the submit button in the employee/edit.html page.
    Changes made there are stored in the database and the client gets redirected to client/dashboard.html.
     */
    @RequestMapping("/employee/edit/submit")
    public String EmployeeEditSubmit(Employee employee,Principal principal, Model model)
    {

        if (principal == null) {
            System.out.println("null");
            model.addAttribute("allDisabled", Disabled.values());
            model.addAttribute("allMarital", MaritalStatus.values());
            model.addAttribute("allSex", Sex.values());
            model.addAttribute("allDenomination", Denomination.values());
            return "/employee/preview";
        } else {
            final String currentUser = principal.getName();
            System.out.println("not null");
            Client client = clientService.getClientByEmail(currentUser);
            employee.setClient(client);
            client.getEmployees().add(employee);

            employeeRepository.save(employee);
            clientRepository.save(client);

            // Redirect to AccountPage page
            return "redirect:/client/dashboard";
        }
    }

    /*
    EmployeeTokenSubmit is invoked on click of the submit botton on employee/token.html
    If the token is valid, the employee gets redirected to the prefilled employee/form.html to make changes on
    the data associated with the token.

    If the token is invalid, an error message shows up on the screen.

    After a token is used, it becomes invalid.
     */
    @RequestMapping("/employee/token/submit")
    public ModelAndView EmployeeTokenSubmit(HttpServletResponse response, @RequestParam(value = "token", required = true) String token, Model model) throws IOException
    {
    	long employeeId = 0;
    	Iterable<Employee> allEmployees = employeeRepository.findAll();
        for (Iterator<Employee> i = allEmployees.iterator(); i.hasNext(); ) {
        	Employee currEmployee = i.next();
        	if( currEmployee.getToken().equals(token)) {
        		employeeId = currEmployee.getId();
        	}
        	
        }
        ModelAndView mav = new ModelAndView();
        if (employeeId != 0) {
	        
	        mav.setViewName("employee/edit");
	        Employee employee = employeeRepository.findOne(employeeId);
	        mav.addObject("id", employeeId);
	        mav.addObject("employee", employee);
	        mav.addObject("allDisabled", Disabled.values());
	        mav.addObject("allMarital", MaritalStatus.values());
	        mav.addObject("allSex", Sex.values());
            mav.addObject("allDenomination", Denomination.values());
        } else {
            mav.addObject("m", "invalid");
        	mav.setViewName("employee/token");
        }
        return mav;
        	
    }

    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
    }


    /*
    EmployeeConfirm handles employee/confirm.html
    Invoked by: Submit button in employee/preview.html

    Apart from providing the opportunity to download the data as text file or pdf, the
    previously entered data is stored in the database.
     */
    @RequestMapping("/employee/confirm")
    public String EmployeeConfirm(@ModelAttribute("employee") Employee employee,
                                 BindingResult result, Model model) throws Exception
    {

        Employee e = employeeService.getEmployeeByToken(employee.getToken());
        Client client = e.getClient();
        employee.setClient(client);
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        employee.setToken(token);
        client.getEmployees().add(employee);
        clientRepository.save(client);

        // If the employee is new: Create
        // If the employee already has a primary key: Update
        Employee newOrUpdatedEmployee = employeeRepository.save(employee);

        // Setup model and return view
        model.addAttribute("EmployeeId", newOrUpdatedEmployee.getId());
        return "employee/confirm";
    }

    // Exception handling - Display exception information
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception)
    {
        // TODO: Add logging!
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        // TODO: Implement stacktrace display function.

        return mav;
    }

    /*
    Invoked by: Delete button on client/dashboard.html

    The respective employee is deleted in the database, recovery is not possible
     */
    @RequestMapping("/employee/delete")
    public String EmployeeDelete(@RequestParam(value = "id", required = true) long employeeId)
    {
        //TODO: Security, allow deletion only for employees assigned to respective client.
        // Remove employee with passed id
        this.employeeRepository.delete(employeeId);

        // Redirect to AccountPage page
        return "redirect:/client/dashboard";
    }

    /*
    Invoked by: New Employee link on client/dashboard.html

    Creates a new employee in the database.
     */
    @RequestMapping("/employee/new")
    public String EmployeeNew(Principal principal)
    {
        // Create a new employee with default name
        Employee employee = new Employee();
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);

        Locale locale = LocaleContextHolder.getLocale();
        String newEmployee = AppContext.getApplicationContext().getMessage("EmployeeFormController.newEmployee", null, locale);
        employee.setFamilyName(newEmployee);
        employee.setClient(client);
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        employee.setToken(token);
        client.getEmployees().add(employee);

        employeeRepository.save(employee);
        clientRepository.save(client);


        // Redirect to AccountPage page
        return "redirect:/client/dashboard";
    }

    @RequestMapping({"/employee/token", "/FrontPage"}) //FrontPage mapping is required by user story TODO ask PO's about this?
    public String EmployeeToken(Model model) throws Exception
    {
        return "employee/token";
    }
}
