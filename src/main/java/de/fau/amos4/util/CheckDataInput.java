package de.fau.amos4.util;

import de.fau.amos4.model.Employee;

import java.lang.reflect.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Yao Bochao on 26/06/2015.
 */
public class CheckDataInput {

    public List<String> isEmpty(Employee employee) {
        List<String> emptyFields = new Vector<String>();

        if(employee.getFirstName().isEmpty()) {
            emptyFields.add("firstName");
        }
        if(employee.getFamilyName().isEmpty()) {
            emptyFields.add("familyName");
        }
        if(employee.getStreet().isEmpty()) {
            emptyFields.add("street");
        }
        if(employee.getHouseNumber().isEmpty()) {
            emptyFields.add("houseNumber");
        }

        Field[] fields = Employee.class.getDeclaredFields();
        for (Field field : fields) {
        }

        return  emptyFields;
    }

    public List<String> checkInput(Employee employee) {
        List<String> wrongInputs = new Vector<String>();
        if(employee.getFirstName().length() > 30 || employee.getFamilyName().matches(".*[0-9].*")) {
            wrongInputs.add("firstName");
        }
        if(employee.getFamilyName().length() > 30 || employee.getFamilyName().matches(".*[0-9].*")) {
            wrongInputs.add("familyName");
        }
        if(employee.getMaidenName().length() > 30 || employee.getMaidenName().matches(".*[0-9].*")) {
            wrongInputs.add("maidenName");
        }
        if(employee.getStreet().length() > 33 ) {
            //alphanumerisch
            wrongInputs.add("street");
        }
        if(employee.getHouseNumber().length() > 9) {
            wrongInputs.add("houseNumber");
        }

        return wrongInputs;
    }
}
