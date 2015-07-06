 
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

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.fields.*;
import de.fau.amos4.util.FieldOrder;
import de.fau.amos4.util.GroupName;
import de.fau.amos4.util.ValidFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Entity
@Table
public class Employee
{
    /*
    General Attributes
     */
    @GroupName("General")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9]*$")
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String token;

    @ManyToOne
    @JoinColumn(name="client_id")
    Client client;

    @GroupName("General")
    @FieldOrder(1.0f)
    @ValidFormat("^[0-9]*$")
    @Column
    int personnelNumber;

    /*
    Personal Data
     */

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[\\p{L} ']*$")
    @Column
    String firstName;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$")
    @Column
    String maidenName;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @Column
    @ValidFormat("^\\w{1,30}$")
    String familyName;

    @Column
    @GroupName("PersonalData")    
    @FieldOrder(1.0f)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String placeOfBirth;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String countryOfBirth;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String street;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String zipCode;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String houseNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String city;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String socialInsuranceNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Sex sex;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    YesNo disabled;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String citizenship;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String employerSocialSavingsNumber;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String iban;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String bic;

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String additionToAddress;
    
    /*
    Employment
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
    int weeklyWorkingHours;
    
    @Column
    String holidayEntitlement;
    
    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfContract typeOfContract;
    
    //Distribution of Weekly Working Hours
    @Column
    int mon;
    
    @Column
    int tue;
    
    @Column
    int wed;
    
    @Column
    int thu;
    
    @Column
    int fri;
    
    @Column
    int sat;
    
    @Column
    int sun;
    
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
    Taxes
     */
    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    int taxOfficeNumber; //4 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    long identificationNumber; //11 digits

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    int taxClass; //1 digit

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float factor; //0.001 - 0.999

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float numberOfExemptionsForChildren; //0 - 99.5, only .0 and .5 are allowed

    @GroupName("Taxes")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Denomination denomination;

    /*
    Social insurance
     */
    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    long statutoryHealthInsurance; //8 digits

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Parenthood parenthood;

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    HealthInsurance healthInsurance;

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    PensionInsurance pensionInsurance;

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    UnemploymentInsurance unemploymentInsurance;

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    NursingCareInsurance nursingCareInsurance;

    @GroupName("Social insurance")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String accidentInsuranceRiskTariff; //12 chars

    /* Temporary Employment*/
    @GroupName("Temporary Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    TypeOfContract1 typeOfContract1;

    @GroupName("Temporary Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date contractFixedDate;

    @GroupName("Temporary Employment")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date contractConcludeDate;
    
    /* Remuneration*/
    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String description1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String description2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float amount1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float amount2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float hourlyWage1;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    float hourlyWage2;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom3;

    @GroupName("Remuneration")
    @FieldOrder(1.0f)
    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    Date validFrom4;   

    public Employee()
    {

    }

    public Map<String,String> getPersonalDataFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);
        DateFormat df;
        if(locale.getLanguage().equals("de")) {
            df = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            df = new SimpleDateFormat("dd/MM/yyyy");
        }

        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.id", null, locale), Long.toString(getId()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.companyName", null, locale), getClient().getCompanyName());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.personnelNumber", null, locale), Long.toString(getPersonnelNumber()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.firstName", null, locale), getFirstName());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.familyName", null, locale), getFamilyName());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.maidenName", null, locale), getMaidenName());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.birthDate", null, locale), format.format(getBirthDate()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.placeOfBirth", null, locale), getPlaceOfBirth());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.countryOfBirth", null, locale), getCountryOfBirth());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.street", null, locale), getStreet());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.houseNumber", null, locale), getHouseNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.additionToAddress", null, locale), getAdditionToAddress());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.city", null, locale), getCity());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.zipCode", null, locale), getZipCode());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.sex", null, locale), getSex().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.maritalStatus", null, locale), getMaritalStatus().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.disabled", null, locale), getDisabled().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.citizenship", null, locale), getCitizenship());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.socialInsuranceNumber", null, locale), getSocialInsuranceNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.employerSocialSavingsNumber", null, locale), getEmployerSocialSavingsNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.iban", null, locale), getIban());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.bic", null, locale), getBic());

        //allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.token", null, locale), getToken());
        return allFields;
    }
    
    public Map<String,String> getTemporaryEmploymentFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);
        DateFormat df;
        if(locale.getLanguage().equals("de")) {
            df = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            df = new SimpleDateFormat("dd/MM/yyyy");
        }
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.typeOfContract1", null, locale), getTypeOfContract1().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.contractFixedDate", null, locale), format.format(getContractFixedDate()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.contractConcludeDate", null, locale), format.format(getContractFixedDate()));
        return allFields;
    }

    public Map<String,String> getTaxesFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.taxOfficeNumber", null, locale), Integer.toString(getTaxOfficeNumber()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.identificationNumber", null, locale), Long.toString(getIdentificationNumber()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.taxClass", null, locale), Integer.toString(getTaxClass()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.factor", null, locale), Float.toString(getFactor()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.numberOfExemptionsForChildren", null, locale), Float.toString(getNumberOfExemptionsForChildren()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.denomination", null, locale), getDenomination().toString());
        return allFields;
    }
    
    public Map<String,String> getRemunerationFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, locale);
        DateFormat df;
        if(locale.getLanguage().equals("de")) {
            df = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            df = new SimpleDateFormat("dd/MM/yyyy");
        }
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.description1", null, locale), getDescription1().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.description2", null, locale), getDescription2().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.amount1", null, locale), Float.toString(getAmount1()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.amount2", null, locale), Float.toString(getAmount2()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.validFrom1", null, locale), format.format(getValidFrom1()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.validFrom2", null, locale), format.format(getValidFrom2()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.hourlyWage1", null, locale), Float.toString(getHourlyWage1()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.hourlyWage1", null, locale), Float.toString(getHourlyWage1()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.validFrom3", null, locale), format.format(getValidFrom3()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.validFrom4", null, locale), format.format(getValidFrom4()));

        return allFields;
    }
    

    public String getAdditionToAddress()
    {
        return additionToAddress;
    }

    public void setAdditionToAddress(String additionToAddress)
    {
        this.additionToAddress = additionToAddress;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public int getPersonnelNumber()
    {
    	return personnelNumber;
    }

    public void setPersonnelNumber(int personnelNumber)
    {
    	this.personnelNumber = personnelNumber;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMaidenName()
    {
        return maidenName;
    }

    public void setMaidenName(String maidenName)
    {
        this.maidenName = maidenName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPlaceOfBirth()
    {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth)
    {
        this.placeOfBirth = placeOfBirth;
    }

    public String getCountryOfBirth()
    {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth)
    {
        this.countryOfBirth = countryOfBirth;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getSocialInsuranceNumber()
    {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber)
    {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public Sex getSex()
    {
        return sex;
    }

    public void setSex(Sex sex)
    {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus()
    {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    public YesNo getDisabled()
    {
        return disabled;
    }

    public void setDisabled(YesNo disabled)
    {
        this.disabled = disabled;
    }

    public String getCitizenship()
    {
        return citizenship;
    }

    public void setCitizenship(String citizenship)
    {
        this.citizenship = citizenship;
    }

    public String getEmployerSocialSavingsNumber()
    {
        return employerSocialSavingsNumber;
    }

    public void setEmployerSocialSavingsNumber(String employerSocialSavingsNumber)
    {
        this.employerSocialSavingsNumber = employerSocialSavingsNumber;
    }

    public String getIban()
    {
        return iban;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public String getBic()
    {
        return bic;
    }

    public void setBic(String bic)
    {
        this.bic = bic;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public int getTaxOfficeNumber()
    {
        return taxOfficeNumber;
    }

    public void setTaxOfficeNumber(int taxOfficeNumber)
    {
        this.taxOfficeNumber = taxOfficeNumber;
    }

    public long getIdentificationNumber()
    {
        return identificationNumber;
    }

    public void setIdentificationNumber(long identificationNumber)
    {
        this.identificationNumber = identificationNumber;
    }

    public int getTaxClass()
    {
        return taxClass;
    }

    public void setTaxClass(int taxClass)
    {
        this.taxClass = taxClass;
    }

    public float getFactor()
    {
        return factor;
    }

    public void setFactor(float factor)
    {
        this.factor = factor;
    }

    public float getNumberOfExemptionsForChildren()
    {
        return numberOfExemptionsForChildren;
    }

    public void setNumberOfExemptionsForChildren(float numberOfExemptionsForChildren)
    {
        this.numberOfExemptionsForChildren = numberOfExemptionsForChildren;
    }


    public Denomination getDenomination()
    {
        return denomination;
    }

    public void setDenomination(Denomination denomination)
    {
        this.denomination = denomination;
    }

    /*
    Social insurance
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

    public void setUnemploymentInsurance(UnemploymentInsurance unemploymentInsurance) {
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

    public void setNursingCareInsurance(NursingCareInsurance nursingCareInsurance) {
        this.nursingCareInsurance = nursingCareInsurance;
    }

    public String getAccidentInsuranceRiskTariff() {
        return accidentInsuranceRiskTariff;
    }

    public void setAccidentInsuranceRiskTariff(String accidentInsuranceRiskTariff) {
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

	public void setProfessionalTraining(ProfessionalTraining professionalTraining) {
		this.professionalTraining = professionalTraining;
	}

	public Date getDateApprenticeshipBegins() {
		return dateApprenticeshipBegins;
	}

	public void setDateApprenticeshipBegins(Date dateApprenticeshipBegins) {
		this.dateApprenticeshipBegins = dateApprenticeshipBegins;
	}

	public Date getPlanedDateApprenticeshipBegins() {
		return planedDateApprenticeshipEnds;
	}

	public void setPlanedDateApprenticeshipBegins(
			Date planedDateApprenticeshipBegins) {
		this.planedDateApprenticeshipEnds = planedDateApprenticeshipBegins;
	}

	public int getWeeklyWorkingHours() {
		return weeklyWorkingHours;
	}

	public void setWeeklyWorkingHours(int weeklyWorkingHours) {
		this.weeklyWorkingHours = weeklyWorkingHours;
	}

	public String getHolidayEntitlement() {
		return holidayEntitlement;
	}

	public void setHolidayEntitlement(String holidayEntitlement) {
		this.holidayEntitlement = holidayEntitlement;
	}

	public TypeOfContract getTypeOfContract() {
		return typeOfContract;
	}

	public void setTypeOfContract(TypeOfContract typeOfContract) {
		this.typeOfContract = typeOfContract;
	}

	public int getMon() {
		return mon;
	}

	public void setMon(int mon) {
		this.mon = mon;
	}

	public int getTue() {
		return tue;
	}

	public void setTue(int tue) {
		this.tue = tue;
	}

	public int getWed() {
		return wed;
	}

	public void setWed(int wed) {
		this.wed = wed;
	}

	public int getThu() {
		return thu;
	}

	public void setThu(int thu) {
		this.thu = thu;
	}

	public int getFri() {
		return fri;
	}

	public void setFri(int fri) {
		this.fri = fri;
	}

	public int getSat() {
		return sat;
	}

	public void setSat(int sat) {
		this.sat = sat;
	}

	public int getSun() {
		return sun;
	}

	public void setSun(int sun) {
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
	
	/*Temporary Employment
     */
   
   public TypeOfContract1 getTypeOfContract1()
   {
       return typeOfContract1;
   }

   public void setTypeOfContract1(TypeOfContract1 typeOfContract1)
   {
       this.typeOfContract1 = typeOfContract1;
   }

   public Date getContractFixedDate()
   {
       return contractFixedDate;
   }

   public void setContractFixedDate(Date contractFixedDate)
   {
       this.contractFixedDate = contractFixedDate;
   }
   
   public Date getContractConcludeDate()
   {
       return contractConcludeDate;
   }

   public void setContractConcludeDate(Date contractConcludeDate)
   {
       this.contractConcludeDate = contractConcludeDate;
   }
/*Remuneration*/
   public String getDescription1()
   {
       return description1;
   }

   public void setDescription1(String description1)
   {
       this.description1 = description1;
   }
   
   public String getDescription2()
   {
       return description2;
   }

   public void setDescription2(String description1)
   {
       this.description2 = description2;
   }
   
   public float getAmount1()
   {
       return amount1;
   }

   public void setAmount1(float amount1)
   {
       this.amount1 = amount1;
   }
   
   public float getAmount2()
   {
       return amount2;
   }

   public void setAmount2(float amount2)
   {
       this.amount2 = amount2;
   }
   
   public Date getValidFrom1()
   {
       return validFrom1;
   }

   public void setValidFrom1(Date ValidFrom1)
   {
       this.validFrom1 = validFrom1;
   }
   
   public Date getValidFrom2()
   {
       return validFrom2;
   }

   public void setValidFrom2(Date ValidFrom2)
   {
       this.validFrom2= validFrom2;
   }
   
   public float getHourlyWage1()
   {
       return hourlyWage1;
   }

   public void setHourlyWage1(float hourlyWage1)
   {
       this.hourlyWage1 = hourlyWage1;
   }
   
   public float getHourlyWage2()
   {
       return hourlyWage2;
   }

   public void setHourlyWage2(float hourlyWage2)
   {
       this.hourlyWage2 = hourlyWage2;
   }
   
   public Date getValidFrom3()
   {
       return validFrom3;
   }

   public void setValidFrom3(Date ValidFrom3)
   {
       this.validFrom3 = validFrom3;
   }
   
   public Date getValidFrom4()
   {
       return validFrom4;
   }

   public void setValidFrom4(Date ValidFrom4)
   {
       this.validFrom4 = validFrom4;
   }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder()
                .append(id, employee.id)
                .append(personnelNumber, employee.personnelNumber)
                .append(weeklyWorkingHours, employee.weeklyWorkingHours)
                .append(mon, employee.mon)
                .append(tue, employee.tue)
                .append(wed, employee.wed)
                .append(thu, employee.thu)
                .append(fri, employee.fri)
                .append(sat, employee.sat)
                .append(sun, employee.sun)
                .append(taxOfficeNumber, employee.taxOfficeNumber)
                .append(identificationNumber, employee.identificationNumber)
                .append(taxClass, employee.taxClass)
                .append(factor, employee.factor)
                .append(numberOfExemptionsForChildren, employee.numberOfExemptionsForChildren)
                .append(statutoryHealthInsurance, employee.statutoryHealthInsurance)
                .append(amount1, employee.amount1)
                .append(amount2, employee.amount2)
                .append(hourlyWage1, employee.hourlyWage1)
                .append(hourlyWage2, employee.hourlyWage2)
                .append(token, employee.token)
                .append(client, employee.client)
                .append(firstName, employee.firstName)
                .append(maidenName, employee.maidenName)
                .append(familyName, employee.familyName)
                .append(birthDate, employee.birthDate)
                .append(placeOfBirth, employee.placeOfBirth)
                .append(countryOfBirth, employee.countryOfBirth)
                .append(street, employee.street)
                .append(zipCode, employee.zipCode)
                .append(houseNumber, employee.houseNumber)
                .append(city, employee.city)
                .append(socialInsuranceNumber, employee.socialInsuranceNumber)
                .append(sex, employee.sex)
                .append(maritalStatus, employee.maritalStatus)
                .append(disabled, employee.disabled)
                .append(citizenship, employee.citizenship)
                .append(employerSocialSavingsNumber, employee.employerSocialSavingsNumber)
                .append(iban, employee.iban)
                .append(bic, employee.bic)
                .append(additionToAddress, employee.additionToAddress)
                .append(entryDate, employee.entryDate)
                .append(firstDay, employee.firstDay)
                .append(placeOfEmployment, employee.placeOfEmployment)
                .append(descriptionOfProfession, employee.descriptionOfProfession)
                .append(jobPerformed, employee.jobPerformed)
                .append(typeOfEmployment, employee.typeOfEmployment)
                .append(probationPeriod, employee.probationPeriod)
                .append(otherJobs, employee.otherJobs)
                .append(lowIncomeEmployment, employee.lowIncomeEmployment)
                .append(levelOfEducation, employee.levelOfEducation)
                .append(professionalTraining, employee.professionalTraining)
                .append(dateApprenticeshipBegins, employee.dateApprenticeshipBegins)
                .append(planedDateApprenticeshipEnds, employee.planedDateApprenticeshipEnds)
                .append(holidayEntitlement, employee.holidayEntitlement)
                .append(typeOfContract, employee.typeOfContract)
                .append(costCentre, employee.costCentre)
                .append(departmentNumber, employee.departmentNumber)
                .append(employedInConstructionIndustrySince, employee.employedInConstructionIndustrySince)
                .append(personGroup, employee.personGroup)
                .append(denomination, employee.denomination)
                .append(parenthood, employee.parenthood)
                .append(healthInsurance, employee.healthInsurance)
                .append(pensionInsurance, employee.pensionInsurance)
                .append(unemploymentInsurance, employee.unemploymentInsurance)
                .append(nursingCareInsurance, employee.nursingCareInsurance)
                .append(accidentInsuranceRiskTariff, employee.accidentInsuranceRiskTariff)
                .append(typeOfContract1, employee.typeOfContract1)
                .append(contractFixedDate, employee.contractFixedDate)
                .append(contractConcludeDate, employee.contractConcludeDate)
                .append(description1, employee.description1)
                .append(description2, employee.description2)
                .append(validFrom1, employee.validFrom1)
                .append(validFrom2, employee.validFrom2)
                .append(validFrom3, employee.validFrom3)
                .append(validFrom4, employee.validFrom4)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(token)
                .append(client)
                .append(personnelNumber)
                .append(firstName)
                .append(maidenName)
                .append(familyName)
                .append(birthDate)
                .append(placeOfBirth)
                .append(countryOfBirth)
                .append(street)
                .append(zipCode)
                .append(houseNumber)
                .append(city)
                .append(socialInsuranceNumber)
                .append(sex)
                .append(maritalStatus)
                .append(disabled)
                .append(citizenship)
                .append(employerSocialSavingsNumber)
                .append(iban)
                .append(bic)
                .append(additionToAddress)
                .append(entryDate)
                .append(firstDay)
                .append(placeOfEmployment)
                .append(descriptionOfProfession)
                .append(jobPerformed)
                .append(typeOfEmployment)
                .append(probationPeriod)
                .append(otherJobs)
                .append(lowIncomeEmployment)
                .append(levelOfEducation)
                .append(professionalTraining)
                .append(dateApprenticeshipBegins)
                .append(planedDateApprenticeshipEnds)
                .append(weeklyWorkingHours)
                .append(holidayEntitlement)
                .append(typeOfContract)
                .append(mon)
                .append(tue)
                .append(wed)
                .append(thu)
                .append(fri)
                .append(sat)
                .append(sun)
                .append(costCentre)
                .append(departmentNumber)
                .append(employedInConstructionIndustrySince)
                .append(personGroup)
                .append(taxOfficeNumber)
                .append(identificationNumber)
                .append(taxClass)
                .append(factor)
                .append(numberOfExemptionsForChildren)
                .append(denomination)
                .append(statutoryHealthInsurance)
                .append(parenthood)
                .append(healthInsurance)
                .append(pensionInsurance)
                .append(unemploymentInsurance)
                .append(nursingCareInsurance)
                .append(accidentInsuranceRiskTariff)
                .append(typeOfContract1)
                .append(contractFixedDate)
                .append(contractConcludeDate)
                .append(description1)
                .append(description2)
                .append(amount1)
                .append(amount2)
                .append(validFrom1)
                .append(validFrom2)
                .append(hourlyWage1)
                .append(hourlyWage2)
                .append(validFrom3)
                .append(validFrom4)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("token", token)
                .append("client", client)
                .append("personnelNumber", personnelNumber)
                .append("firstName", firstName)
                .append("maidenName", maidenName)
                .append("familyName", familyName)
                .append("birthDate", birthDate)
                .append("placeOfBirth", placeOfBirth)
                .append("countryOfBirth", countryOfBirth)
                .append("street", street)
                .append("zipCode", zipCode)
                .append("houseNumber", houseNumber)
                .append("city", city)
                .append("socialInsuranceNumber", socialInsuranceNumber)
                .append("sex", sex)
                .append("maritalStatus", maritalStatus)
                .append("disabled", disabled)
                .append("citizenship", citizenship)
                .append("employerSocialSavingsNumber", employerSocialSavingsNumber)
                .append("iban", iban)
                .append("bic", bic)
                .append("additionToAddress", additionToAddress)
                .append("entryDate", entryDate)
                .append("firstDay", firstDay)
                .append("placeOfEmployment", placeOfEmployment)
                .append("descriptionOfProfession", descriptionOfProfession)
                .append("jobPerformed", jobPerformed)
                .append("typeOfEmployment", typeOfEmployment)
                .append("probationPeriod", probationPeriod)
                .append("otherJobs", otherJobs)
                .append("lowIncomeEmployment", lowIncomeEmployment)
                .append("levelOfEducation", levelOfEducation)
                .append("professionalTraining", professionalTraining)
                .append("dateApprenticeshipBegins", dateApprenticeshipBegins)
                .append("planedDateApprenticeshipEnds", planedDateApprenticeshipEnds)
                .append("weeklyWorkingHours", weeklyWorkingHours)
                .append("holidayEntitlement", holidayEntitlement)
                .append("typeOfContract", typeOfContract)
                .append("mon", mon)
                .append("tue", tue)
                .append("wed", wed)
                .append("thu", thu)
                .append("fri", fri)
                .append("sat", sat)
                .append("sun", sun)
                .append("costCentre", costCentre)
                .append("departmentNumber", departmentNumber)
                .append("employedInConstructionIndustrySince", employedInConstructionIndustrySince)
                .append("personGroup", personGroup)
                .append("taxOfficeNumber", taxOfficeNumber)
                .append("identificationNumber", identificationNumber)
                .append("taxClass", taxClass)
                .append("factor", factor)
                .append("numberOfExemptionsForChildren", numberOfExemptionsForChildren)
                .append("denomination", denomination)
                .append("statutoryHealthInsurance", statutoryHealthInsurance)
                .append("parenthood", parenthood)
                .append("healthInsurance", healthInsurance)
                .append("pensionInsurance", pensionInsurance)
                .append("unemploymentInsurance", unemploymentInsurance)
                .append("nursingCareInsurance", nursingCareInsurance)
                .append("accidentInsuranceRiskTariff", accidentInsuranceRiskTariff)
                .append("typeOfContract1", typeOfContract1)
                .append("contractFixedDate", contractFixedDate)
                .append("contractConcludeDate", contractConcludeDate)
                .append("description1", description1)
                .append("description2", description2)
                .append("amount1", amount1)
                .append("amount2", amount2)
                .append("validFrom1", validFrom1)
                .append("validFrom2", validFrom2)
                .append("hourlyWage1", hourlyWage1)
                .append("hourlyWage2", hourlyWage2)
                .append("validFrom3", validFrom3)
                .append("validFrom4", validFrom4)
                .toString();
    }
}
