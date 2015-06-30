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
package de.fau.amos4.model.fields;

import de.fau.amos4.configuration.AppContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public enum Disabled
{
    //From .properties files
    YES("EMPLOYEE.disabled.yes"), NO("EMPLOYEE.disabled.no");

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

    public int getLodas()
    {
        switch (this)
        {
            case NO:
                return 0;
            case YES:
                return 1;
        }
        return -1;
    }

    public String toString()
    {
        return getText();
    }
}
