package de.fau.amos4.test;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.test.configuration.TestConfiguration;
import de.fau.amos4.util.TokenGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class EmployeeTest
{
    @Resource
    EmployeeRepository employeeRepository;

    @Resource
    ClientRepository clientRepository;

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
        // Add client to db
        Client newClient = new Client();
        newClient.setId(1l);
        newClient.setCompanyName("Test Company");
        newClient = clientRepository.save(newClient);

        // Add employee to db (with client reference)
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("firstname");
        newEmployee.setMaidenName("maidenname");
        newEmployee.setFamilyName("familyname");
        newEmployee.setBirthDate(Calendar.getInstance().getTime());
        newEmployee.setPlaceOfBirth("placeofbirth");
        newEmployee.setCountryOfBirth("countryofbirth");
        newEmployee.setStreet("street");
        newEmployee.setZipCode("1234");
        newEmployee.setHouseNumber("1234");
        newEmployee.setCity("city");
        newEmployee.setSocialInsuranceNumber("socialinsurance");
        newEmployee.setSex(Sex.UNKNOWN);
        newEmployee.setMaritalStatus(MaritalStatus.MARRIED);
        newEmployee.setDisabled(Disabled.NO);
        newEmployee.setCitizenship("citizenship");
        newEmployee.setEmployerSocialSavingsNumber("socialsavingsnumber");
        newEmployee.setIban("iban");
        newEmployee.setBic("bic");
        newEmployee.setAdditionToAddress("addition");
        newEmployee.setToken("token");
        newEmployee.setClient(newClient);
        newEmployee = employeeRepository.save(newEmployee);

        // Insert backreference
        List<Employee> employeeList = new ArrayList<>(1);
        employeeList.add(newEmployee);
        newClient.setEmployees(employeeList);
        clientRepository.save(newClient);

        Client c = clientRepository.findOne(1l);
        List<Employee> foundEmployees = employeeRepository.findByClient(c);

        Assert.assertEquals("Inserting a employee inserted more than one.", foundEmployees.size(), 1);
        Assert.assertEquals("Inserted employee is not same as one retrieved from client.",
                foundEmployees.get(0).getFirstName(), newEmployee.getFirstName());
        System.out.println(foundEmployees.size());
    }
}
