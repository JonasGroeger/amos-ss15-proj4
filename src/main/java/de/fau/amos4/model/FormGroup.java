package de.fau.amos4.model;

import java.util.ArrayList;
import java.util.List;

public class FormGroup {
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<FormField> getFields() {
        return fields;
    }
    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
    String name;
    List<FormField> fields = new ArrayList<FormField>(); 
}
