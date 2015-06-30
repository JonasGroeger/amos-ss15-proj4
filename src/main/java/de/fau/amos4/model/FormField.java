package de.fau.amos4.model;

import java.util.List;

public class FormField {
    FormGroup group;
    List<String> options;
    String name;
    float FormOrder = 0;
    
    public float getFormOrder() {
        return FormOrder;
    }
    public void setFormOrder(float formOrder) {
        FormOrder = formOrder;
    }
    public FormGroup getGroup() {
        return group;
    }
    public void setGroup(FormGroup group) {
        this.group = group;
    }
    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
