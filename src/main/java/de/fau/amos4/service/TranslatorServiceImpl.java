/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fau.amos4.service;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

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
    public String[] translate(String... messages) {
        ArrayList<String> translated = new ArrayList<>();

        for(String s: messages)
        {
            translated.add(translate(s));
        }

        return translated.toArray(new String[translated.size()]);
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
