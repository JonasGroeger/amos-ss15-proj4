package de.fau.amos4.domain.fields;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public enum Disabled
{
    //From .properties files
	YES("employee.disabled.yes"), NO("employee.disabled.no");

    private String text;

    Disabled(String text)
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
