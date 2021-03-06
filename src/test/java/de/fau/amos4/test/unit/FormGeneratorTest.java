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
 *
 * Created by Yao Bochao on 29/06/2015.
 */

package de.fau.amos4.test.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.fau.amos4.model.Form;
import de.fau.amos4.model.FormField;
import de.fau.amos4.model.FormGroup;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.util.FieldOrder;
import de.fau.amos4.util.FormGenerator;
import de.fau.amos4.util.GroupName;

public class FormGeneratorTest {
    
    // Dummy class - used to test FormGenerator class
    public class DummyClass
    {
        @GroupName("Integers")
        @FieldOrder(1.0f)
        int data = 1;

        @GroupName("Strings")
        @FieldOrder(2.0f)
        String name2 = "DummyName";
        
        @GroupName("Strings")
        @FieldOrder(1.0f)
        String name = "DummyName";

        @GroupName("Strings")
        @FieldOrder(3.0f)
        String name3 = "DummyName";

        @GroupName("Enums")
        @FieldOrder(1.0f)
        MaritalStatus marital;
        
        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public String getName2() {
            return name;
        }

        public void setName2(String name) {
            this.name = name;
        }
        public String getName3() {
            return name;
        }

        public void setName3(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MaritalStatus getMarital() {
            return marital;
        }

        public void setMarital(MaritalStatus marital) {
            this.marital = marital;
        }
    }

    // Make sure that the String fields are ordered based on their Order Annotation
    @Test
    public void DummyClass_StringsFieldsAreInTheCorrectOrder() throws Exception
    {
        FormGenerator generator = new FormGenerator();
        
        // Generate form data from class description
        Form form = generator.Generate(DummyClass.class, new DummyClass());
        
        FormGroup stringGroup = null;
        for(FormGroup f : form.getGroups())
        {
            if(f.getName().equals("Strings"))
            {
            stringGroup = f;
            }
        }
        
        String FirstFieldName = stringGroup.getFields().get(0).getName();
        String SecondFieldName = stringGroup.getFields().get(1).getName();
        String ThirdFieldName = stringGroup.getFields().get(2).getName();

        Assert.assertEquals("name", FirstFieldName);
        Assert.assertEquals("name2", SecondFieldName);
        Assert.assertEquals("name3", ThirdFieldName);
    }
 
    // Make sure that both 'Integers' and 'Strings' groups are found in the dummy class
    @Test
    public void DummyClass_HasTwoGroups() throws Exception
    {
        FormGenerator generator = new FormGenerator();
        
        // Generate form data from class description
        Form form = generator.Generate(DummyClass.class, new DummyClass());
        
        // Collect all the group names found
        List<String> groupsFound = new ArrayList<String>();
        for(FormGroup group : form.getGroups())
        {
            groupsFound.add(group.getName());
        }
        
        Assert.assertTrue(groupsFound.contains("Integers"));
        Assert.assertTrue(groupsFound.contains("Strings"));
    }

    // Make sure that both 'data' and 'name' fields are found in the dummy class. 
    @Test
    public void DummyClass_HasBothFields() throws Exception
    {
        FormGenerator generator = new FormGenerator();
        
        // Generate form data from class description
        Form form = generator.Generate(DummyClass.class, new DummyClass());
        
        // Collect all the field names found
        List<String> fieldsFound = new ArrayList<String>();
        for(FormGroup group : form.getGroups())
        {
            for(FormField f : group.getFields())
            {
                fieldsFound.add(f.getName());
            }
        }

        Assert.assertTrue(fieldsFound.contains("data"));
        Assert.assertTrue(fieldsFound.contains("name"));
    }
    
    // Make sure that enum options are properly loaded. 
    @Test
    public void DummyClass_EnumOptionsAreLoaded() throws Exception
    {
        FormGenerator generator = new FormGenerator();
        
        // Generate form data from class description
        Form form = generator.Generate(DummyClass.class, new DummyClass());
        
        // Collect all the field names found
        List<String> enumOptionsFound = new ArrayList<String>();
        for(FormGroup group : form.getGroups())
        {
            for(FormField f : group.getFields())
            {
                List<String> options = f.getOptions();
                if(options != null)
                {
                    // options is not null, it is an enum.
                    enumOptionsFound.addAll(options);
                }
            }
        }
        
        Assert.assertTrue(enumOptionsFound.size() > 0);
    }
}
