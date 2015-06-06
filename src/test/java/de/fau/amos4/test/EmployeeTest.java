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

    @Test
    public void testFindEmployeeByClient()
    {
        Client c = clientRepository.findOne(1l);
        List<Employee> foundEmployees = employeeRepository.findByClient(c);

        Assert.assertEquals("Inserting a employee inserted more than one.", foundEmployees.size(), 1);
    }
}
