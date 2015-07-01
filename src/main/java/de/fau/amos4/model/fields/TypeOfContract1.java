<<<<<<< HEAD
/**
 *  Personalfragebogen 2.0. Revolutionize form data entry for taxation and
=======
/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
>>>>>>> 0982800290a8819221f5a74d19766e035b744572
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


    //From .properties files

public enum TypeOfContract1
{
    //From .properties files
	FIXEDTERM("EMPLOYEE.contract.fixedTerm"),
    PERMANENT("EMPLOYEE.contract.permanent"),
    FIXEDTERMEND("EMPLOYEE.contract.fixedTermEnd");

    private String text;

    TypeOfContract1(String text)
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
=======
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


    //From .properties files

public enum TypeOfContract1
{
    //From .properties files
    PERMANENTFULL("EMPLOYEE.contract.permanentFull"),
    PERMANENTPART("EMPLOYEE.contract.permanentPart"),
    FIXEDTERMFULL("EMPLOYEE.contract.fixedtermFull"),
    FIXEDTERMPART("EMPLOYEE.contract.fixedtermPart");

    private String text;

    TypeOfContract1(String text)
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
>>>>>>> 9963098d6b15aef57abc15c3bdfe56fbb6ce7a76
