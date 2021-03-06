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

public enum ProfessionalTraining
{
    //From .properties files
    NOTHING("EMPLOYEE.professionalTraining.nothing"),
    VOCATIONAL("EMPLOYEE.professionalTraining.vocational"),
    MASTERCRAFTSMAN("EMPLOYEE.professionalTraining.masterCraftsman"),
    BACHELOR("EMPLOYEE.professionalTraining.bachelor"),
    MASTER("EMPLOYEE.professionalTraining.master"),
    PHD("EMPLOYEE.professionalTraining.phd"),
    UNKNOWN("EMPLOYEE.professionalTraining.unknown");

    private String text;

    ProfessionalTraining(String text)
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
            case VOCATIONAL:
                return 2;
            case MASTERCRAFTSMAN:
                return 3;
            case BACHELOR:
                return 4;
            case MASTER:
                return 5;
            case PHD:
                return 6;
            case UNKNOWN:
                return 9;
        }
        return -1;
    }
}
