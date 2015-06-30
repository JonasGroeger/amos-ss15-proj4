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

import de.fau.amos4.model.Employee;
import de.fau.amos4.util.CheckDataInput;
import de.fau.amos4.util.ValidFormat;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class CheckDataInputTest
{
    // Dummy class, used to test data validation.
    public class TestClass
    {
        @ValidFormat("^[0-9a-zA-Z]*$")
        private String Data;
        
        public String getData() {
            return Data;
        }

        public void setData(String data) {
            Data = data;
        }
    }

    // When data field contains invalid characters, based on ValidFormat annotation, it should be detected by the listInvalidFields method.
    @Test
    public void objectIsNotValid() throws Exception
    {
        TestClass o = new TestClass();
        CheckDataInput cdi = new CheckDataInput();
        o.setData("NotValidContent !!!");
        
        List<String> InvalidFields = cdi.listInvalidFields(o);
        InvalidFields = cdi.listInvalidFields(o);
        Boolean IsDataFieldInvalid = InvalidFields.contains("Data");
        Assert.assertTrue(IsDataFieldInvalid);
    }
    
    @Test
    public void employeeIsNotValid() throws Exception
    {
        List<String> InvalidFields;
        CheckDataInput cdi = new CheckDataInput();
        Employee e = new Employee();
        
        e.setPersonnelNumber(666666); //5
        e.setFirstName("ThisFirstWithNumber30!");
        e.setMaidenName("ThisMaidenWithNumber30!");
        e.setFamilyName("ThisFamilyWithNumber30!");
        e.setPlaceOfBirth("asdf(<&");
        //e.setCountryOfBirth("");
        e.setStreet("This?*!@#$");
        e.setZipCode("123456%");
        e.setHouseNumber("1010101010!!");
        e.setCity("ThisCity<>()@");
        e.setSocialInsuranceNumber("12345678910x!"); //12-stellig
        e.setCitizenship(""); //?
        e.setEmployerSocialSavingsNumber(""); //?
        /*
         * 
         Not yet implemented (RegExes are missing from class.)
         e.setIban("4444!");
         
         e.setBic("123456789102_"); //5 or 11
         e.setAdditionToAddress(""); //?
         e.setTaxOfficeNumber(123); //4
         e.setIdentificationNumber(123456789011l); //11
         e.setTaxClass(13); //1
         e.setFactor(0.0f);
         e.setNumberOfExemptionsForChildren(100);
         e.setBirthDate(new Date(2011, 2, 31));
        
        */

        Boolean IsFieldInvalid;
        InvalidFields = cdi.listInvalidFields(e);
        IsFieldInvalid = InvalidFields.contains("familyName");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("personnelNumber");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("firstName");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("maidenName");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("placeOfBirth");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("street");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("zipCode");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("houseNumber");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("city");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("socialInsuranceNumber");
        Assert.assertTrue(IsFieldInvalid);
        /* Not yet implemented. Regexes are missing from Employee class.
        IsFieldInvalid = InvalidFields.contains("iban");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("bic");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("taxOfficeNumber");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("taxClass");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("factor");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("numberOfExemptionsForChildren");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("birthDate");
        Assert.assertTrue(IsFieldInvalid);
        IsFieldInvalid = InvalidFields.contains("identificationNumber");
        Assert.assertTrue(IsFieldInvalid);
        */
    }@Test
    public void employeeIsValid() throws Exception
    {
        List<String> InvalidFields;
        CheckDataInput cdi = new CheckDataInput();
        Employee e = new Employee();
        
        e.setFirstName("John");
        e.setFamilyName("Small");
        e.setPlaceOfBirth("Erlangen");
        //e.setCountryOfBirth("");
        e.setZipCode("90521");
        e.setHouseNumber("2");
        e.setCity("Erlangen");
        e.setSocialInsuranceNumber("12345678910"); //12-stellig
        e.setCitizenship("German"); //?
        e.setEmployerSocialSavingsNumber("123452"); //?
        /*
         * 
         Not yet implemented (RegExes are missing from class.)
         e.setIban("4444!");
         
         e.setBic("123456789102_"); //5 or 11
         e.setAdditionToAddress(""); //?
         e.setTaxOfficeNumber(123); //4
         e.setIdentificationNumber(123456789011l); //11
         e.setTaxClass(13); //1
         e.setFactor(0.0f);
         e.setNumberOfExemptionsForChildren(100);
         e.setBirthDate(new Date(2011, 2, 31));
        
        */


        List<String> listInvalidFields = cdi.listInvalidFields(e);
        List<String> listEmptyFields = cdi.listEmptyFields(e);
        listInvalidFields.removeAll(listEmptyFields);
		Boolean isObjectValid = listInvalidFields.size() == 0;
        Assert.assertTrue(isObjectValid);
    }
    
    // When a data field is empty, and has the ValidFormat annotation, it should be detected by the listEmptyFields method.
    @Test
    public void objectIsHavingEmptyField() throws Exception {
        TestClass o = new TestClass();
        o.setData("");
        
        CheckDataInput cdi = new CheckDataInput();
        List<String> emptyFields = cdi.listEmptyFields(o);
        
        Assert.assertTrue(emptyFields.size() > 0);
    }
    
    @Test
    public void employeeIsHavingEmptyField() throws Exception {
        Employee e = new Employee();
        e.setId(0);
        e.setToken("");
        e.setPersonnelNumber(1);
        e.setFirstName("");
        e.setMaidenName("");
        e.setFamilyName("");
        e.setBirthDate(new Date());
        e.setNumberOfExemptionsForChildren(0);
        CheckDataInput cdi = new CheckDataInput();
        List<String> emptyFields = cdi.listEmptyFields(e);

        Boolean IsDataFieldEmpty;
        IsDataFieldEmpty = emptyFields.size() > 0;
        Assert.assertTrue(IsDataFieldEmpty);
    }
    
    // When an object's field has some value set, then it should not be listed by the listEmptyFields method.
    @Test
    public void objectIsHavingNoEmptyField() throws Exception
    {
        TestClass o = new TestClass();
        o.setData("SomeData");

        CheckDataInput cdi = new CheckDataInput();
        List<String> emptyFields = cdi.listEmptyFields(o);
        Boolean IsDataFieldInvalid = !emptyFields.contains("Data");
        Assert.assertTrue(IsDataFieldInvalid);
    }
    
    // When a field is valid based on the ValidFormat annotations it has, the field should not be listed by listInvalidFields method. 
    @Test
    public void objectIsValid() throws Exception
    {
        TestClass o = new TestClass();
        o.setData("OnlyValidContent123412Nospecialchars");
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(o);
        Boolean IsDataFieldValid = !InvalidFields.contains("Data");
        Assert.assertTrue(IsDataFieldValid);
        InvalidFields = cdi.listInvalidFields(o);
        Assert.assertTrue(InvalidFields.size() == 0);
    }
}
