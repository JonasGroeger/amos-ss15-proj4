package de.fau.amos4.test;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.util.TokenGenerator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EmployeeTest extends BaseWebApplicationContextTests
{

    @Test
    public void generatedTokenIsInCorrectFormat() throws Exception
    {
        // Create Employee
        Employee emp = new Employee();
        emp.setToken(TokenGenerator.getInstance().createUniqueToken(employeeRepository));
        employeeRepository.save(emp);

        // Make sure that the Token is a 6 char long alphanumeric string.
        String generatedToken = emp.getToken();
        // Is the Token 6 char long?
        Assert.assertEquals(6, generatedToken.length());
        // Is the Token alphanumeric?
        Pattern p = Pattern.compile("([A-Z]|[0-9]){6}");
        boolean hasCorrectFormat = p.matcher(generatedToken).matches();
        Assert.assertTrue(hasCorrectFormat);
    }

    @Test
    public void generatedTokensAreUnique() throws Exception
    {
        List<String> generatedTokens = new ArrayList<>();

        // Generate 10 Tokens -> Each of them should be unique.
        for (int i = 0; i < 10; i++) {
            String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
            generatedTokens.add(token);
        }

        // Check if they are unique: Add to set and compare size.
        Set<String> tokenSet = new HashSet<>(generatedTokens);
        if(tokenSet.size() < generatedTokens.size()){
            Assert.fail("There is at least a duplicate in the tokens.");
        }
    }

    /*
     * Employee test: Newly added employee can be retrieved from it's client. 
     */
    @Test
    public void testFindEmployeeByClient()
    {
        // Get a client
        Client client = clientRepository.findOne(1l);
        
        // Add a new employee to the client
        Employee employee = new Employee();
        employee.setClient(client);
        employeeRepository.save(employee);
        
        // Retrieve employees for this client
        List<Employee> foundEmployees = employeeRepository.findByClient(client);
        
        // Make sure that at least one employee is returned. (One has just been added.)
        Assert.assertTrue(foundEmployees.size() > 0);
    }
}
