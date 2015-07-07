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

package de.fau.amos4.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.tools.jar.resources.jar;
import de.fau.amos4.model.*;

// Class used to load form data from class description. 
public class FormGenerator {
    // Generate form for given class
    public Form Generate(Class clazz, Object instance)
    {
            // Really generate form data if it is not available yet.
            Form form = ProcessObject(clazz, instance);
            
            return form;
    }
    
    private Form ProcessObject(Class clazz, Object instance) {
        Form form = new Form();
        
        // Process each field
        Field[] fields = clazz.getDeclaredFields();
        Map<String, FormGroup> Groups = new HashMap<String, FormGroup>();
        
        for(Field field : fields)
        {
            Boolean IsAnnotated = false;
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations)
            {
                if(annotation instanceof GroupName)
                {
                    IsAnnotated = true;
                }
            }
            
            if(!IsAnnotated)
            {
                continue;
            }
            
            String GroupName = null;
            String FieldName = field.getName();
            String FieldValue = "";
            
            
            try {
                Object Value = clazz.getMethod("get" + FieldName.substring(0,1).toUpperCase() + FieldName.substring(1)).invoke(instance);
                Class cl = field.getClass();
                if(Value instanceof java.util.Date)
                {
                    FieldValue = (Value == null) ? "" : new SimpleDateFormat("dd/MM/yyyy").format((java.util.Date)Value).toString();
                }
                else
                {
                    FieldValue = (Value == null) ? "" : Value.toString();
                }
                
            } catch (Exception e) {
                continue;
            }
            
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
                Groups.put(GroupName, group);
                form.getGroups().add(group);
            }
            
            
            // Add current field to the group
            FormField formField = new FormField();
            formField.setName(FieldName);
            formField.setValue(FieldValue);
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
        
        // Order group elements based on Order annotations
        for(FormGroup group : form.getGroups())
        {
            List<FormField> fieldsInGroup = group.getFields();
            fieldsInGroup.sort(new Comparator<FormField>(){
                   @Override
                   public int compare(final FormField lhs,FormField rhs) {
                     if(lhs.getFormOrder() < rhs.getFormOrder())
                     {
                         return -1;
                     }
                     
                     return 1;
                     }
                 });
        }
        
        return form;
    }
}
