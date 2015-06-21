package de.fau.amos4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Translates message specifiers into readable text using the current locale.
 */
@Service
public class TranslatorServiceImpl implements TranslatorService
{
    private final ApplicationContext applicationContext;

    @Autowired
    public TranslatorServiceImpl(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public String translate(String message)
    {
        return this.translate(message, null);
    }

    @Override
    public String translate(String message, Object[] args)
    {
        return applicationContext.getMessage(message, args, getLocale());
    }

    private Locale getLocale()
    {
        return LocaleContextHolder.getLocale();
    }
}
