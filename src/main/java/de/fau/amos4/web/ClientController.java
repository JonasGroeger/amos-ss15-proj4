package de.fau.amos4.web;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ClientController(ClientService clientService, EmployeeRepository employeeRepository)
    {
        this.clientService = clientService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Show the client login page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value =  "/", method = RequestMethod.GET)
    public String getClientLoginPage() throws Exception
    {
        return "ClientLogin";
    }

    @RequestMapping(value = "/client/list")
    public ModelAndView getClientEmployeeList(@RequestParam(value = "id", defaultValue = "1") long clientId)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("AccountPage");

        Client client = clientService.getClientById(clientId);
        Iterable<Employee> clientsEmployees = employeeRepository.findByClient(client);

        mav.addObject("Employees", clientsEmployees);
        return mav;
    }
}
