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

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.*;
import de.fau.amos4.test.BaseWebApplicationContextTests;
import de.fau.amos4.util.EmailSender;
import de.fau.amos4.util.Lodas;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;

import sun.nio.cs.MS1250;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LODASFormatTest extends BaseWebApplicationContextTests {
    Client client = new Client();
    Employee employee = new Employee();
    
    public LODASFormatTest() throws ParseException
    {
        DateFormat format = SimpleDateFormat.getDateInstance(0, Locale.GERMANY);
        Calendar c = format.getCalendar();
        c.set(2000, 0, 2); // Note: Month = 0 means January!
        Date DummyDate = c.getTime();
        employee.setId(2L);
        employee.setToken("UUVXGD");
        employee.setClient(client);
        employee.setPersonnelNumber(12345);
        employee.setFirstName("Max");
        employee.setMaidenName("Mustermann");
        employee.setFamilyName("Mustermann");
        employee.setBirthDate(DummyDate);
        employee.setPlaceOfBirth("Ort");
        employee.setCountryOfBirth("Germany");
        employee.setStreet("Muster Straße");
        employee.setZipCode("91052");
        employee.setHouseNumber("2b");
        employee.setCity("Erlangen");
        employee.setSocialInsuranceNumber("123456");
        employee.setSex(Sex.MALE);
        employee.setMaritalStatus(MaritalStatus.MARRIED);
        employee.setDisabled(YesNo.NO);
        employee.setCitizenship("German");
        employee.setEmployerSocialSavingsNumber("123456");
        employee.setIban("12345");
        employee.setBic("1234512");
        employee.setAdditionToAddress("Addition");
        employee.setEntryDate(DummyDate);
        employee.setFirstDay(DummyDate);
        employee.setPlaceOfEmployment("EmploymentPlace");
        employee.setDescriptionOfProfession("ProfessionDesc");
        employee.setJobPerformed("JobPerfomedDesc");
        employee.setTypeOfEmployment(TypeOfEmployment.REGULAR);
        employee.setProbationPeriod(YesNo.YES);
        employee.setOtherJobs(YesNo.YES);
        employee.setLowIncomeEmployment(YesNo.NO);
        employee.setLevelOfEducation(LevelOfEducation.ALEVELS);
        employee.setProfessionalTraining(ProfessionalTraining.MASTER);
        employee.setDateApprenticeshipBegins(DummyDate);
        employee.setPlanedDateApprenticeshipEnds(DummyDate);
        employee.setWeeklyWorkingHours(40f);
        employee.setHolidayEntitlement(28f);
        employee.setTypeOfContract(TypeOfContract.FIXEDTERMPART);
        employee.setMon(1f);
        employee.setTue(2f);
        employee.setWed(3f);
        employee.setThu(4f);
        employee.setFri(5f);
        employee.setSat(6f);
        employee.setSun(7f);
        employee.setCostCentre("CostCentre");
        employee.setDepartmentNumber("3");
        employee.setEmployedInConstructionIndustrySince(DummyDate);
        employee.setPersonGroup(PersonGroup.G107);
        employee.setTaxOfficeNumber(4);
        employee.setIdentificationNumber(12345678L);
        employee.setTaxClass(1);
        employee.setFactor(2f);
        employee.setNumberOfExemptionsForChildren(3f);
        employee.setDenomination(Denomination.FO);
        employee.setStatutoryHealthInsurance(4L);
        employee.setParenthood(Parenthood.N);
        employee.setHealthInsurance(HealthInsurance._3);
        employee.setPensionInsurance(PensionInsurance._3);
        employee.setUnemploymentInsurance(UnemploymentInsurance._2);
        employee.setNursingCareInsurance(NursingCareInsurance._2);
        employee.setAccidentInsuranceRiskTariff("RistTariff");
        employee.setTypeOfContract1(TypeOfContract1.FIXEDTERMPART);
        employee.setContractFixedDate(DummyDate);
        employee.setContractConcludeDate(DummyDate);
        employee.setDescription1("Desc1");
        employee.setDescription2("Des2");
        employee.setAmount1(10f);
        employee.setAmount2(20f);
        employee.setValidFrom1(DummyDate);
        employee.setValidFrom2(DummyDate);
        employee.setHourlyWage1(10f);
        employee.setHourlyWage2(20f);
        employee.setValidFrom3(DummyDate);
        employee.setValidFrom4(DummyDate);
        employee.setFrom1(DummyDate);
        employee.setTo1(DummyDate);
        employee.setTypeOfPreviousEmployment1("emp1");
        employee.setFrom2(DummyDate);
        employee.setTo2(DummyDate);
        employee.setTypeOfPreviousEmployment2("emp2");
    }

    @Test
    public void test_LodasString_notEmpty() throws Exception {
        Lodas lodas = new Lodas(employee);
        String test = lodas.generate();
        Assert.assertFalse(test.equals(""));
    }

    @Test
    public void test_LodasString_ContainsEmployeeData() throws Exception {
        Lodas lodas = new Lodas(employee);
        String test = lodas.generate();
        
        /* Expected output:
         [Stammdaten]
         100; 12345;Mustermann;Max;Mustermann;Muster Stra=C3=9Fe;2b;91052;Erlangen;=
         Addition;
         101; 12345;02.01.2000;Ort;Germany;0;123456;1;German;
         102; 12345;12345;1234512;
         103; 12345;0;
         200; 12345;02.01.2000;
         201; 12345;02.01.2000;
         300; 12345;107;
         301; 12345;02.01.2000;02.01.2000;
         400; 12345;ProfessionDesc;JobPerfomedDesc;4;5;0;4;
         502; 12345;3;
         503; 12345;CostCentre;
         800; 12345;40,00;1,00;2,00;3,00;4,00;5,00;6,00;
         801; 12345;28,0;
         */
        
        Assert.assertTrue(test.contains(" 200; 12345;02.01.2000;\n"));
        Assert.assertTrue(test.contains(" 201; 12345;02.01.2000;\n"));
        Assert.assertTrue(test.contains(" 300; 12345;107;\n"));
        Assert.assertTrue(test.contains(" 301; 12345;02.01.2000;02.01.2000;\n"));
        Assert.assertTrue(test.contains(" 400; 12345;ProfessionDesc;JobPerfomedDesc;4;5;0;4;\n"));
        Assert.assertTrue(test.contains(" 502; 12345;3;\n"));
        Assert.assertTrue(test.contains(" 503; 12345;CostCentre;\n"));
        Assert.assertTrue(test.contains(" 800; 12345;40,00;1,00;2,00;3,00;4,00;5,00;6,00;\n"));
        Assert.assertTrue(test.contains(" 801; 12345;28,0;"));
    }
}
