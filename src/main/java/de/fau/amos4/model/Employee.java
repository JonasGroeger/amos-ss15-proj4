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

package de.fau.amos4.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;

import de.fau.amos4.configuration.AppContext;
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
import de.fau.amos4.model.fields.TypeOfFixedTermContract;
import de.fau.amos4.model.fields.TypeOfEmployment;
import de.fau.amos4.model.fields.UnemploymentInsurance;
import de.fau.amos4.model.fields.YesNo;
import de.fau.amos4.util.FieldOrder;
import de.fau.amos4.util.GroupName;
import de.fau.amos4.util.ValidFormat;

@Entity
@Table
public class Employee {

    public Employee() {

    }
    
    private final static String LatinCharsAndSomeCharsMax30 = "^[\\p{L} ']{0,30}$";
    private final static String LatinCharsAndSomeCharsMax34 = "^[\\p{L} ']{0,34}$";
    private final static String LatinCharsAndCharsCommonInAddressesMax33 = "^[\\p{L}0-9 _\\-\\.']{0,33}$";
    private final static String LatinCharsAndCharsCommonCityNamesMax34 = "^[\\p{L} \\-'/]{0,34}$";
    private final static String HouseNumberRegex = "^\\d+[a-zA-Z]*$";
    private final static String PostcodeRegex = "^[1-9][0-9]{4}$";
    private final static String AlphanumericMax12 = "^[a-zA-Z0-9']{0,12}$";
    private final static String AlphanumericMax30 = "^[a-zA-Z0-9']{0,30}$";
    private final static String EightDigits = "^[0-9]{8}$";
    private final static String FloatingPoint = "[-+]?[0-9]*\\.?[0-9]+";

