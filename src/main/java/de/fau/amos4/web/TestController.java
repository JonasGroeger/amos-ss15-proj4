package de.fau.amos4.web;

import de.fau.amos4.domain.EmployeeRepository;
import de.fau.amos4.util.TokenGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestController
{
    @Resource
    EmployeeRepository employeeRepository;

    @RequestMapping("/test")
    public String message()
    {
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        return String.valueOf(token);
    }
}
