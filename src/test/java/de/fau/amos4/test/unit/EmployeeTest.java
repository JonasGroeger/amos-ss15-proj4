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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.test.BaseWebApplicationContextTests;
import de.fau.amos4.util.CheckDataInput;
import de.fau.amos4.util.TokenGenerator;

public class EmployeeTest extends BaseWebApplicationContextTests
{
    // Too long insurance number is rejected
    @Test
    public void test_TooLongInsuranceID_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setSocialInsuranceNumber("0123456789123");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("socialInsuranceNumber"));
    }

    // Correct insurance number is accepted
    @Test
    public void test_CorrectInsuranceID_Accepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setSocialInsuranceNumber("012345678912");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("socialInsuranceNumber"));
    }
    
    // Make sure that a valid house number is accepted (34b)
    @Test
    public void test_ValidHouseNumber_Accepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setHouseNumber("34b");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("houseNumber"));
    }

    // Make sure that an invalid house number is rejected (34b !!!)
    @Test
    public void test_InvalidHouseNumber_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setHouseNumber("34b !!!");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("houseNumber"));
    }
    
    // Make sure that a too long street value is not valid
    @Test
    public void test_TooLongStreeName_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setStreet("Arnold-von-Maximilian-Straße 193434/b 1. OG 23");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("street"));
    }

    // Make sure that a valid street is accepted
    @Test
    public void test_ValidStreet_Accepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setStreet("Werner-von-Siemens-Str. 2.");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("street"));
    }

    // Make sure that a street name with special chars is rejected
    @Test
    public void test_StreetWithKoreanChars_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setStreet("Werner-von-Siemens-Str.˘˘");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("street"));
    }
    

    // Make sure that a too long first name is not valid
    @Test
    public void testTooLongFirstNameIsInvalid() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("Alexander Anderson Anthony Benjamin Broderick Cameron Christopher Demetrius DeAndre Emerson");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("firstName"));
    }

    // Valid ZIP code is accepted
    @Test
    public void test_ValidPostcode_IsValid() throws Exception
    {
        Employee emp = new Employee();
        emp.setPostcode("91052"); // A valid ZIP code in Germany (Erlangen)
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("postcode"));
    }

    // Too long ZIP code is rejected
    @Test
    public void test_TooLongPostcode_IsInvalid() throws Exception
    {
        Employee emp = new Employee();
        emp.setPostcode("191052"); // A too long ZIP code.
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("postcode"));
    }

    // Too short ZIP code is rejected
    @Test
    public void test_TooShortPostcode_IsInvalid() throws Exception
    {
        Employee emp = new Employee();
        emp.setPostcode("9105"); // A too short ZIP code.
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("postcode"));
    }
    
    // ZIP code with letters is rejected
    @Test
    public void test_PostcodeWithLeters_IsInvalid() throws Exception
    {
        Employee emp = new Employee();
        emp.setPostcode("91o52"); // A ZIP code with letter (o)
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("postcode"));
    }
    
    // Make sure that a 31 char long first name is not valid
    @Test
    public void test_31CharsLongName_IsInvalid() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("abcdefghijabcdefghijabcdefghija"); // 31 chars
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("firstName"));
    }

    // Make sure that a 30 char long, valid first name is still valid
    @Test
    public void test_30CharsLongName_IsValid() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("abcdefghijabcdefghijabcdefghij"); // 30 chars
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("firstName"));
    }
    
    // Make sure that a fake, but correct name is accepted (Hans ßéáűőúöüóÖOÜÓÚŐŐÁÄä)
    @Test
    public void testCorrectFirstNameIsAccepted_2() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("Hans ßéáűőúöüóÖOÜÓÚŐŐÁÄä");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("firstName"));
    }
    
    // Make sure that a first name containing invalid characters is rejected
    @Test
    public void test_IncorrectFirstName_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("Jan +!");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("firstName"));
    }
    
    // Make sure that a first name containing numbers is rejected
    @Test
    public void test_FirstNameWithNumbers_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("Jan 314");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(InvalidFields.contains("firstName"));
    }
    
    // Make sure that a correct name is accepted 
    @Test
    public void testCorrectFirstNameIsAccepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setFirstName("Zoé");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("firstName"));
    }

    // Make sure that a correct city is accepted 
    @Test
    public void test_CorrectCity_Accepted_1() throws Exception
    {
        Employee emp = new Employee();
        emp.setCity("Auerbach in der Oberpfalz");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("city"));
    }
    
    // Make sure that a correct city is accepted 
    @Test
    public void test_CorrectCity_Accepted_2() throws Exception
    {
        Employee emp = new Employee();
        emp.setCity("Aßlar");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("city"));
    }
    
    // Make sure that a correct city is accepted 
    @Test
    public void test_CorrectCity_Accepted_3() throws Exception
    {
        Employee emp = new Employee();
        emp.setCity("Bad Frankenhausen/Kyffhäuser");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("city"));
    }
    
    // Make sure that a correct city is accepted 
    @Test
    public void test_CorrectCity_Accepted_4() throws Exception
    {
        Employee emp = new Employee();
        emp.setCity("Bitterfeld-Wolfen");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);
        
        Assert.assertTrue(!InvalidFields.contains("city"));
    }

    // Make sure that too long statutory health insurance is rejected
    @Test
    public void test_StatutoryHealthInsuranceTooLong_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setStatutoryHealthInsurance(123456789);

        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);

        Assert.assertTrue(InvalidFields.contains("statutoryHealthInsurance"));
    }

    // Make sure that too short statutory health insurance is rejected
    @Test
    public void test_StatutoryHealthInsuranceTooShort_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setStatutoryHealthInsurance(1234567);

        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);

        Assert.assertTrue(InvalidFields.contains("statutoryHealthInsurance"));
    }

    /*
    * Test Social insurance section
    */
    // Make sure that a correct statutory health insurance is accepted
    @Test
    public void test_CorrectStatutoryHealthInsurance_Accepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setStatutoryHealthInsurance(12345678);

        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);

        Assert.assertTrue(!InvalidFields.contains("statutoryHealthInsurance"));
    }

    // Make sure that too long accidentInsuranceRiskTariff is rejected
    @Test
    public void test_AccidentInsuranceRiskTariffTooLong_Rejected() throws Exception
    {
        Employee emp = new Employee();
        emp.setAccidentInsuranceRiskTariff("1234567890abc");

        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);

        Assert.assertTrue(InvalidFields.contains("accidentInsuranceRiskTariff"));
    }

    // Make sure that a correct statutory health insurance is accepted
    @Test
    public void test_CorrectAccidentInsuranceRiskTariff_Accepted() throws Exception
    {
        Employee emp = new Employee();
        emp.setAccidentInsuranceRiskTariff("ab0123456789");

        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(emp);

        Assert.assertTrue(!InvalidFields.contains("accidentInsuranceRiskTariff"));
    }
    
    // Make sure that the generated Token matches the format expectation. Should be a 6 chars long alphanumeric string.
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

    // Generates a few tokens and makes sure that each of them are unique.
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
