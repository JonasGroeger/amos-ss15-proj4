package de.fau.amos4.model;

import java.util.ArrayList;
import java.util.List;

public class Form {
    List<FormGroup> groups = new ArrayList<FormGroup>();
    
    public List<FormGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FormGroup> groups) {
        this.groups = groups;
    }
}
