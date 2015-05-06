package personal.fields;

import java.util.HashMap;
import java.util.Map;

public enum MartialStatus
{
    SINGLE("Single"), MARRIED("Married"), SEPARATED("Separated"), OTHER("Other");

    private String text;

    MartialStatus(String text)
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
        for (MartialStatus m : MartialStatus.values()) {
            map.put(m.name(), m.getText());
        }
        return map;
    }
}
