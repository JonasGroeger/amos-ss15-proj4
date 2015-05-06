package personal.fields;

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

    public String getText()
    {
        return text;
    }

    public static Map<String, String> asMap()
    {
        Map<String, String> map = new HashMap<>();
        for (Sex m : Sex.values()) {
            map.put(m.name(), m.getText());
        }
        return map;
    }
}
