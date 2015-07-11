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

package de.fau.amos4.test.unit;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Denomination;
import de.fau.amos4.model.fields.HealthInsurance;
import de.fau.amos4.model.fields.LevelOfEducation;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.NursingCareInsurance;
import de.fau.amos4.model.fields.Parenthood;
import de.fau.amos4.model.fields.PensionInsurance;
import de.fau.amos4.model.fields.PersonGroup;
import de.fau.amos4.model.fields.ProfessionalTraining;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.model.fields.TypeOfContract;
import de.fau.amos4.model.fields.TypeOfContract1;
import de.fau.amos4.model.fields.TypeOfEmployment;
import de.fau.amos4.model.fields.UnemploymentInsurance;
import de.fau.amos4.model.fields.YesNo;
import de.fau.amos4.test.BaseWebApplicationContextTests;
import de.fau.amos4.util.Lodas;

public class LODASFormatTest extends BaseWebApplicationContextTests {
    Client client = new Client();
    Date date = new Date(969660000000L);
    Employee employee = new Employee(2L, "UUVXGD", client, 12345, "Max",
            "Mustermann", "Mustermann", new Date(1275688800000L), "Berlin",
            "Deutschland", "Musterstraße", "1000", "24", "Berlin", "89234978",
            Sex.MALE, MaritalStatus.SINGLE, YesNo.NO, "Deutsch",
            "ARBEITNEHMERNUMMER23", "DE892347289348", "BIC2389402", "",
            new Date(1312408800000L), new Date(1307224800000L), "Burger King",
            "Burger Brater", "Brat Burger", TypeOfEmployment.REGULAR,
            YesNo.YES, YesNo.YES, YesNo.YES, LevelOfEducation.NOTHING,
            ProfessionalTraining.PHD, new Date(1401919200000L), new Date(1417734000000L), 40.0F, 12.0F, TypeOfContract.PERMANENTFULL, 6.0F, 6.0F,
            6.0F, 6.0F, 6.0F, 5.0F, 5.0F, "Gunsenhausen", "b13", new Date(1417734000000L), PersonGroup.G101, 1922, 11111111111L, 3, 0.123F, 50.5F,
            Denomination.RK, 23487623, Parenthood.J, HealthInsurance._0,
            PensionInsurance._0, UnemploymentInsurance._0,
            NursingCareInsurance._0, "Coscom", TypeOfContract1.PERMANENTFULL,
            null, null, "", "", 3000, 4000, null, null, 20, 20, null, null,
            new Date(1401919200000L), new Date(1401919200000L), "", new Date(1401919200000L), new Date(1401919200000L), "");

    //test if string is not empty
    @Test
    public void test_LodasString_notEmpty() throws Exception {
        String test = "";
        Lodas lodas = new Lodas(employee);
        test = lodas.generate();
        Assert.assertFalse(test.equals(""));
    }
    
    //test if string is not empty
    @Test
    @Ignore // Issue opened: https://github.com/JOBAA/amos-ss15-proj4/issues/100
    public void test_LodasString_ContainsEmployeeData() throws Exception {
        String test = "";
        Lodas lodas = new Lodas(employee);
        test = lodas.generate();
        Assert.assertTrue(test.contains(" 200; 12345;04.08.2011;\n"));
        Assert.assertTrue(test.contains(" 201; 12345;05.06.2011;\n"));
        Assert.assertTrue(test.contains(" 300; 12345;101;\n"));
        Assert.assertTrue(test.contains(" 301; 12345;05.06.2014;05.12.2014;\n"));
        Assert.assertTrue(test.contains(" 400; 12345;Burger Brater;Brat Burger;1;6;0;1;\n"));
        Assert.assertTrue(test.contains(" 502; 12345;b13;\n"));
        Assert.assertTrue(test.contains(" 503; 12345;Gunsenhausen;\n"));
        Assert.assertTrue(test.contains(" 800; 12345;40,00;6,00;6,00;6,00;6,00;6,00;5,00;\n"));
        Assert.assertTrue(test.contains(" 801; 12345;12,0;"));
    }
}
