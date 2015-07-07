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
        sb.append("pnr").append(SEPARATOR);
        sb.append("schwerbeschaedigt").append(SEPARATOR);
        sb.append("\n");
        
        // Section 200
        sb.append(indent()).append("200; u_lod_psd_mitarbeiter;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("ersteintrittsdatum").append(SEPARATOR);
        sb.append("\n");
        
        // Section 201
        sb.append(indent()).append("201; u_lod_psd_beschaeftigung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("eintrittdatum").append(SEPARATOR);
        sb.append("\n");
        
        // Section 300
        sb.append(indent()).append("300; u_lod_psd_taetigkeit;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("persgrs").append(SEPARATOR);
        sb.append("\n");
        
        // Section 301
        sb.append(indent()).append("301; u_lod_psd_taetigkeit;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("ausbildungsbeginn").append(SEPARATOR);
        sb.append("vorr_ausbildungsende").append(SEPARATOR);
        sb.append("\n");
        
        // Section 400
        sb.append(indent()).append("400; u_lod_psd_taetigkeit;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("berufsbezeichnung").append(SEPARATOR);
        sb.append("ausg_taetigkeit").append(SEPARATOR);
        sb.append("schulabschluss").append(SEPARATOR);
        sb.append("ausbildungsabschluss").append(SEPARATOR);
        sb.append("arbeitnehmerueberlassung").append(SEPARATOR);
        sb.append("vertragsform").append(SEPARATOR);
        sb.append("\n");
        
        // Section 502
        sb.append(indent()).append("502; u_lod_psd_taetigkeit;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("kst_abteilungs_nr").append(SEPARATOR);
        sb.append("\n");
        
        // Section 503
        sb.append(indent()).append("503; u_lod_psd_taetigkeit;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("stammkostenstelle").append(SEPARATOR);
        sb.append("\n");
        
        // Section 600
        sb.append(indent()).append("600; u_lod_psd_sozialversicherung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("kv_schluessel").append(SEPARATOR);
        sb.append("rv_schluessel").append(SEPARATOR);
        sb.append("av_schluessel").append(SEPARATOR);
        sb.append("pv_schluessel").append(SEPARATOR);
        sb.append("\n");
        
        // Section 700
        sb.append(indent()).append("700; u_lod_psd_steuer;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("fa_nr").append(SEPARATOR);
        sb.append("\n");
        
        // Section 701
        sb.append(indent()).append("701; u_lod_psd_steuer;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("st_klasse").append(SEPARATOR);
        sb.append("kfb_anzahl").append(SEPARATOR);
        sb.append("\n");
        
        // Section 702
        sb.append(indent()).append("700; u_lod_psd_steuer;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("identifikationsnummer").append(SEPARATOR);
        sb.append("\n");
        
        // Section 800
        sb.append(indent()).append("800; u_lod_psd_arbeitszeit_regelm;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("az_wtl_indiv").append(SEPARATOR);
        sb.append("regelm_az_mo").append(SEPARATOR);
        sb.append("regelm_az_di").append(SEPARATOR);
        sb.append("regelm_az_mi").append(SEPARATOR);
        sb.append("regelm_az_do").append(SEPARATOR);
        sb.append("regelm_az_fr").append(SEPARATOR);
        sb.append("regelm_az_sa").append(SEPARATOR);
        sb.append("\n");
        
        // Section 801
        sb.append(indent()).append("801; u_lod_psd_arbeitszeit_regelm;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("url_tage_jhrl").append(SEPARATOR);
        sb.append("\n");
        
        // Section 900
        sb.append(indent()).append("900; u_lod_psd_vermoegensbildung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("vwl_institut_1").append(SEPARATOR);
        sb.append("\n");
        
        // Section 901
        sb.append(indent()).append("901; u_lod_psd_vermoegensbildung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("vwl_1_netto_abz_1").append(SEPARATOR);
        sb.append("vwl_ag_anteil_betrag_1").append(SEPARATOR);
        sb.append("\n");
        
        // Section 902
        sb.append(indent()).append("902; u_lod_psd_vermoegensbildung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("vwl_1_beginn_mmjj").append(SEPARATOR);
        sb.append("\n");
        
        // Section 903
        sb.append(indent()).append("903; u_lod_psd_vermoegensbildung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("vwl_vertrag_nr_1").append(SEPARATOR);
        sb.append("\n");
        
        // Section 904
        sb.append(indent()).append("904; u_lod_psd_vermoegensbildung;");
        sb.append("pnr").append(SEPARATOR);
        sb.append("vwl_1_iban").append(SEPARATOR);
        sb.append("vwl_1_bic").append(SEPARATOR);
        
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