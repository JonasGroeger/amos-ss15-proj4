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

public enum PersonGroup
{
    //From .properties files
    G101("EMPLOYEE.personGroup.101"),
    G102("EMPLOYEE.personGroup.102"),
    G103("EMPLOYEE.personGroup.103"),
    G104("EMPLOYEE.personGroup.104"),
    G105("EMPLOYEE.personGroup.105"),
    G106("EMPLOYEE.personGroup.106"),
    G107("EMPLOYEE.personGroup.107"),
    G108("EMPLOYEE.personGroup.108"),
    G109("EMPLOYEE.personGroup.109"),
    G110("EMPLOYEE.personGroup.110"),
    G111("EMPLOYEE.personGroup.111"),
    G112("EMPLOYEE.personGroup.112"),
    G113("EMPLOYEE.personGroup.113"),
    G114("EMPLOYEE.personGroup.114"),
    G115("EMPLOYEE.personGroup.115"),
    G116("EMPLOYEE.personGroup.116"),
    G117("EMPLOYEE.personGroup.117"),
    G118("EMPLOYEE.personGroup.118"),
    G119("EMPLOYEE.personGroup.119"),
    G190("EMPLOYEE.personGroup.190"),
    G900("EMPLOYEE.personGroup.900");

    private String text;

    PersonGroup(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(text, null, locale);
    }

}
