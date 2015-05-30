package de.fau.amos4.web;

import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController
{
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TestController(EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testA")
    public String messageAdmin()
    {
        return "You are ADMIN.";
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @RequestMapping("/testC")
    public String messageClient()
    {
        return "You are CLIENT.";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @RequestMapping("/testE")
    public String messageEmployee()
    {
        return "You are EMPLOYEE.";
    }
}
