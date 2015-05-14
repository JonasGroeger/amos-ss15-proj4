package personal.fields;

import java.util.HashMap;
import java.util.Map;

public enum MaritalStatus
{
    SINGLE("Single"), MARRIED("Married"), SEPARATED("Separated"), OTHER("Other");

    private String text;

    MaritalStatus(String text)
    {
        this.text = text;
    }

    public static Map<String, String> asMap()
    {
        Map<String, String> map = new HashMap<>();
        for (MaritalStatus m : MaritalStatus.values()) {
            map.put(m.name(), m.getText());
        }
        return map;
    }

    public String getText()
    {
        return text;
    }
}
