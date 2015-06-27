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
import de.fau.amos4.util.ValidFormat;

public class CheckDataInputTest
{
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

    @Test
    public void objectIsNotValid() throws Exception
    {
        TestClass o = new TestClass();
        o.setData("NotValidContent !!!");
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(o);
        Boolean IsDataFieldInvalid = InvalidFields.contains("Data");
        Assert.assertTrue(IsDataFieldInvalid);
    }

    @Test
    public void objectIsHavingEmptyField() throws Exception
    {
        TestClass o = new TestClass();
        o.setData("");
        CheckDataInput cdi = new CheckDataInput();
        List<String> emptyFields = cdi.listEmptyFields(o);
        Boolean IsDataFieldInvalid = emptyFields.contains("Data");
        Assert.assertTrue(IsDataFieldInvalid);
    }
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
    
    
    @Test
    public void objectIsValid() throws Exception
    {
        TestClass o = new TestClass();
        o.setData("OnlyValidContent123412Nospecialchars");
        CheckDataInput cdi = new CheckDataInput();
        List<String> InvalidFields = cdi.listInvalidFields(o);
        Boolean IsDataFieldValid = !InvalidFields.contains("Data");
        Assert.assertTrue(IsDataFieldValid);
    }
}
