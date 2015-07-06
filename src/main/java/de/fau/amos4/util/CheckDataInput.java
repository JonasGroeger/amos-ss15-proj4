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
package de.fau.amos4.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/*
 * This lass can be used to validate objects based on their ValidFormat annotations.
 */
public class CheckDataInput {
    // Lists fields of the class with ValidFormat annotation.
    private List<Field> getFieldsWithFormatAttribute(Class clazz){
        List<Field> result = new ArrayList<Field>();
        
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields)
        {
            Annotation annotation = field.getAnnotation(ValidFormat.class);
            if(annotation instanceof ValidFormat)
            {
                result.add(field);
            }
        }
        return result;
    }
    
    // Lists empty fields with ValidFormat annotation
    public List<String> listEmptyFields(Object object) {
        List<String> result = new ArrayList<String>();
        List<Field> fieldsWithFormat = this.getFieldsWithFormatAttribute(object.getClass());
        
        for(Field field : fieldsWithFormat)
        {
            if(this.isFieldMatching(field, object, "^(|0|0.0)$"))
            {
                result.add(field.getName());
            }
        }
        
        return result;
    }
    
    // Gets an instance of a special annotation, when none is present, then returns null
    private Annotation GetAnnotation(Field field, Class clazz)
    {
        Annotation[] annotations = field.getAnnotations();
        for(Annotation annotation : annotations)
        {
            if(clazz.isInstance(annotation))
            {
                return annotation;
            }
        }
        return null;
    }
    
    // Lists fields that are containing invalid data, based on their ValidFormat annotation
    public List<String> listInvalidFields(Object object) {
        List<String> result = new ArrayList<String>();
        List<Field> fieldsWithFormat = this.getFieldsWithFormatAttribute(object.getClass());
        
        for(Field field : fieldsWithFormat)
        {
            ValidFormat annotation = (ValidFormat)this.GetAnnotation(field, ValidFormat.class);
            String validFormatRegex = annotation.value();
            if(!this.isFieldMatching(field, object, validFormatRegex))
            {
                result.add(field.getName());
            }
        }
        
        return result;
    }
    
    // Matches an object's field with the passed regular exception. Returns true on matching, otherwise false.
    private Boolean isFieldMatching(Field field, Object object, String validFormatRegex) {
        String FieldContent = "";
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if(fieldValue != null)
            {
                FieldContent = field.get(object).toString();
            }
            field.setAccessible(false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Boolean result = FieldContent.matches(validFormatRegex);
        return result;
    }
}
