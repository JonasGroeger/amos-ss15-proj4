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
import de.fau.amos4.model.fields.TypeOfContract1;
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
    private final static String AlphanumericMax30 = "^[a-zA-Z0-9']{0,12}$";

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
    @ValidFormat("^\\w{1,30}$")
    @Column
    @Enumerated(EnumType.STRING)
    Sex sex;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    @Column
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
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
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String iban;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String bic;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String additionToAddress;

    /*
     * Employment
     */

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date entryDate;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date firstDay;

    @Column
    String placeOfEmployment;

    @Column
    String descriptionOfProfession;

    @Column
    String jobPerformed;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfEmployment typeOfEmployment;

    @Column
    @Enumerated(EnumType.STRING)
    private YesNo probationPeriod;

    @Column
    @Enumerated(EnumType.STRING)
    private YesNo otherJobs;

    @Column
    @Enumerated(EnumType.STRING)
    private YesNo lowIncomeEmployment;

    @Column
    @Enumerated(EnumType.STRING)
    private LevelOfEducation levelOfEducation;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfessionalTraining professionalTraining;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date dateApprenticeshipBegins;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date planedDateApprenticeshipEnds;

    @Column
    float weeklyWorkingHours;

    @Column
    float holidayEntitlement;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfContract typeOfContract;

    // Distribution of Weekly Working Hours
    @Column
    float mon;

    @Column
    float tue;

    @Column
    float wed;

    @Column
    float thu;

    @Column
    float fri;

    @Column
    float sat;

    @Column
    float sun;

    @Column
    String costCentre;

    @Column
    String departmentNumber;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date employedInConstructionIndustrySince;

    @Column
    @Enumerated(EnumType.STRING)
    private PersonGroup personGroup;

    /*
     * Taxes
     */

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    int taxOfficeNumber; // 4 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    long identificationNumber; // 11 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
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
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Denomination denomination;

    /*
     * Social insurance
     */

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    long statutoryHealthInsurance; // 8 digits

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Parenthood parenthood;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    HealthInsurance healthInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    PensionInsurance pensionInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    UnemploymentInsurance unemploymentInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    NursingCareInsurance nursingCareInsurance;

    @GroupName("SocialInsurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String accidentInsuranceRiskTariff; // 12 chars

    /*
     * Temporary Employment
     */

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    TypeOfContract1 typeOfContract1;

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date contractFixedDate;

    @GroupName("TemporaryEmployment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date contractConcludeDate;

    /*
     * Remuneration
     */

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String description1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
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
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
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
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom3;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
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
                        "EMPLOYEE.typeOfContract1", null, locale),
                getTypeOfContract1().toString());
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

    public TypeOfContract1 getTypeOfContract1() {
        return typeOfContract1;
    }

    public void setTypeOfContract1(TypeOfContract1 typeOfContract1) {
        this.typeOfContract1 = typeOfContract1;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (accidentInsuranceRiskTariff == null) {
            if (other.accidentInsuranceRiskTariff != null)
                return false;
        } else if (!accidentInsuranceRiskTariff
                .equals(other.accidentInsuranceRiskTariff))
            return false;
        if (additionToAddress == null) {
            if (other.additionToAddress != null)
                return false;
        } else if (!additionToAddress.equals(other.additionToAddress))
            return false;
        if (Float.floatToIntBits(amount1) != Float
                .floatToIntBits(other.amount1))
            return false;
        if (Float.floatToIntBits(amount2) != Float
                .floatToIntBits(other.amount2))
            return false;
        if (bic == null) {
            if (other.bic != null)
                return false;
        } else if (!bic.equals(other.bic))
            return false;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (citizenship == null) {
            if (other.citizenship != null)
                return false;
        } else if (!citizenship.equals(other.citizenship))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (!client.equals(other.client))
            return false;
        if (contractConcludeDate == null) {
            if (other.contractConcludeDate != null)
                return false;
        } else if (!contractConcludeDate.equals(other.contractConcludeDate))
            return false;
        if (contractFixedDate == null) {
            if (other.contractFixedDate != null)
                return false;
        } else if (!contractFixedDate.equals(other.contractFixedDate))
            return false;
        if (costCentre == null) {
            if (other.costCentre != null)
                return false;
        } else if (!costCentre.equals(other.costCentre))
            return false;
        if (countryOfBirth == null) {
            if (other.countryOfBirth != null)
                return false;
        } else if (!countryOfBirth.equals(other.countryOfBirth))
            return false;
        if (dateApprenticeshipBegins == null) {
            if (other.dateApprenticeshipBegins != null)
                return false;
        } else if (!dateApprenticeshipBegins
                .equals(other.dateApprenticeshipBegins))
            return false;
        if (denomination != other.denomination)
            return false;
        if (departmentNumber == null) {
            if (other.departmentNumber != null)
                return false;
        } else if (!departmentNumber.equals(other.departmentNumber))
            return false;
        if (description1 == null) {
            if (other.description1 != null)
                return false;
        } else if (!description1.equals(other.description1))
            return false;
        if (description2 == null) {
            if (other.description2 != null)
                return false;
        } else if (!description2.equals(other.description2))
            return false;
        if (descriptionOfProfession == null) {
            if (other.descriptionOfProfession != null)
                return false;
        } else if (!descriptionOfProfession
                .equals(other.descriptionOfProfession))
            return false;
        if (disabled != other.disabled)
            return false;
        if (employedInConstructionIndustrySince == null) {
            if (other.employedInConstructionIndustrySince != null)
                return false;
        } else if (!employedInConstructionIndustrySince
                .equals(other.employedInConstructionIndustrySince))
            return false;
        if (employerSocialSavingsNumber == null) {
            if (other.employerSocialSavingsNumber != null)
                return false;
        } else if (!employerSocialSavingsNumber
                .equals(other.employerSocialSavingsNumber))
            return false;
        if (entryDate == null) {
            if (other.entryDate != null)
                return false;
        } else if (!entryDate.equals(other.entryDate))
            return false;
        if (Float.floatToIntBits(factor) != Float.floatToIntBits(other.factor))
            return false;
        if (familyName == null) {
            if (other.familyName != null)
                return false;
        } else if (!familyName.equals(other.familyName))
            return false;
        if (firstDay == null) {
            if (other.firstDay != null)
                return false;
        } else if (!firstDay.equals(other.firstDay))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (Float.floatToIntBits(fri) != Float.floatToIntBits(other.fri))
            return false;
        if (from1 == null) {
            if (other.from1 != null)
                return false;
        } else if (!from1.equals(other.from1))
            return false;
        if (from2 == null) {
            if (other.from2 != null)
                return false;
        } else if (!from2.equals(other.from2))
            return false;
        if (healthInsurance != other.healthInsurance)
            return false;
        if (Float.floatToIntBits(holidayEntitlement) != Float
                .floatToIntBits(other.holidayEntitlement))
            return false;
        if (Float.floatToIntBits(hourlyWage1) != Float
                .floatToIntBits(other.hourlyWage1))
            return false;
        if (Float.floatToIntBits(hourlyWage2) != Float
                .floatToIntBits(other.hourlyWage2))
            return false;
        if (houseNumber == null) {
            if (other.houseNumber != null)
                return false;
        } else if (!houseNumber.equals(other.houseNumber))
            return false;
        if (iban == null) {
            if (other.iban != null)
                return false;
        } else if (!iban.equals(other.iban))
            return false;
        if (id != other.id)
            return false;
        if (identificationNumber != other.identificationNumber)
            return false;
        if (jobPerformed == null) {
            if (other.jobPerformed != null)
                return false;
        } else if (!jobPerformed.equals(other.jobPerformed))
            return false;
        if (levelOfEducation != other.levelOfEducation)
            return false;
        if (lowIncomeEmployment != other.lowIncomeEmployment)
            return false;
        if (maidenName == null) {
            if (other.maidenName != null)
                return false;
        } else if (!maidenName.equals(other.maidenName))
            return false;
        if (maritalStatus != other.maritalStatus)
            return false;
        if (Float.floatToIntBits(mon) != Float.floatToIntBits(other.mon))
            return false;
        if (Float.floatToIntBits(numberOfExemptionsForChildren) != Float
                .floatToIntBits(other.numberOfExemptionsForChildren))
            return false;
        if (nursingCareInsurance != other.nursingCareInsurance)
            return false;
        if (otherJobs != other.otherJobs)
            return false;
        if (parenthood != other.parenthood)
            return false;
        if (pensionInsurance != other.pensionInsurance)
            return false;
        if (personGroup != other.personGroup)
            return false;
        if (personnelNumber != other.personnelNumber)
            return false;
        if (placeOfBirth == null) {
            if (other.placeOfBirth != null)
                return false;
        } else if (!placeOfBirth.equals(other.placeOfBirth))
            return false;
        if (placeOfEmployment == null) {
            if (other.placeOfEmployment != null)
                return false;
        } else if (!placeOfEmployment.equals(other.placeOfEmployment))
            return false;
        if (planedDateApprenticeshipEnds == null) {
            if (other.planedDateApprenticeshipEnds != null)
                return false;
        } else if (!planedDateApprenticeshipEnds
                .equals(other.planedDateApprenticeshipEnds))
            return false;
        if (probationPeriod != other.probationPeriod)
            return false;
        if (professionalTraining != other.professionalTraining)
            return false;
        if (Float.floatToIntBits(sat) != Float.floatToIntBits(other.sat))
            return false;
        if (sex != other.sex)
            return false;
        if (socialInsuranceNumber == null) {
            if (other.socialInsuranceNumber != null)
                return false;
        } else if (!socialInsuranceNumber.equals(other.socialInsuranceNumber))
            return false;
        if (statutoryHealthInsurance != other.statutoryHealthInsurance)
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (Float.floatToIntBits(sun) != Float.floatToIntBits(other.sun))
            return false;
        if (taxClass != other.taxClass)
            return false;
        if (taxOfficeNumber != other.taxOfficeNumber)
            return false;
        if (Float.floatToIntBits(thu) != Float.floatToIntBits(other.thu))
            return false;
        if (to1 == null) {
            if (other.to1 != null)
                return false;
        } else if (!to1.equals(other.to1))
            return false;
        if (to2 == null) {
            if (other.to2 != null)
                return false;
        } else if (!to2.equals(other.to2))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        if (Float.floatToIntBits(tue) != Float.floatToIntBits(other.tue))
            return false;
        if (typeOfContract != other.typeOfContract)
            return false;
        if (typeOfContract1 != other.typeOfContract1)
            return false;
        if (typeOfEmployment != other.typeOfEmployment)
            return false;
        if (typeOfPreviousEmployment1 == null) {
            if (other.typeOfPreviousEmployment1 != null)
                return false;
        } else if (!typeOfPreviousEmployment1
                .equals(other.typeOfPreviousEmployment1))
            return false;
        if (typeOfPreviousEmployment2 == null) {
            if (other.typeOfPreviousEmployment2 != null)
                return false;
        } else if (!typeOfPreviousEmployment2
                .equals(other.typeOfPreviousEmployment2))
            return false;
        if (unemploymentInsurance != other.unemploymentInsurance)
            return false;
        if (validFrom1 == null) {
            if (other.validFrom1 != null)
                return false;
        } else if (!validFrom1.equals(other.validFrom1))
            return false;
        if (validFrom2 == null) {
            if (other.validFrom2 != null)
                return false;
        } else if (!validFrom2.equals(other.validFrom2))
            return false;
        if (validFrom3 == null) {
            if (other.validFrom3 != null)
                return false;
        } else if (!validFrom3.equals(other.validFrom3))
            return false;
        if (validFrom4 == null) {
            if (other.validFrom4 != null)
                return false;
        } else if (!validFrom4.equals(other.validFrom4))
            return false;
        if (Float.floatToIntBits(wed) != Float.floatToIntBits(other.wed))
            return false;
        if (Float.floatToIntBits(weeklyWorkingHours) != Float
                .floatToIntBits(other.weeklyWorkingHours))
            return false;
        if (postcode == null) {
            if (other.postcode != null)
                return false;
        } else if (!postcode.equals(other.postcode))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((accidentInsuranceRiskTariff == null) ? 0
                        : accidentInsuranceRiskTariff.hashCode());
        result = prime
                * result
                + ((additionToAddress == null) ? 0 : additionToAddress
                        .hashCode());
        result = prime * result + Float.floatToIntBits(amount1);
        result = prime * result + Float.floatToIntBits(amount2);
        result = prime * result + ((bic == null) ? 0 : bic.hashCode());
        result = prime * result
                + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result
                + ((citizenship == null) ? 0 : citizenship.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime
                * result
                + ((contractConcludeDate == null) ? 0 : contractConcludeDate
                        .hashCode());
        result = prime
                * result
                + ((contractFixedDate == null) ? 0 : contractFixedDate
                        .hashCode());
        result = prime * result
                + ((costCentre == null) ? 0 : costCentre.hashCode());
        result = prime * result
                + ((countryOfBirth == null) ? 0 : countryOfBirth.hashCode());
        result = prime
                * result
                + ((dateApprenticeshipBegins == null) ? 0
                        : dateApprenticeshipBegins.hashCode());
        result = prime * result
                + ((denomination == null) ? 0 : denomination.hashCode());
        result = prime
                * result
                + ((departmentNumber == null) ? 0 : departmentNumber.hashCode());
        result = prime * result
                + ((description1 == null) ? 0 : description1.hashCode());
        result = prime * result
                + ((description2 == null) ? 0 : description2.hashCode());
        result = prime
                * result
                + ((descriptionOfProfession == null) ? 0
                        : descriptionOfProfession.hashCode());
        result = prime * result
                + ((disabled == null) ? 0 : disabled.hashCode());
        result = prime
                * result
                + ((employedInConstructionIndustrySince == null) ? 0
                        : employedInConstructionIndustrySince.hashCode());
        result = prime
                * result
                + ((employerSocialSavingsNumber == null) ? 0
                        : employerSocialSavingsNumber.hashCode());
        result = prime * result
                + ((entryDate == null) ? 0 : entryDate.hashCode());
        result = prime * result + Float.floatToIntBits(factor);
        result = prime * result
                + ((familyName == null) ? 0 : familyName.hashCode());
        result = prime * result
                + ((firstDay == null) ? 0 : firstDay.hashCode());
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + Float.floatToIntBits(fri);
        result = prime * result + ((from1 == null) ? 0 : from1.hashCode());
        result = prime * result + ((from2 == null) ? 0 : from2.hashCode());
        result = prime * result
                + ((healthInsurance == null) ? 0 : healthInsurance.hashCode());
        result = prime * result + Float.floatToIntBits(holidayEntitlement);
        result = prime * result + Float.floatToIntBits(hourlyWage1);
        result = prime * result + Float.floatToIntBits(hourlyWage2);
        result = prime * result
                + ((houseNumber == null) ? 0 : houseNumber.hashCode());
        result = prime * result + ((iban == null) ? 0 : iban.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result
                + (int) (identificationNumber ^ (identificationNumber >>> 32));
        result = prime * result
                + ((jobPerformed == null) ? 0 : jobPerformed.hashCode());
        result = prime
                * result
                + ((levelOfEducation == null) ? 0 : levelOfEducation.hashCode());
        result = prime
                * result
                + ((lowIncomeEmployment == null) ? 0 : lowIncomeEmployment
                        .hashCode());
        result = prime * result
                + ((maidenName == null) ? 0 : maidenName.hashCode());
        result = prime * result
                + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
        result = prime * result + Float.floatToIntBits(mon);
        result = prime * result
                + Float.floatToIntBits(numberOfExemptionsForChildren);
        result = prime
                * result
                + ((nursingCareInsurance == null) ? 0 : nursingCareInsurance
                        .hashCode());
        result = prime * result
                + ((otherJobs == null) ? 0 : otherJobs.hashCode());
        result = prime * result
                + ((parenthood == null) ? 0 : parenthood.hashCode());
        result = prime
                * result
                + ((pensionInsurance == null) ? 0 : pensionInsurance.hashCode());
        result = prime * result
                + ((personGroup == null) ? 0 : personGroup.hashCode());
        result = prime * result + personnelNumber;
        result = prime * result
                + ((placeOfBirth == null) ? 0 : placeOfBirth.hashCode());
        result = prime
                * result
                + ((placeOfEmployment == null) ? 0 : placeOfEmployment
                        .hashCode());
        result = prime
                * result
                + ((planedDateApprenticeshipEnds == null) ? 0
                        : planedDateApprenticeshipEnds.hashCode());
        result = prime * result
                + ((probationPeriod == null) ? 0 : probationPeriod.hashCode());
        result = prime
                * result
                + ((professionalTraining == null) ? 0 : professionalTraining
                        .hashCode());
        result = prime * result + Float.floatToIntBits(sat);
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime
                * result
                + ((socialInsuranceNumber == null) ? 0 : socialInsuranceNumber
                        .hashCode());
        result = prime
                * result
                + (int) (statutoryHealthInsurance ^ (statutoryHealthInsurance >>> 32));
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + Float.floatToIntBits(sun);
        result = prime * result + taxClass;
        result = prime * result + taxOfficeNumber;
        result = prime * result + Float.floatToIntBits(thu);
        result = prime * result + ((to1 == null) ? 0 : to1.hashCode());
        result = prime * result + ((to2 == null) ? 0 : to2.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + Float.floatToIntBits(tue);
        result = prime * result
                + ((typeOfContract == null) ? 0 : typeOfContract.hashCode());
        result = prime * result
                + ((typeOfContract1 == null) ? 0 : typeOfContract1.hashCode());
        result = prime
                * result
                + ((typeOfEmployment == null) ? 0 : typeOfEmployment.hashCode());
        result = prime
                * result
                + ((typeOfPreviousEmployment1 == null) ? 0
                        : typeOfPreviousEmployment1.hashCode());
        result = prime
                * result
                + ((typeOfPreviousEmployment2 == null) ? 0
                        : typeOfPreviousEmployment2.hashCode());
        result = prime
                * result
                + ((unemploymentInsurance == null) ? 0 : unemploymentInsurance
                        .hashCode());
        result = prime * result
                + ((validFrom1 == null) ? 0 : validFrom1.hashCode());
        result = prime * result
                + ((validFrom2 == null) ? 0 : validFrom2.hashCode());
        result = prime * result
                + ((validFrom3 == null) ? 0 : validFrom3.hashCode());
        result = prime * result
                + ((validFrom4 == null) ? 0 : validFrom4.hashCode());
        result = prime * result + Float.floatToIntBits(wed);
        result = prime * result + Float.floatToIntBits(weeklyWorkingHours);
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", token=" + token + ", client=" + client
                + ", personnelNumber=" + personnelNumber + ", firstName="
                + firstName + ", maidenName=" + maidenName + ", familyName="
                + familyName + ", birthDate=" + birthDate + ", placeOfBirth="
                + placeOfBirth + ", countryOfBirth=" + countryOfBirth
                + ", street=" + street + ", postcode=" + postcode
                + ", houseNumber=" + houseNumber + ", city=" + city
                + ", socialInsuranceNumber=" + socialInsuranceNumber + ", sex="
                + sex + ", maritalStatus=" + maritalStatus + ", disabled="
                + disabled + ", citizenship=" + citizenship
                + ", employerSocialSavingsNumber="
                + employerSocialSavingsNumber + ", iban=" + iban + ", bic="
                + bic + ", additionToAddress=" + additionToAddress
                + ", entryDate=" + entryDate + ", firstDay=" + firstDay
                + ", placeOfEmployment=" + placeOfEmployment
                + ", descriptionOfProfession=" + descriptionOfProfession
                + ", jobPerformed=" + jobPerformed + ", typeOfEmployment="
                + typeOfEmployment + ", probationPeriod=" + probationPeriod
                + ", otherJobs=" + otherJobs + ", lowIncomeEmployment="
                + lowIncomeEmployment + ", levelOfEducation="
                + levelOfEducation + ", professionalTraining="
                + professionalTraining + ", dateApprenticeshipBegins="
                + dateApprenticeshipBegins + ", planedDateApprenticeshipEnds="
                + planedDateApprenticeshipEnds + ", weeklyWorkingHours="
                + weeklyWorkingHours + ", holidayEntitlement="
                + holidayEntitlement + ", typeOfContract=" + typeOfContract
                + ", mon=" + mon + ", tue=" + tue + ", wed=" + wed + ", thu="
                + thu + ", fri=" + fri + ", sat=" + sat + ", sun=" + sun
                + ", costCentre=" + costCentre + ", departmentNumber="
                + departmentNumber + ", employedInConstructionIndustrySince="
                + employedInConstructionIndustrySince + ", personGroup="
                + personGroup + ", taxOfficeNumber=" + taxOfficeNumber
                + ", identificationNumber=" + identificationNumber
                + ", taxClass=" + taxClass + ", factor=" + factor
                + ", numberOfExemptionsForChildren="
                + numberOfExemptionsForChildren + ", denomination="
                + denomination + ", statutoryHealthInsurance="
                + statutoryHealthInsurance + ", parenthood=" + parenthood
                + ", healthInsurance=" + healthInsurance
                + ", pensionInsurance=" + pensionInsurance
                + ", unemploymentInsurance=" + unemploymentInsurance
                + ", nursingCareInsurance=" + nursingCareInsurance
                + ", accidentInsuranceRiskTariff="
                + accidentInsuranceRiskTariff + ", typeOfContract1="
                + typeOfContract1 + ", contractFixedDate=" + contractFixedDate
                + ", contractConcludeDate=" + contractConcludeDate
                + ", description1=" + description1 + ", description2="
                + description2 + ", amount1=" + amount1 + ", amount2="
                + amount2 + ", validFrom1=" + validFrom1 + ", validFrom2="
                + validFrom2 + ", hourlyWage1=" + hourlyWage1
                + ", hourlyWage2=" + hourlyWage2 + ", validFrom3=" + validFrom3
                + ", validFrom4=" + validFrom4 + ", from1=" + from1 + ", to1="
                + to1 + ", typeOfPreviousEmployment1="
                + typeOfPreviousEmployment1 + ", from2=" + from2 + ", to2="
                + to2 + ", typeOfPreviousEmployment2="
                + typeOfPreviousEmployment2 + "]";
    }
}
