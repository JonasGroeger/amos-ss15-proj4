package de.fau.amos4.test.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.fau.amos4.model.*;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.util.*;

public class FormGeneratorTest {
    
    // Dummy class - used to test FormGenerator class
    class DummyClass
    {
        @GroupName("Integers")
        int data;
        
        @GroupName("Strings")
        String name;

        @GroupName("Enums")
        MaritalStatus marital;
    }

    // Make sure that both 'Integers' and 'Strings' groups are found in the dummy class
    @Test
    public void DummyClass_HasTwoGroups() throws Exception
    {
        FormGenerator generator = new FormGenerator();
        
        // Generate form data from class description
        Form form = generator.Generate(DummyClass.class);
        
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
        Form form = generator.Generate(DummyClass.class);
        
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
        Form form = generator.Generate(DummyClass.class);
        
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
