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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.fau.amos4.annotation.TheClient;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.CurrentClient;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Title;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.service.TranslatorService;
import de.fau.amos4.util.EmailSender;
import de.fau.amos4.util.ZipGenerator;
import de.fau.amos4.web.form.ResetPasswordForm;
import net.lingala.zip4j.exception.ZipException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.fau.amos4.service.ClientRepository;

import java.util.List;

/**
 * Handles client related requests.
 */
@Controller
public class ClientController
{
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final TranslatorService translatorService;

    @Autowired
    public ClientController(ClientService clientService, ClientRepository clientRepository, EmployeeRepository employeeRepository, TranslatorService translatorService)
    {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.translatorService = translatorService;
    }

    /**
     * Show the client login page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value =  "/", method = RequestMethod.GET)
    public ModelAndView ClientLogin(@RequestParam(value = "m", required = false, defaultValue = "") String message,
                                    @TheClient CurrentClient client
    ) throws Exception
    {
        if (client != null) {
            return new ModelAndView("redirect:/client/dashboard");
        }

        return new ModelAndView("/client/login", "message", message);
    }

    @RequestMapping(value = "/client/dashboard")
    public ModelAndView ClientDashboard(@TheClient CurrentClient client)
    {
        final List<Employee> employees = employeeRepository.findByClient(client.getClient());
        return new ModelAndView("/client/dashboard", "Employees", employees);
    }

    @RequestMapping(value = "/client/profile")
    public ModelAndView ClientProfile(@TheClient CurrentClient client)
    {
        final Client clientFromDatabase = clientService.getClientByEmail(client.getUsername());
        return new ModelAndView("/client/profile", "Client", clientFromDatabase);
    }

    @RequestMapping(value = "/client/edit")
    public ModelAndView ClientEdit(@TheClient CurrentClient client)
    {
        final Client clientFromDatabase = clientService.getClientByEmail(client.getUsername());

        ModelAndView mav = new ModelAndView("/client/edit");
        mav.addObject("Client", clientFromDatabase);
        mav.addObject("allTitles", Title.values());
        return mav;
    }
    
    @RequestMapping("/client/edit/submit")
    public String ClientEditSubmit(HttpServletRequest request, @ModelAttribute(value = "client") Client client, @RequestParam("NewPassword") String NewPassword, @RequestParam("ConfirmPassword") String ConfirmPassword, @RequestParam("OldPassword") String OldPassword)
            throws MessagingException
    {
    	//get database object
        Client tmp = clientService.getClientByEmail(client.getEmail());
        
        //update password
        if (NewPassword.equals("") == false) {
        	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        if (NewPassword.equals(ConfirmPassword) && encoder.matches(OldPassword, tmp.getPasswordHash())) {
	        	//store as hash
	        	tmp.setPasswordHash(encoder.encode(NewPassword));
	        }
	        else {
	        	return "redirect:/client/edit?m=newPasswordFailed";
	        }
        }
        
        if (client.getZipPassword() != null) {
            tmp.setZipPassword(client.getZipPassword());
        }
        
        //update client information
        tmp.setOutputFormat(client.getOutputFormat());
        tmp.setTitle(client.getTitle());
        tmp.setFirstName(client.getFirstName());
        tmp.setFamilyName(client.getFamilyName());
        tmp.setBirthDate(client.getBirthDate()); //FIXME outputs null at the moment
        tmp.setOfficePhoneNumber(client.getOfficePhoneNumber());
        tmp.setMobilePhoneNumber(client.getMobilePhoneNumber());
        tmp.setCompanyName(client.getCompanyName());
        tmp.setCompanyType(client.getCompanyType());
        tmp.setCountry(client.getCountry());
        tmp.setAddress(client.getAddress());
        tmp.setZipCode(client.getZipCode());
        tmp.setBirthDate(client.getBirthDate());
        
        //write back to database
        clientRepository.save(tmp);
        
        return "redirect:/client/dashboard?m=profileChanged";
    }

    /**
     * Displays the "reset password" page.
     * @return The "reset password view".
     */
    @RequestMapping(value = "/client/forgotPassword", method = RequestMethod.GET)
    public ModelAndView getForgotPasswordPage()
    {
        return new ModelAndView("/client/forgotPassword");
    }

    /**
     * Resets the users password to a randomly generated one if a valid email has been provided.
     * @param resetPasswordForm The form the user has entered his "reset password" request.
     * @param errors If there were any errors during POST.
     * @return Redirects to the "reset password" page on error. Else, redirects to the login page.
     */
    @RequestMapping(value = "/client/forgotPassword", method = RequestMethod.POST)
    public ModelAndView postForgotPasswordPage(@Valid @ModelAttribute final ResetPasswordForm resetPasswordForm,
                                               final BindingResult errors)
    {
        if(errors.hasErrors()) {
            final ModelAndView mav = new ModelAndView("/client/forgotPassword");
            mav.addObject("error", translatorService.translate("client.resetpassword.error"));
            return mav;
        }

        final Client client = clientService.getClientByEmail(resetPasswordForm.getEmail());
        if(client == null) {
            ModelAndView mav = new ModelAndView("/client/forgotPassword");
            mav.addObject("error", translatorService.translate("client.resetpassword.noclient"));
            return mav;
        }

        // Actually change the clients password to a random one
        clientService.generateNewPassword(client);

        return new ModelAndView("/client/login");
    }

    @RequestMapping(value = "/employee/email/send")
    public ModelAndView EmployeeEmailSend(@RequestParam(value = "id") long id,
                                          @RequestParam(value = "to") String to,
                                          @TheClient CurrentClient client
    ) throws NoSuchMessageException, COSVisitorException, ZipException, IOException, CloneNotSupportedException,
                     MessagingException
    {
        ModelAndView mav = new ModelAndView();

        Employee employee = employeeRepository.findOne(id);

        Client currentClient = clientService.getClientByEmail(client.getUsername());

        int fontSize=12;
        float height= 1;
        height = height*fontSize*1.05f;

        Locale locale = LocaleContextHolder.getLocale();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ZipGenerator zipGenerator = new ZipGenerator();

        zipGenerator.generate(out, locale, height, employee, fontSize, currentClient.getZipPassword());

        String filename = "employee.zip";
        String subject = filename + "_" + currentClient.getCompanyName() + "_" + employee.getFirstName() + "," + employee.getFamilyName();
        String emailContent = "This email is sent by " + currentClient.getEmail() + " via Personalfragebogen 2.0";
        EmailSender sender = new EmailSender();
        sender.SendEmail(to, subject, emailContent, out.toByteArray(), filename);

        mav.setViewName("redirect:/client/dashboard");
        return mav;
    }
}
