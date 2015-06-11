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

import java.security.Principal;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.EmailSender;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles client related requests.
 */
@Controller
public class ClientController
{
    private final ClientService clientService;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientService clientService, EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Show the client login page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value =  "/", method = RequestMethod.GET)
    public ModelAndView ClientLogin(@RequestParam(value = "m", required=false, defaultValue = "")String message) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        // Display the default login screen
        mav.setViewName("client/login");
        // Set the message to be displayed (invalid login, confirm success, confirm fail, registration done)
        mav.addObject("message", message);
    	
        return mav;
    }

    @RequestMapping(value = "/client/dashboard")
    public ModelAndView ClientDashboard(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("client/dashboard");
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        Iterable<Employee> clientsEmployees = employeeRepository.findByClient(client);

        mav.addObject("Employees", clientsEmployees);
        return mav;
    }
    
    @RequestMapping(value = "/client/profile")
    public ModelAndView ClientProfile(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        mav.addObject("Client", client);
        
        mav.setViewName("client/profile");
        return mav;
    }
    
    @RequestMapping(value = "/client/edit")
    public ModelAndView ClientEdit(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        mav.addObject("Client", client);
        
        mav.setViewName("client/edit");
        return mav;
    }
    
    @RequestMapping(value = "/client/forgotPassword")
    public ModelAndView ClientForgotPassword(@RequestParam(value="email", required= false, defaultValue = "")String email) throws AddressException, MessagingException
    {
        ModelAndView mav = new ModelAndView();
        
        if(!email.equals(""))
        {
            // Create new password for user
            Client client = clientService.getClientByEmail(email);
            String newRandomPassword = RandomStringUtils.random(8, "ABCDEFGHJKLMNPQRSTUVWXYZ23456789");
            String newPasswordHash = new BCryptPasswordEncoder().encode(newRandomPassword);
            client.setPasswordHash(newPasswordHash);
            
            EmailSender Sender = new EmailSender();
            Sender.SendEmail(client.getEmail(), "PersonalFragebogen 2.0", "Your new password is:" + newRandomPassword);
            clientRepository.save(client);
            mav.setViewName("/client/login");
        }
        else
        {
            mav.setViewName("/client/forgotPassword");
        }
        return mav;
    }
}
