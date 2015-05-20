package de.fau.amos4.domain.fields;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

public enum Sex
{
	//From .properties files
    MALE("employee.sex.male"), FEMALE("employee.sex.female"), UNKNOWN("employee.sex.unknown");

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
    	Locale locale = LocaleContextHolder.getLocale();
    	return AppContext.getApplicationContext().getMessage(text, null, locale);
    }
}
