package de.fau.amos4.model.fields;

import de.fau.amos4.configuration.AppContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public enum Sex
{
	//From .properties files
    MALE("employee.sex.male"), FEMALE("employee.sex.female"), UNKNOWN("employee.sex.unknown");

    private String text;

    Sex(String text)
    {
        this.text = text;
    }

    public String getText()
    {
    	Locale locale = LocaleContextHolder.getLocale();
    	return AppContext.getApplicationContext().getMessage(text, null, locale);
    }

    public String toString()
    {
        return getText();
    }
}
