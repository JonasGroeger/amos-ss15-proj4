package de.fau.amos4.model.fields;

import de.fau.amos4.configuration.AppContext;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public enum Title
{
    //From .properties files
    Mr("RegistrationPage.title.Mr"), Ms("RegistrationPage.title.Ms"), Mrs("RegistrationPage.title.Mrs");

    private String text;

    Title(String text)
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
