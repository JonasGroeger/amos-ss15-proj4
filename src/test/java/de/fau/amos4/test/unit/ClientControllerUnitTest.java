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
package de.fau.amos4.test.unit;

import com.sun.security.auth.UserPrincipal;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.test.BaseWebApplicationContextTests;
import de.fau.amos4.web.ClientController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public class ClientControllerUnitTest extends BaseWebApplicationContextTests
{
    /*
     * Test client dashboard: Make sure that each Employee displayed in the dashboard really belongs to the actual client.
     */
    @Test
    public void clientDashboard_OnlyClientsEmployeesAreDisplayrd() throws Exception
    {
        // Instantiate the controller
        ClientController clientController = new ClientController(this.clientService, this.employeeRepository, clientRepository);
        // Get a client's username
        String UserName = this.clientService.getClientById(1l).getEmail();
        
        // Get the List
        Principal principal = new UserPrincipal(UserName);
        ModelAndView mav = clientController.ClientDashboard(principal);
        
        // Model should contain only employees with this client.
        Iterable<Employee> employees = (Iterable<Employee>)mav.getModel().get("Employees");
        for(Employee employee : employees)
        {
            // Make sure that each Employee belongs to the current client.
            Assert.assertEquals(UserName, employee.getClient().getEmail());
        }
    }
    
    /*
     * Test client dashboard: Make sure that a newly added employee is also displayed.
     * After adding a new employee to the client, then the number of displayed employees
     * should be higher.
     */
    @Test
    public void clientDashboard_addedNewEmployeeIsAlsoListed() throws Exception
    {
        // Instantiate the controller
        ClientController clientController = new ClientController(this.clientService, this.employeeRepository, this.clientRepository);
        // Get a client's username
        Client client = this.clientService.getClientById(1l);
        String UserName = client.getEmail();
        
        // Get the List
        Principal principal = new UserPrincipal(UserName);
        ModelAndView mav = clientController.ClientDashboard(principal);
        
        // Model should contain only employees with this client.
        Iterable<Employee> employees = (Iterable<Employee>)mav.getModel().get("Employees");
        
        // Count original employee count
        int originalEmployeeCount = 0;
        for(Employee employee : employees)
        {
            originalEmployeeCount++;
        }
        
        // Add new employee to the client
        Employee newEmployee = new Employee();
        newEmployee.setClient(client);
        employeeRepository.save(newEmployee);
        
        // Get new employee list
        mav = clientController.ClientDashboard(principal);
        
        // Model should contain only employees with this client.
        employees = (Iterable<Employee>)mav.getModel().get("Employees");
        
        // Count new employee count
        int newEmployeeCount = 0;
        for(Employee employee : employees)
        {
            newEmployeeCount++;
        }
        
        Assert.assertEquals(originalEmployeeCount + 1, newEmployeeCount);
    }
}
