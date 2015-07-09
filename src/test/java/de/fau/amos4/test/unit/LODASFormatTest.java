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
import de.fau.amos4.model.fields.*;

public class LODASFormatTest extends BaseWebApplicationContextTests {
	Client client = new Client();
	Date date = new Date(969660000000L);
	Employee employee = new Employee(2L, "UUVXGD", client, 12345, "Max",
			"Mustermann", "Mustermann", new Date(2010, 6, 5), "Berlin",
			"Deutschland", "Musterstraße", "1000", "24", "Berlin", "89234978",
			Sex.MALE, MaritalStatus.SINGLE, YesNo.NO, "Deutsch",
			"ARBEITNEHMERNUMMER23", "DE892347289348", "BIC2389402", "",
			new Date(2011, 8, 4), new Date(2014, 6, 5), "Burger King",
			"Burger Brater", "Brat Burger", TypeOfEmployment.REGULAR,
			YesNo.YES, YesNo.YES, YesNo.YES, LevelOfEducation.NOTHING,
			ProfessionalTraining.PHD, new Date(2014, 6, 5), new Date(2014, 12,
					5), 40.0F, 12.0F, TypeOfContract.PERMANENTFULL, 6.0F, 6.0F,
			6.0F, 6.0F, 6.0F, 5.0F, 5.0F, "Gunsenhausen", "b13", new Date(2014, 12,
					5), PersonGroup.G101, 1922, 11111111111L, 3, 0.123F, 50.5F,
			Denomination.RK, 23487623, Parenthood.J, HealthInsurance._0,
			PensionInsurance._0, UnemploymentInsurance._0,
			NursingCareInsurance._0, "Coscom", TypeOfContract1.PERMANENTFULL,
			null, null, "", "", 3000, 4000, null, null, 20, 20, null, null,
			new Date(2014, 6, 5), new Date(2014, 6, 5), "", new Date(2014, 6, 5), new Date(2014, 6, 5), "");

	//test if string is not empty
	@Test
	public void test_ValidHouseNumber_Accepted() throws Exception {
		String test = "";
		Lodas lodas = new Lodas(employee);
		test = lodas.generate();
		Assert.assertFalse(test.equals(""));
	}
}
