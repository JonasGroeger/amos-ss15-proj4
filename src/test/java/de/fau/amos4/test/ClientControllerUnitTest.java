package de.fau.amos4.test;

import java.security.Principal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.sun.security.auth.UserPrincipal;

import de.fau.amos4.model.Employee;
import de.fau.amos4.web.ClientController;

public class ClientControllerUnitTest extends BaseWebApplicationContextTests
{
    /*
     * Test client dashboard: Make sure that each Employee displayed in the dashboard really belongs to the actual client.
     */
    @Test
    public void clientDashboard_OnlyClientsEmployeesAreDisplayrd() throws Exception
    {
        // Instantiate the controller
        ClientController clientController = new ClientController(this.clientService, this.employeeRepository);
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
}
