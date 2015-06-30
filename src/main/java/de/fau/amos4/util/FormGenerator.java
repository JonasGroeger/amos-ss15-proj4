package de.fau.amos4.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fau.amos4.model.*;

// Class used to load form data from class description. 
public class FormGenerator {
    //Stores forms for previously inspected classes  
    Map<Class, Form> FormHistory = new HashMap();
    private static Object lock = new Object();
    
    // Generate form for given class
    public Form Generate(Class clazz)
    {
        synchronized(lock)
        {
            // Use previously generated form when it was already made before.
            if(FormHistory.containsKey(clazz))
            {
                return FormHistory.get(clazz);
            }
            
            // Really generate form data if it is not available yet.
            Form form = ProcessObject(clazz);
            
            // Add the newly generated form data to the available form descriptions.
            FormHistory.put(clazz, form);
            
            return form;
        }
    }
    
    private Form ProcessObject(Class clazz) {
        Form form = new Form();
        
        // Process each field
        Field[] fields = clazz.getDeclaredFields();
        Map<String, FormGroup> Groups = new HashMap<String, FormGroup>();
        
        for(Field field : fields)
        {
            Annotation[] annotations = field.getAnnotations();
            
            String GroupName = null;
            String FieldName = field.getName();
            float FieldOrder = 999;
            FormGroup group = null;
            
            for(Annotation annotation : annotations)
            {
                if(annotation instanceof GroupName)
                {
                    GroupName = ((GroupName)annotation).value();
                }
                if(annotation instanceof FieldOrder)
                {
                    FieldOrder = ((FieldOrder)annotation).value();
                }
            }
            
            if(GroupName == null)
            {
               // Field has no group. It is not displayed. Let's continue with next field.
               continue;
            }
            
            // Get the group of this field
            if(Groups.containsKey(GroupName))
            {
                // Already exists - get it from Map
                group = Groups.get(GroupName);
            }
            else
            {
                // New group - must be created
                group = new FormGroup();
                group.setName(GroupName);
                form.getGroups().add(group);
            }
            
            
            // Add current field to the group
            FormField formField = new FormField();
            formField.setName(FieldName);
            formField.setGroup(group);
            formField.setFormOrder(FieldOrder);

            Class<?> type = field.getType();
            // Load options for enums
            if(type instanceof Class && ((Class<?>)type).isEnum())
            {
                Object[] possibleValues = type.getEnumConstants();
                List<String> options = new ArrayList<String>();
                for(Object o :  possibleValues)
                {
                     options.add(o.toString());
                }
                formField.setOptions(options);
            }
            
            group.getFields().add(formField);
        }
        
        
        
        return form;
    }
}
