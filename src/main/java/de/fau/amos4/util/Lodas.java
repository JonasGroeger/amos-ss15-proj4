/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 * Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
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
package de.fau.amos4.util;

import de.fau.amos4.model.Employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Lodas
{
    private final Employee employee;

    private static final String SEPARATOR = "#psd;";

    public Lodas(Employee employee)
    {
        this.employee = employee;
    }

    private String sectionAllgemein()
    {
        return "Ziel                = LODAS\n" +
               "Version_SST         = 1.0\n" +
               "Version_DB          = 9.45\n" +
               "BeraterNr           = 1111111\n" +
               "MandantenNr         = 2222222\n" +
               "Kommentarzeichen    = *\n" +
               "Feldtrennzeichen    = ;\n" +
               "Zahlenkomma         = ,\n" +
               "Datumsformat        = TT/MM/JJJJ\n" +
               "StammdatenGueltigAb = 01.02.2008";
    }

    private String divider()
    {
        return "\n\n";
    }

    private String indent()
    {
        return " ";
    }

    private String title(String name)
    {
        return String.format("[%s]\n", name);
    }

    public String generate()
    {
        StringBuilder sb = new StringBuilder();
        sb
                .append(title("Allgemein"))
                .append(sectionAllgemein())
                .append(divider())
                .append(title("Satzbeschreibung"))
                .append(sectionSatzbeschreibung())
                .append(divider())
                .append(title("Stammdaten"))
                .append(sectionStammdaten());

        return sb.toString();
    }

    private String sectionSatzbeschreibung()
    {
        StringBuilder sb = new StringBuilder();

        // Section 100
        sb.append(indent()).append("100; u_lod_psd_mitarbeiter;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("duevo_familienname").append(SEPARATOR);
        sb.append("duevo_vorname").append(SEPARATOR);
        sb.append("gebname").append(SEPARATOR);
        sb.append("adresse_strassenname").append(SEPARATOR);
        sb.append("adresse_strasse_nr").append(SEPARATOR);
        sb.append("adresse_plz").append(SEPARATOR);
        sb.append("adresse_ort").append(SEPARATOR);
        sb.append("adresse_anschriftenzusatz").append(SEPARATOR);
        sb.append("\n");

        // Section 101
        sb.append(indent()).append("101; u_lod_psd_mitarbeiter;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("geburtsdatum_ttmmjj").append(SEPARATOR);
        sb.append("gebort").append(SEPARATOR);
        sb.append("geburtsland").append(SEPARATOR);
        sb.append("geschlecht").append(SEPARATOR);
        sb.append("sozialversicherung_nr").append(SEPARATOR);
        sb.append("familienstand").append(SEPARATOR);
        sb.append("staatsangehoerigkeit").append(SEPARATOR);
        sb.append("\n");

        // Section 102
        sb.append(indent()).append("102; u_lod_psd_ma_bank;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("ma_iban").append(SEPARATOR);
        sb.append("ma_bic").append(SEPARATOR);
        sb.append("\n");

        // Section 103
        sb.append(indent()).append("103; u_lod_psd_mitarbeiter;");
        sb.append("schwerbeschaedigt").append(SEPARATOR);
        return sb.toString();
    }

    private String field(String text)
    {
        if(org.apache.commons.lang3.StringUtils.isBlank(text))
        {
            return ";";
        }

        return text + ";";
    }

    private String field(int number)
    {
        return field(String.valueOf(number));
    }

    private String sectionStammdaten()
    {
        final int personnelNumber = this.employee.getPersonnelNumber();
        StringBuilder sb = new StringBuilder();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        // Section 100
        sb.append(indent()).append("100; ")
                .append(field(personnelNumber))
                .append(field(employee.getFamilyName()))
                .append(field(employee.getFirstName()))
                .append(field(employee.getMaidenName()))
                .append(field(employee.getStreet()))
                .append(field(employee.getHouseNumber()))
                .append(field(employee.getZipCode()))
                .append(field(employee.getCity()))
                .append(field(employee.getAdditionToAddress()))
                .append("\n");

        // Section 101
        sb.append(indent()).append("101; ")
                .append(field(personnelNumber))
                .append(field(dateFormat.format(employee.getBirthDate())))
                .append(field(employee.getPlaceOfBirth()))
                .append(field(employee.getCountryOfBirth()))
                .append(field(employee.getSex().getLodas()))
                .append(field(employee.getSocialInsuranceNumber()))
                .append(field(employee.getMaritalStatus().getLodas()))
                .append(field(employee.getCitizenship()))
                .append("\n");

        // Section 102
        sb.append(indent()).append("102; ")
                .append(field(personnelNumber))
                .append(field(employee.getIban()))
                .append(field(employee.getBic()))
                .append("\n");

        // Section 103
        sb.append(indent()).append("103; ")
                .append(field(employee.getDisabled().getLodas()))
                .append(field(personnelNumber));

        return sb.toString();
    }
}
