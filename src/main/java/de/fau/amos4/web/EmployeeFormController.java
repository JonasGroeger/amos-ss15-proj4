package de.fau.amos4.web;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.model.fields.Title;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.StringUtils;
import de.fau.amos4.util.TokenGenerator;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Controller
public class EmployeeFormController
{
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public EmployeeFormController(EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    // Employee data form - Enter Employee data
    @RequestMapping("/EmployeeForm")
    public String EmployeeForm(Model model) throws Exception
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMarital", MaritalStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeeForm";
    }

    @RequestMapping("/EmployeeEdit")
    public ModelAndView EmployeeEdit(HttpServletResponse response, @RequestParam(value = "id") long employeeId, Model model) throws IOException
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeEdit");
        Employee employee = employeeRepository.findOne(employeeId);
        mav.addObject("id", employeeId);
        mav.addObject("employee", employee);
        mav.addObject("allDisabled", Disabled.values());
        mav.addObject("allMarital", MaritalStatus.values());
        mav.addObject("allSex", Sex.values());
        return mav;
    }

    @RequestMapping("/EditSubmit")
    public String EditSubmit(Employee employee, Model model)
    {
        Client client = clientRepository.findOne(1l);
        employee.setClient(client);
        client.getEmployees().add(employee);

        employeeRepository.save(employee);
        clientRepository.save(client);

        // Redirect to AccountPage page
        return "redirect:/AccountPage";
    }
    
    @RequestMapping("/FrontPageSubmit")
    public ModelAndView FrontPageSubmit(HttpServletResponse response, @RequestParam(value = "token", required = true) String token, Model model) throws IOException
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
	        
	        mav.setViewName("EmployeeForm");
	        Employee employee = employeeRepository.findOne(employeeId);
	        mav.addObject("id", employeeId);
	        mav.addObject("employee", employee);
	        mav.addObject("allDisabled", Disabled.values());
	        mav.addObject("allMarital", MaritalStatus.values());
	        mav.addObject("allSex", Sex.values());
        } else {
        	mav.setViewName("WrongToken");
        }
        return mav;
        	
    }
    
    @RequestMapping("/WrongToken")
    public String EmployeeLogin()
    {
        return "WrongToken";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
    }

    // Employee data preview - Review Employee data
    @RequestMapping(value = "/EmployeePreview", method = {RequestMethod.POST, RequestMethod.GET})
    public String EmployeePreview(@ModelAttribute("employee") Employee employee, BindingResult result, Model model)
    {
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMarital", MaritalStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeePreview";
    }

    // Employee data submit - Submit Employee data
    @RequestMapping("/EmployeeSubmit")
    public String EmployeeSubmit(@ModelAttribute("employee") Employee employee,
                                 BindingResult result, Model model) throws Exception
    {
        Client client = clientRepository.findOne(1l);
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
        return "EmployeeSubmit";
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


    @RequestMapping("/EmployeeDelete")
    public String EmployeeDelete(@RequestParam(value = "id", required = true) long employeeId)
    {
        // Remove employee with passed id
        this.employeeRepository.delete(employeeId);

        // Redirect to AccountPage page
        return "redirect:/AccountPage";
    }

    @RequestMapping("/NewEmployee")
    public String NewEmployee()
    {
        // Create a new employee with default name
        Employee employee = new Employee();
        Client client = clientRepository.findOne(1l);

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
        return "redirect:/AccountPage";
    }

    @RequestMapping("/AccountPage")
    public ModelAndView AccountPage(@RequestParam(value = "id", defaultValue = "1") long clientId)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("AccountPage");

        Client client = clientRepository.findOne(clientId);
        Iterable<Employee> clientsEmployees = employeeRepository.findByClient(client);

        mav.addObject("Employees", clientsEmployees);
        return mav;
    }

    @RequestMapping("/FrontPage")
    public String FrontPage(Model model) throws Exception
    {
        return "FrontPage";
    }
}
