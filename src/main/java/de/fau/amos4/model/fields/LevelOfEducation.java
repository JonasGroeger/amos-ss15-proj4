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

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import de.fau.amos4.configuration.AppContext;

public enum LevelOfEducation
{
    //From .properties files
    NOTHING("EMPLOYEE.education.nothing"),
    SCHOOL("EMPLOYEE.education.school"),
    HIGHSCHOOL("EMPLOYEE.education.highschool"),
    ALEVELS("EMPLOYEE.education.aLevels"),
    UNKNOWN("EMPLOYEE.education.unknown");

    private String text;

    LevelOfEducation(String text)
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
            case NOTHING:
                return 1;
            case SCHOOL:
                return 2;
            case HIGHSCHOOL:
                return 3;
            case ALEVELS:
                return 4;
            case UNKNOWN:
                return 9;
        }
        return -1;
    }
}
