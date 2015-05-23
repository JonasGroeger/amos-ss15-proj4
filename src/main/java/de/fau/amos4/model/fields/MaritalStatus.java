package de.fau.amos4.model.fields;

import de.fau.amos4.configuration.AppContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public enum MaritalStatus
{
	//From .properties files
    SINGLE("employee.marital.single"), MARRIED("employee.marital.married"), SEPARATED("employee.marital.separated"), OTHER("employee.marital.other");

    private String text;

    MaritalStatus(String text)
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
