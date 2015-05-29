package de.fau.amos4.web;

import de.fau.amos4.model.Client;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/test")
    public String message()
    {
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        Iterable<Client> allClients = clientRepository.findAll();
        return String.valueOf(token);
    }
}
