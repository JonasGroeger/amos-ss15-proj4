package de.fau.amos4.domain.fields;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

public enum MaritalStatus
{
    SINGLE("employee.marital.single"), MARRIED("employee.marital.married"), SEPARATED("employee.marital.separated"), OTHER("employee.marital.other");

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
    	Locale locale = LocaleContextHolder.getLocale();
    	return AppContext.getApplicationContext().getMessage(text, null, locale);
    }
}