    /*
     * General Attributes
     */

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String token;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @GroupName("General")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9]*$")
    @Column
    int personnelNumber;

    /*
     * Personal Data
     */

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndSomeCharsMax30) // Max 30 letters + special chars
    @Column
    String firstName;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndSomeCharsMax30) // Max 30 letters + special chars
    @Column
    String maidenName;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @Column
    @ValidFormat(LatinCharsAndSomeCharsMax30) // Max 30 letters + special chars
    String familyName;

    @Column
    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndSomeCharsMax34) // Max 34, alphanumeric
    @Column
    String placeOfBirth;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndSomeCharsMax30) // ??? - No such field in reference document.
    @Column
    String countryOfBirth;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndCharsCommonInAddressesMax33) // Max 33 chars, alphanumeric
    @Column
    String street;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(PostcodeRegex) // Numeric - Interval: 10000 - 99999
    @Column
    String postcode;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(HouseNumberRegex) // Max 9 chars, alphanumeric
    @Column
    String houseNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndCharsCommonCityNamesMax34) // Max 34 chars, alphanumeric
    @Column
    String city;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(AlphanumericMax12) // Max 12 chars, alphanumeric 
    @Column
    String socialInsuranceNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    @Column
    @Enumerated(EnumType.STRING)
    Sex sex;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    @Column
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    @Column
    @Enumerated(EnumType.STRING)
    YesNo disabled;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(LatinCharsAndSomeCharsMax30) // ??? - No such field in reference document.
    @Column
    String citizenship;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat(AlphanumericMax30) // ??? - No such field in reference document.
    @Column
    String employerSocialSavingsNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String iban;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String bic;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String additionToAddress;

    /*
     * Employment
     */

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date entryDate;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date firstDay;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String placeOfEmployment;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String descriptionOfProfession;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String jobPerformed;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfEmployment typeOfEmployment;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private YesNo probationPeriod;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String durationOfProbationPeriod; //TODO: datatype

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private YesNo otherJobs;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private YesNo lowIncomeEmployment;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private LevelOfEducation levelOfEducation;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private ProfessionalTraining professionalTraining;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date dateApprenticeshipBegins;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date planedDateApprenticeshipEnds;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float weeklyWorkingHours;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float holidayEntitlement;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfContract typeOfContract;

    // Distribution of Weekly Working Hours
    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float mon;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float tue;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float wed;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float thu;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float fri;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float sat;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat(FloatingPoint)
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float sun;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String costCentre;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String departmentNumber;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date employedInConstructionIndustrySince;

    @GroupName("Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    private PersonGroup personGroup;

        /*
     * Temporary Employment
     */

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    TypeOfFixedTermContract typeOfFixedTermContract;

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date contractFixedDate;

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date contractConcludeDate;

    /*
     * Taxes
     */

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    int taxOfficeNumber; // 4 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    long identificationNumber; // 11 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    int taxClass; // 1 digit

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^.{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float factor; // 0.001 - 0.999

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^.{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float numberOfExemptionsForChildren; // 0 - 99.5, only .0 and .5 are allowed

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Denomination denomination;

    /*
     * Social insurance
     */

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat(EightDigits)
    @Column
    long statutoryHealthInsurance; // 8 digits

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$") //DropDown
    @Column
    @Enumerated(EnumType.STRING)
    Parenthood parenthood;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$") //DropDown
    @Column
    @Enumerated(EnumType.STRING)
    HealthInsurance healthInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$") //DropDown
    @Column
    @Enumerated(EnumType.STRING)
    PensionInsurance pensionInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$") //DropDown
    @Column
    @Enumerated(EnumType.STRING)
    UnemploymentInsurance unemploymentInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$") //DropDown
    @Column
    @Enumerated(EnumType.STRING)
    NursingCareInsurance nursingCareInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat(AlphanumericMax12)
    @Column
    String accidentInsuranceRiskTariff; // 12 chars


    /*
     * Remuneration
     */

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String description1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\w ]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String description2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9.]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float amount1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9.]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float amount2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date validFrom1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date validFrom2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9.]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float hourlyWage1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9.]{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float hourlyWage2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date validFrom3;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    Date validFrom4;

    /*
     * Details of previous periods of taxable employment
     */

    @Column
    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date from1;

    @Column
    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date to1;

    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\p{L} ']*$")
    @Column
    String typeOfPreviousEmployment1;

    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\p{L} ']*$")
    @Column
    int numberOfEmploymentDays1;

    @Column
    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date from2;

    @Column
    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date to2;

    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\p{L} ']*$")
    @Column
    String typeOfPreviousEmployment2;

    @GroupName("PreviousEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\p{L} ']*$")
    @Column
    int numberOfEmploymentDays2;

    public Map<String, String> getPersonalDataFields() {
        Map<String, String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);

        allFields.put(
                AppContext.getApplicationContext().getMessage("EMPLOYEE.id",
                        null, locale), Long.toString(getId()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.companyName", null, locale), getClient()
                        .getCompanyName());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.personnelNumber", null, locale),
                Long.toString(getPersonnelNumber()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.firstName", null, locale), getFirstName());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.familyName", null, locale), getFamilyName());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.maidenName", null, locale), getMaidenName());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.birthDate", null, locale),
                format.format(getBirthDate()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.placeOfBirth", null, locale),
                getPlaceOfBirth());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.countryOfBirth", null, locale),
                getCountryOfBirth());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.street", null, locale), getStreet());
        allFields
                .put(AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.houseNumber", null, locale), getHouseNumber());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.additionToAddress", null, locale),
                getAdditionToAddress());
        allFields.put(
                AppContext.getApplicationContext().getMessage("EMPLOYEE.city",
                        null, locale), getCity());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.postcode", null, locale), getPostcode());
        allFields.put(
                AppContext.getApplicationContext().getMessage("EMPLOYEE.sex",
                        null, locale), getSex().toString());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.maritalStatus", null, locale),
                getMaritalStatus().toString());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.disabled", null, locale), getDisabled()
                        .toString());
        allFields
                .put(AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.citizenship", null, locale), getCitizenship());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.socialInsuranceNumber", null, locale),
                getSocialInsuranceNumber());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.employerSocialSavingsNumber", null, locale),
                getEmployerSocialSavingsNumber());
        allFields.put(
                AppContext.getApplicationContext().getMessage("EMPLOYEE.iban",
                        null, locale), getIban());
        allFields.put(
                AppContext.getApplicationContext().getMessage("EMPLOYEE.bic",
                        null, locale), getBic());

        // allFields.put(
        // AppContext.getApplicationContext().getMessage("EMPLOYEE.token", null,
        // locale), getToken());
        return allFields;
    }

    public Map<String, String> getTemporaryEmploymentFields() {
        Map<String, String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);

        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.typeOfFixedTermContract", null, locale),
                getTypeOfFixedTermContract().toString());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.contractFixedDate", null, locale),
                format.format(getContractFixedDate()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.contractConcludeDate", null, locale),
                format.format(getContractFixedDate()));
        return allFields;
    }

    public Map<String, String> getTaxesFields() {
        Map<String, String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.taxOfficeNumber", null, locale),
                Integer.toString(getTaxOfficeNumber()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.identificationNumber", null, locale),
                Long.toString(getIdentificationNumber()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.taxClass", null, locale),
                Integer.toString(getTaxClass()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.factor", null, locale),
                Float.toString(getFactor()));
        allFields
                .put(AppContext.getApplicationContext().getMessage(
                                "EMPLOYEE.numberOfExemptionsForChildren", null, locale),
                        Float.toString(getNumberOfExemptionsForChildren()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.denomination", null, locale),
                getDenomination().toString());
        return allFields;
    }

    public Map<String, String> getRemunerationFields() {
        Map<String, String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);

        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.description1", null, locale),
                getDescription1().toString());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.description2", null, locale),
                getDescription2().toString());
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.amount1", null, locale),
                Float.toString(getAmount1()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.amount2", null, locale),
                Float.toString(getAmount2()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.validFrom1", null, locale),
                format.format(getValidFrom1()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.validFrom2", null, locale),
                format.format(getValidFrom2()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.hourlyWage1", null, locale),
                Float.toString(getHourlyWage1()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.hourlyWage1", null, locale),
                Float.toString(getHourlyWage1()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.validFrom3", null, locale),
                format.format(getValidFrom3()));
        allFields.put(
                AppContext.getApplicationContext().getMessage(
                        "EMPLOYEE.validFrom4", null, locale),
                format.format(getValidFrom4()));

        return allFields;
    }

    public String getAdditionToAddress() {
        return additionToAddress;
    }

    public void setAdditionToAddress(String additionToAddress) {
        this.additionToAddress = additionToAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(int personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public YesNo getDisabled() {
        return disabled;
    }

    public void setDisabled(YesNo disabled) {
        this.disabled = disabled;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getEmployerSocialSavingsNumber() {
        return employerSocialSavingsNumber;
    }

    public void setEmployerSocialSavingsNumber(
            String employerSocialSavingsNumber) {
        this.employerSocialSavingsNumber = employerSocialSavingsNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTaxOfficeNumber() {
        return taxOfficeNumber;
    }

    public void setTaxOfficeNumber(int taxOfficeNumber) {
        this.taxOfficeNumber = taxOfficeNumber;
    }

    public long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public int getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(int taxClass) {
        this.taxClass = taxClass;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getNumberOfExemptionsForChildren() {
        return numberOfExemptionsForChildren;
    }

    public void setNumberOfExemptionsForChildren(
            float numberOfExemptionsForChildren) {
        this.numberOfExemptionsForChildren = numberOfExemptionsForChildren;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    /*
     * Social insurance
     */

    public HealthInsurance getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(HealthInsurance healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public PensionInsurance getPensionInsurance() {
        return pensionInsurance;
    }

    public void setPensionInsurance(PensionInsurance pensionInsurance) {
        this.pensionInsurance = pensionInsurance;
    }

    public long getStatutoryHealthInsurance() {
        return statutoryHealthInsurance;
    }

    public void setStatutoryHealthInsurance(long statutoryHealthInsurance) {
        this.statutoryHealthInsurance = statutoryHealthInsurance;
    }

    public UnemploymentInsurance getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(
            UnemploymentInsurance unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public Parenthood getParenthood() {
        return parenthood;
    }

    public void setParenthood(Parenthood parenthood) {
        this.parenthood = parenthood;
    }

    public NursingCareInsurance getNursingCareInsurance() {
        return nursingCareInsurance;
    }

    public void setNursingCareInsurance(
            NursingCareInsurance nursingCareInsurance) {
        this.nursingCareInsurance = nursingCareInsurance;
    }

    public String getAccidentInsuranceRiskTariff() {
        return accidentInsuranceRiskTariff;
    }

    public void setAccidentInsuranceRiskTariff(
            String accidentInsuranceRiskTariff) {
        this.accidentInsuranceRiskTariff = accidentInsuranceRiskTariff;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    public String getPlaceOfEmployment() {
        return placeOfEmployment;
    }

    public void setPlaceOfEmployment(String placeOfEmployment) {
        this.placeOfEmployment = placeOfEmployment;
    }

    public String getDescriptionOfProfession() {
        return descriptionOfProfession;
    }

    public void setDescriptionOfProfession(String descriptionOfProfession) {
        this.descriptionOfProfession = descriptionOfProfession;
    }

    public String getJobPerformed() {
        return jobPerformed;
    }

    public void setJobPerformed(String jobPerformed) {
        this.jobPerformed = jobPerformed;
    }

    public TypeOfEmployment getTypeOfEmployment() {
        return typeOfEmployment;
    }

    public void setTypeOfEmployment(TypeOfEmployment typeOfEmployment) {
        this.typeOfEmployment = typeOfEmployment;
    }

    public YesNo getProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(YesNo probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    public String getDurationOfProbationPeriod() {
        return durationOfProbationPeriod;
    }

    public void setDurationOfProbationPeriod(String durationOfProbationPeriod) {
        durationOfProbationPeriod = durationOfProbationPeriod;
    }

    public YesNo getOtherJobs() {
        return otherJobs;
    }

    public void setOtherJobs(YesNo otherJobs) {
        this.otherJobs = otherJobs;
    }

    public YesNo getLowIncomeEmployment() {
        return lowIncomeEmployment;
    }

    public void setLowIncomeEmployment(YesNo lowIncomeEmployment) {
        this.lowIncomeEmployment = lowIncomeEmployment;
    }

    public LevelOfEducation getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(LevelOfEducation levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public ProfessionalTraining getProfessionalTraining() {
        return professionalTraining;
    }

    public void setProfessionalTraining(
            ProfessionalTraining professionalTraining) {
        this.professionalTraining = professionalTraining;
    }

    public Date getDateApprenticeshipBegins() {
        return dateApprenticeshipBegins;
    }

    public void setDateApprenticeshipBegins(Date dateApprenticeshipBegins) {
        this.dateApprenticeshipBegins = dateApprenticeshipBegins;
    }

    public Date getPlanedDateApprenticeshipEnds() {
        return planedDateApprenticeshipEnds;
    }

    public void setPlanedDateApprenticeshipEnds(
            Date planedDateApprenticeshipBegins) {
        this.planedDateApprenticeshipEnds = planedDateApprenticeshipBegins;
    }

    public TypeOfContract getTypeOfContract() {
        return typeOfContract;
    }

    public void setTypeOfContract(TypeOfContract typeOfContract) {
        this.typeOfContract = typeOfContract;
    }

    public float getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public float getHolidayEntitlement() {
        return holidayEntitlement;
    }

    public float getMon() {
        return mon;
    }

    public float getTue() {
        return tue;
    }

    public float getWed() {
        return wed;
    }

    public float getThu() {
        return thu;
    }

    public float getFri() {
        return fri;
    }

    public float getSat() {
        return sat;
    }

    public float getSun() {
        return sun;
    }

    public void setWeeklyWorkingHours(float weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public void setHolidayEntitlement(float holidayEntitlement) {
        this.holidayEntitlement = holidayEntitlement;
    }

    public void setMon(float mon) {
        this.mon = mon;
    }

    public void setTue(float tue) {
        this.tue = tue;
    }

    public void setWed(float wed) {
        this.wed = wed;
    }

    public void setThu(float thu) {
        this.thu = thu;
    }

    public void setFri(float fri) {
        this.fri = fri;
    }

    public void setSat(float sat) {
        this.sat = sat;
    }

    public void setSun(float sun) {
        this.sun = sun;
    }

    public String getCostCentre() {
        return costCentre;
    }

    public void setCostCentre(String costCentre) {
        this.costCentre = costCentre;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public Date getEmployedInConstructionIndustrySince() {
        return employedInConstructionIndustrySince;
    }

    public void setEmployedInConstructionIndustrySince(
            Date employedInConstructionIndustrySince) {
        this.employedInConstructionIndustrySince = employedInConstructionIndustrySince;
    }

    public PersonGroup getPersonGroup() {
        return personGroup;
    }

    public void setPersonGroup(PersonGroup personGroup) {
        this.personGroup = personGroup;
    }

    /*
     * Temporary Employment
     */

    public TypeOfFixedTermContract getTypeOfFixedTermContract() {
        return typeOfFixedTermContract;
    }

    public void setTypeOfFixedTermContract(TypeOfFixedTermContract typeOfFixedTermContract) {
        this.typeOfFixedTermContract = typeOfFixedTermContract;
    }

    public Date getContractFixedDate() {
        return contractFixedDate;
    }

    public void setContractFixedDate(Date contractFixedDate) {
        this.contractFixedDate = contractFixedDate;
    }

    public Date getContractConcludeDate() {
        return contractConcludeDate;
    }

    public void setContractConcludeDate(Date contractConcludeDate) {
        this.contractConcludeDate = contractConcludeDate;
    }

    /*
     * Remuneration
     */

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public float getAmount1() {
        return amount1;
    }

    public void setAmount1(float amount1) {
        this.amount1 = amount1;
    }

    public float getAmount2() {
        return amount2;
    }

    public void setAmount2(float amount2) {
        this.amount2 = amount2;
    }

    public Date getValidFrom1() {
        return validFrom1;
    }

    public void setValidFrom1(Date validFrom1) {
        this.validFrom1 = validFrom1;
    }

    public Date getValidFrom2() {
        return validFrom2;
    }

    public void setValidFrom2(Date validFrom2) {
        this.validFrom2 = validFrom2;
    }

    public float getHourlyWage1() {
        return hourlyWage1;
    }

    public void setHourlyWage1(float hourlyWage1) {
        this.hourlyWage1 = hourlyWage1;
    }

    public float getHourlyWage2() {
        return hourlyWage2;
    }

    public void setHourlyWage2(float hourlyWage2) {
        this.hourlyWage2 = hourlyWage2;
    }

    public Date getValidFrom3() {
        return validFrom3;
    }

    public void setValidFrom3(Date validFrom3) {
        this.validFrom3 = validFrom3;
    }

    public Date getValidFrom4() {
        return validFrom4;
    }

    public void setValidFrom4(Date validFrom4) {
        this.validFrom4 = validFrom4;
    }

    public Date getFrom1() {
        return from1;
    }

    public void setFrom1(Date from1) {
        this.from1 = from1;
    }

    public Date getTo1() {
        return to1;
    }

    public void setTo1(Date to1) {
        this.to1 = to1;
    }

    public String getTypeOfPreviousEmployment1() {
        return typeOfPreviousEmployment1;
    }

    public void setTypeOfPreviousEmployment1(String typeOfPreviousEmployment1) {
        this.typeOfPreviousEmployment1 = typeOfPreviousEmployment1;
    }

    public int getNumberOfEmploymentDays1() {
        return numberOfEmploymentDays1;
    }

    public void setNumberOfEmploymentDays1(int numberOfEmploymentDays1) {
        this.numberOfEmploymentDays1 = numberOfEmploymentDays1;
    }

    public Date getFrom2() {
        return from2;
    }

    public void setFrom2(Date from2) {
        this.from2 = from2;
    }

    public Date getTo2() {
        return to2;
    }

    public void setTo2(Date to2) {
        this.to2 = to2;
    }

    public String getTypeOfPreviousEmployment2() {
        return typeOfPreviousEmployment2;
    }

    public void setTypeOfPreviousEmployment2(String typeOfPreviousEmployment2) {
        this.typeOfPreviousEmployment2 = typeOfPreviousEmployment2;
    }

    public int getNumberOfEmploymentDays2() {
        return numberOfEmploymentDays2;
    }

    public void setNumberOfEmploymentDays2(int numberOfEmploymentDays2) {
        this.numberOfEmploymentDays2 = numberOfEmploymentDays2;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", client=" + client +
                ", personnelNumber=" + personnelNumber +
                ", firstName='" + firstName + '\'' +
                ", maidenName='" + maidenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthDate=" + birthDate +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", countryOfBirth='" + countryOfBirth + '\'' +
                ", street='" + street + '\'' +
                ", postcode='" + postcode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", socialInsuranceNumber='" + socialInsuranceNumber + '\'' +
                ", sex=" + sex +
                ", maritalStatus=" + maritalStatus +
                ", disabled=" + disabled +
                ", citizenship='" + citizenship + '\'' +
                ", employerSocialSavingsNumber='" + employerSocialSavingsNumber + '\'' +
                ", iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", additionToAddress='" + additionToAddress + '\'' +
                ", entryDate=" + entryDate +
                ", firstDay=" + firstDay +
                ", placeOfEmployment='" + placeOfEmployment + '\'' +
                ", descriptionOfProfession='" + descriptionOfProfession + '\'' +
                ", jobPerformed='" + jobPerformed + '\'' +
                ", typeOfEmployment=" + typeOfEmployment +
                ", probationPeriod=" + probationPeriod +
                ", durationOfProbationPeriod='" + durationOfProbationPeriod + '\'' +
                ", otherJobs=" + otherJobs +
                ", lowIncomeEmployment=" + lowIncomeEmployment +
                ", levelOfEducation=" + levelOfEducation +
                ", professionalTraining=" + professionalTraining +
                ", dateApprenticeshipBegins=" + dateApprenticeshipBegins +
                ", planedDateApprenticeshipEnds=" + planedDateApprenticeshipEnds +
                ", weeklyWorkingHours=" + weeklyWorkingHours +
                ", holidayEntitlement=" + holidayEntitlement +
                ", typeOfContract=" + typeOfContract +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                ", sun=" + sun +
                ", costCentre='" + costCentre + '\'' +
                ", departmentNumber='" + departmentNumber + '\'' +
                ", employedInConstructionIndustrySince=" + employedInConstructionIndustrySince +
                ", personGroup=" + personGroup +
                ", typeOfFixedTermContract=" + typeOfFixedTermContract +
                ", contractFixedDate=" + contractFixedDate +
                ", contractConcludeDate=" + contractConcludeDate +
                ", taxOfficeNumber=" + taxOfficeNumber +
                ", identificationNumber=" + identificationNumber +
                ", taxClass=" + taxClass +
                ", factor=" + factor +
                ", numberOfExemptionsForChildren=" + numberOfExemptionsForChildren +
                ", denomination=" + denomination +
                ", statutoryHealthInsurance=" + statutoryHealthInsurance +
                ", parenthood=" + parenthood +
                ", healthInsurance=" + healthInsurance +
                ", pensionInsurance=" + pensionInsurance +
                ", unemploymentInsurance=" + unemploymentInsurance +
                ", nursingCareInsurance=" + nursingCareInsurance +
                ", accidentInsuranceRiskTariff='" + accidentInsuranceRiskTariff + '\'' +
                ", description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", amount1=" + amount1 +
                ", amount2=" + amount2 +
                ", validFrom1=" + validFrom1 +
                ", validFrom2=" + validFrom2 +
                ", hourlyWage1=" + hourlyWage1 +
                ", hourlyWage2=" + hourlyWage2 +
                ", validFrom3=" + validFrom3 +
                ", validFrom4=" + validFrom4 +
                ", from1=" + from1 +
                ", to1=" + to1 +
                ", typeOfPreviousEmployment1='" + typeOfPreviousEmployment1 + '\'' +
                ", numberOfEmploymentDays1=" + numberOfEmploymentDays1 +
                ", from2=" + from2 +
                ", to2=" + to2 +
                ", typeOfPreviousEmployment2='" + typeOfPreviousEmployment2 + '\'' +
                ", numberOfEmploymentDays2=" + numberOfEmploymentDays2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (personnelNumber != employee.personnelNumber) return false;
        if (Float.compare(employee.weeklyWorkingHours, weeklyWorkingHours) != 0) return false;
        if (Float.compare(employee.holidayEntitlement, holidayEntitlement) != 0) return false;
        if (Float.compare(employee.mon, mon) != 0) return false;
        if (Float.compare(employee.tue, tue) != 0) return false;
        if (Float.compare(employee.wed, wed) != 0) return false;
        if (Float.compare(employee.thu, thu) != 0) return false;
        if (Float.compare(employee.fri, fri) != 0) return false;
        if (Float.compare(employee.sat, sat) != 0) return false;
        if (Float.compare(employee.sun, sun) != 0) return false;
        if (taxOfficeNumber != employee.taxOfficeNumber) return false;
        if (identificationNumber != employee.identificationNumber) return false;
        if (taxClass != employee.taxClass) return false;
        if (Float.compare(employee.factor, factor) != 0) return false;
        if (Float.compare(employee.numberOfExemptionsForChildren, numberOfExemptionsForChildren) != 0) return false;
        if (statutoryHealthInsurance != employee.statutoryHealthInsurance) return false;
        if (Float.compare(employee.amount1, amount1) != 0) return false;
        if (Float.compare(employee.amount2, amount2) != 0) return false;
        if (Float.compare(employee.hourlyWage1, hourlyWage1) != 0) return false;
        if (Float.compare(employee.hourlyWage2, hourlyWage2) != 0) return false;
        if (numberOfEmploymentDays1 != employee.numberOfEmploymentDays1) return false;
        if (numberOfEmploymentDays2 != employee.numberOfEmploymentDays2) return false;
        if (token != null ? !token.equals(employee.token) : employee.token != null) return false;
        if (client != null ? !client.equals(employee.client) : employee.client != null) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (maidenName != null ? !maidenName.equals(employee.maidenName) : employee.maidenName != null) return false;
        if (familyName != null ? !familyName.equals(employee.familyName) : employee.familyName != null) return false;
        if (birthDate != null ? !birthDate.equals(employee.birthDate) : employee.birthDate != null) return false;
        if (placeOfBirth != null ? !placeOfBirth.equals(employee.placeOfBirth) : employee.placeOfBirth != null)
            return false;
        if (countryOfBirth != null ? !countryOfBirth.equals(employee.countryOfBirth) : employee.countryOfBirth != null)
            return false;
        if (street != null ? !street.equals(employee.street) : employee.street != null) return false;
        if (postcode != null ? !postcode.equals(employee.postcode) : employee.postcode != null) return false;
        if (houseNumber != null ? !houseNumber.equals(employee.houseNumber) : employee.houseNumber != null)
            return false;
        if (city != null ? !city.equals(employee.city) : employee.city != null) return false;
        if (socialInsuranceNumber != null ? !socialInsuranceNumber.equals(employee.socialInsuranceNumber) : employee.socialInsuranceNumber != null)
            return false;
        if (sex != employee.sex) return false;
        if (maritalStatus != employee.maritalStatus) return false;
        if (disabled != employee.disabled) return false;
        if (citizenship != null ? !citizenship.equals(employee.citizenship) : employee.citizenship != null)
            return false;
        if (employerSocialSavingsNumber != null ? !employerSocialSavingsNumber.equals(employee.employerSocialSavingsNumber) : employee.employerSocialSavingsNumber != null)
            return false;
        if (iban != null ? !iban.equals(employee.iban) : employee.iban != null) return false;
        if (bic != null ? !bic.equals(employee.bic) : employee.bic != null) return false;
        if (additionToAddress != null ? !additionToAddress.equals(employee.additionToAddress) : employee.additionToAddress != null)
            return false;
        if (entryDate != null ? !entryDate.equals(employee.entryDate) : employee.entryDate != null) return false;
        if (firstDay != null ? !firstDay.equals(employee.firstDay) : employee.firstDay != null) return false;
        if (placeOfEmployment != null ? !placeOfEmployment.equals(employee.placeOfEmployment) : employee.placeOfEmployment != null)
            return false;
        if (descriptionOfProfession != null ? !descriptionOfProfession.equals(employee.descriptionOfProfession) : employee.descriptionOfProfession != null)
            return false;
        if (jobPerformed != null ? !jobPerformed.equals(employee.jobPerformed) : employee.jobPerformed != null)
            return false;
        if (typeOfEmployment != employee.typeOfEmployment) return false;
        if (probationPeriod != employee.probationPeriod) return false;
        if (durationOfProbationPeriod != null ? !durationOfProbationPeriod.equals(employee.durationOfProbationPeriod) : employee.durationOfProbationPeriod != null)
            return false;
        if (otherJobs != employee.otherJobs) return false;
        if (lowIncomeEmployment != employee.lowIncomeEmployment) return false;
        if (levelOfEducation != employee.levelOfEducation) return false;
        if (professionalTraining != employee.professionalTraining) return false;
        if (dateApprenticeshipBegins != null ? !dateApprenticeshipBegins.equals(employee.dateApprenticeshipBegins) : employee.dateApprenticeshipBegins != null)
            return false;
        if (planedDateApprenticeshipEnds != null ? !planedDateApprenticeshipEnds.equals(employee.planedDateApprenticeshipEnds) : employee.planedDateApprenticeshipEnds != null)
            return false;
        if (typeOfContract != employee.typeOfContract) return false;
        if (costCentre != null ? !costCentre.equals(employee.costCentre) : employee.costCentre != null) return false;
        if (departmentNumber != null ? !departmentNumber.equals(employee.departmentNumber) : employee.departmentNumber != null)
            return false;
        if (employedInConstructionIndustrySince != null ? !employedInConstructionIndustrySince.equals(employee.employedInConstructionIndustrySince) : employee.employedInConstructionIndustrySince != null)
            return false;
        if (personGroup != employee.personGroup) return false;
        if (typeOfFixedTermContract != employee.typeOfFixedTermContract) return false;
        if (contractFixedDate != null ? !contractFixedDate.equals(employee.contractFixedDate) : employee.contractFixedDate != null)
            return false;
        if (contractConcludeDate != null ? !contractConcludeDate.equals(employee.contractConcludeDate) : employee.contractConcludeDate != null)
            return false;
        if (denomination != employee.denomination) return false;
        if (parenthood != employee.parenthood) return false;
        if (healthInsurance != employee.healthInsurance) return false;
        if (pensionInsurance != employee.pensionInsurance) return false;
        if (unemploymentInsurance != employee.unemploymentInsurance) return false;
        if (nursingCareInsurance != employee.nursingCareInsurance) return false;
        if (accidentInsuranceRiskTariff != null ? !accidentInsuranceRiskTariff.equals(employee.accidentInsuranceRiskTariff) : employee.accidentInsuranceRiskTariff != null)
            return false;
        if (description1 != null ? !description1.equals(employee.description1) : employee.description1 != null)
            return false;
        if (description2 != null ? !description2.equals(employee.description2) : employee.description2 != null)
            return false;
        if (validFrom1 != null ? !validFrom1.equals(employee.validFrom1) : employee.validFrom1 != null) return false;
        if (validFrom2 != null ? !validFrom2.equals(employee.validFrom2) : employee.validFrom2 != null) return false;
        if (validFrom3 != null ? !validFrom3.equals(employee.validFrom3) : employee.validFrom3 != null) return false;
        if (validFrom4 != null ? !validFrom4.equals(employee.validFrom4) : employee.validFrom4 != null) return false;
        if (from1 != null ? !from1.equals(employee.from1) : employee.from1 != null) return false;
        if (to1 != null ? !to1.equals(employee.to1) : employee.to1 != null) return false;
        if (typeOfPreviousEmployment1 != null ? !typeOfPreviousEmployment1.equals(employee.typeOfPreviousEmployment1) : employee.typeOfPreviousEmployment1 != null)
            return false;
        if (from2 != null ? !from2.equals(employee.from2) : employee.from2 != null) return false;
        if (to2 != null ? !to2.equals(employee.to2) : employee.to2 != null) return false;
        return !(typeOfPreviousEmployment2 != null ? !typeOfPreviousEmployment2.equals(employee.typeOfPreviousEmployment2) : employee.typeOfPreviousEmployment2 != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + personnelNumber;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (maidenName != null ? maidenName.hashCode() : 0);
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (placeOfBirth != null ? placeOfBirth.hashCode() : 0);
        result = 31 * result + (countryOfBirth != null ? countryOfBirth.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (socialInsuranceNumber != null ? socialInsuranceNumber.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
        result = 31 * result + (disabled != null ? disabled.hashCode() : 0);
        result = 31 * result + (citizenship != null ? citizenship.hashCode() : 0);
        result = 31 * result + (employerSocialSavingsNumber != null ? employerSocialSavingsNumber.hashCode() : 0);
        result = 31 * result + (iban != null ? iban.hashCode() : 0);
        result = 31 * result + (bic != null ? bic.hashCode() : 0);
        result = 31 * result + (additionToAddress != null ? additionToAddress.hashCode() : 0);
        result = 31 * result + (entryDate != null ? entryDate.hashCode() : 0);
        result = 31 * result + (firstDay != null ? firstDay.hashCode() : 0);
        result = 31 * result + (placeOfEmployment != null ? placeOfEmployment.hashCode() : 0);
        result = 31 * result + (descriptionOfProfession != null ? descriptionOfProfession.hashCode() : 0);
        result = 31 * result + (jobPerformed != null ? jobPerformed.hashCode() : 0);
        result = 31 * result + (typeOfEmployment != null ? typeOfEmployment.hashCode() : 0);
        result = 31 * result + (probationPeriod != null ? probationPeriod.hashCode() : 0);
        result = 31 * result + (durationOfProbationPeriod != null ? durationOfProbationPeriod.hashCode() : 0);
        result = 31 * result + (otherJobs != null ? otherJobs.hashCode() : 0);
        result = 31 * result + (lowIncomeEmployment != null ? lowIncomeEmployment.hashCode() : 0);
        result = 31 * result + (levelOfEducation != null ? levelOfEducation.hashCode() : 0);
        result = 31 * result + (professionalTraining != null ? professionalTraining.hashCode() : 0);
        result = 31 * result + (dateApprenticeshipBegins != null ? dateApprenticeshipBegins.hashCode() : 0);
        result = 31 * result + (planedDateApprenticeshipEnds != null ? planedDateApprenticeshipEnds.hashCode() : 0);
        result = 31 * result + (weeklyWorkingHours != +0.0f ? Float.floatToIntBits(weeklyWorkingHours) : 0);
        result = 31 * result + (holidayEntitlement != +0.0f ? Float.floatToIntBits(holidayEntitlement) : 0);
        result = 31 * result + (typeOfContract != null ? typeOfContract.hashCode() : 0);
        result = 31 * result + (mon != +0.0f ? Float.floatToIntBits(mon) : 0);
        result = 31 * result + (tue != +0.0f ? Float.floatToIntBits(tue) : 0);
        result = 31 * result + (wed != +0.0f ? Float.floatToIntBits(wed) : 0);
        result = 31 * result + (thu != +0.0f ? Float.floatToIntBits(thu) : 0);
        result = 31 * result + (fri != +0.0f ? Float.floatToIntBits(fri) : 0);
        result = 31 * result + (sat != +0.0f ? Float.floatToIntBits(sat) : 0);
        result = 31 * result + (sun != +0.0f ? Float.floatToIntBits(sun) : 0);
        result = 31 * result + (costCentre != null ? costCentre.hashCode() : 0);
        result = 31 * result + (departmentNumber != null ? departmentNumber.hashCode() : 0);
        result = 31 * result + (employedInConstructionIndustrySince != null ? employedInConstructionIndustrySince.hashCode() : 0);
        result = 31 * result + (personGroup != null ? personGroup.hashCode() : 0);
        result = 31 * result + (typeOfFixedTermContract != null ? typeOfFixedTermContract.hashCode() : 0);
        result = 31 * result + (contractFixedDate != null ? contractFixedDate.hashCode() : 0);
        result = 31 * result + (contractConcludeDate != null ? contractConcludeDate.hashCode() : 0);
        result = 31 * result + taxOfficeNumber;
        result = 31 * result + (int) (identificationNumber ^ (identificationNumber >>> 32));
        result = 31 * result + taxClass;
        result = 31 * result + (factor != +0.0f ? Float.floatToIntBits(factor) : 0);
        result = 31 * result + (numberOfExemptionsForChildren != +0.0f ? Float.floatToIntBits(numberOfExemptionsForChildren) : 0);
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (int) (statutoryHealthInsurance ^ (statutoryHealthInsurance >>> 32));
        result = 31 * result + (parenthood != null ? parenthood.hashCode() : 0);
        result = 31 * result + (healthInsurance != null ? healthInsurance.hashCode() : 0);
        result = 31 * result + (pensionInsurance != null ? pensionInsurance.hashCode() : 0);
        result = 31 * result + (unemploymentInsurance != null ? unemploymentInsurance.hashCode() : 0);
        result = 31 * result + (nursingCareInsurance != null ? nursingCareInsurance.hashCode() : 0);
        result = 31 * result + (accidentInsuranceRiskTariff != null ? accidentInsuranceRiskTariff.hashCode() : 0);
        result = 31 * result + (description1 != null ? description1.hashCode() : 0);
        result = 31 * result + (description2 != null ? description2.hashCode() : 0);
        result = 31 * result + (amount1 != +0.0f ? Float.floatToIntBits(amount1) : 0);
        result = 31 * result + (amount2 != +0.0f ? Float.floatToIntBits(amount2) : 0);
        result = 31 * result + (validFrom1 != null ? validFrom1.hashCode() : 0);
        result = 31 * result + (validFrom2 != null ? validFrom2.hashCode() : 0);
        result = 31 * result + (hourlyWage1 != +0.0f ? Float.floatToIntBits(hourlyWage1) : 0);
        result = 31 * result + (hourlyWage2 != +0.0f ? Float.floatToIntBits(hourlyWage2) : 0);
        result = 31 * result + (validFrom3 != null ? validFrom3.hashCode() : 0);
        result = 31 * result + (validFrom4 != null ? validFrom4.hashCode() : 0);
        result = 31 * result + (from1 != null ? from1.hashCode() : 0);
        result = 31 * result + (to1 != null ? to1.hashCode() : 0);
        result = 31 * result + (typeOfPreviousEmployment1 != null ? typeOfPreviousEmployment1.hashCode() : 0);
        result = 31 * result + numberOfEmploymentDays1;
        result = 31 * result + (from2 != null ? from2.hashCode() : 0);
        result = 31 * result + (to2 != null ? to2.hashCode() : 0);
        result = 31 * result + (typeOfPreviousEmployment2 != null ? typeOfPreviousEmployment2.hashCode() : 0);
        result = 31 * result + numberOfEmploymentDays2;
        return result;
    }
}
