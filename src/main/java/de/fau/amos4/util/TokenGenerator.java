package de.fau.amos4.util;

import de.fau.amos4.model.Employee;
import de.fau.amos4.service.EmployeeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayDeque;
import java.util.Deque;

@Configuration
public class TokenGenerator
{
    private final String TOKEN_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private final int TOKEN_LENGTH = 6;

    private final int TOKEN_BUFFER_CAPACITY = 64;
    private final Deque<String> TOKEN_BUFFER = new ArrayDeque<>(TOKEN_BUFFER_CAPACITY);

    private static TokenGenerator instance;

    public static synchronized TokenGenerator getInstance()
    {
        if(instance == null)
        {
            instance = new TokenGenerator();
        }
        return instance;
    }

    private String createToken()
    {
        return RandomStringUtils.random(TOKEN_LENGTH, TOKEN_CHARACTERS);
    }

    private void ensureTokensAvailable()
    {
        if(TOKEN_BUFFER.size() < 1)
        {
            for (int i = 0; i < TOKEN_BUFFER_CAPACITY; i++) {
                TOKEN_BUFFER.add(createToken());
            }
        }
    }

    public synchronized String createUniqueToken(EmployeeRepository employeeRepository)
    {
        this.ensureTokensAvailable();

        Iterable<Employee> allEmployees = employeeRepository.findAll();

        boolean foundUniqueToken;
        String potentialToken;
        do
        {
            ensureTokensAvailable();

            potentialToken = TOKEN_BUFFER.pop();
            foundUniqueToken = true; // Assume its valid

            // If we find an employee with this token -> try again
            for(Employee emp: allEmployees)
            {
                if(emp.getToken() == null) continue;
                if(emp.getToken().equals(potentialToken))
                {
                    foundUniqueToken = false;
                }
            }
        } while(!foundUniqueToken);

        return potentialToken;
    }

}
