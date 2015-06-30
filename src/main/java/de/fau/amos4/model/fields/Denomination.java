

/**
 *  Personalfragebogen 2.0. Revolutionize form data entry for taxation and
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
 *
 * Created by Yao Bochao on 21/06/2015.
 */

package de.fau.amos4.model.fields;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import de.fau.amos4.configuration.AppContext;

public enum Denomination {
    //From .properties files
    RK("EMPLOYEE.denomination.rk"),
    EV("EMPLOYEE.denomination.ev"),
    LT("EMPLOYEE.denomination.lt"),
    RF("EMPLOYEE.denomination.rf"),
    AK("EMPLOYEE.denomination.ak"),
    IS("EMPLOYEE.denomination.is"),
    FB("EMPLOYEE.denomination.fb"),
    IB("EMPLOYEE.denomination.ib"),
    FO("EMPLOYEE.denomination.fo"),
    FP("EMPLOYEE.denomination.fp"),
    FM("EMPLOYEE.denomination.fm"),
    JÜ("EMPLOYEE.denomination.jü"),
    IW("EMPLOYEE.denomination.iw"),
    IF("EMPLOYEE.denomination.if"),
    IL("EMPLOYEE.denomination.il"),
    UN("EMPLOYEE.denomination.un"),
    FR("EMPLOYEE.denomination.fr"),
    FA("EMPLOYEE.denomination.fa"),
    FG("EMPLOYEE.denomination.fg"),
    FS("EMPLOYEE.denomination.fs"),
    IH("EMPLOYEE.denomination.ih"),
    JD("EMPLOYEE.denomination.jd"),
    JH("EMPLOYEE.denomination.jh");

    private String text;

    Denomination(String text)
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
