package de.fau.amos4.domain.fields;

import java.util.HashMap;
import java.util.Map;

public enum Sex
{
    MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");

    private String text;

    Sex(String text)
    {
        this.text = text;
    }

    public static Map<String, String> asMap()
    {
        Map<String, String> map = new HashMap<>();
        for (Sex m : Sex.values()) {
            map.put(m.name(), m.getText());
        }
        return map;
    }

    public String getText()
    {
        return text;
    }
}