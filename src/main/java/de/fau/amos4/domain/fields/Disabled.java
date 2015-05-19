package de.fau.amos4.domain.fields;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

public enum Disabled
{
    //From .properties files
	YES("employee.disabled.yes"), NO("employee.disabled.no");

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
    	Locale locale = LocaleContextHolder.getLocale();
    	return AppContext.getApplicationContext().getMessage(text, null, locale);
    }
    
    public String toString() {
    	return getText();
    }
}
