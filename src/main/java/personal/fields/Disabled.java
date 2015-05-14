package personal.fields;

import java.util.HashMap;
import java.util.Map;

public enum Disabled
{
    YES("Yes"), NO("No");

    private String text;

    Disabled(String text)
    {
        this.text = text;
    }

    public static Map<String, String> asMap()
    {
        Map<String, String> map = new HashMap<>();
        for (Disabled m : Disabled.values()) {
            map.put(m.name(), m.getText());
        }
        return map;
    }

    public String getText()
    {
        return text;
    }
}
