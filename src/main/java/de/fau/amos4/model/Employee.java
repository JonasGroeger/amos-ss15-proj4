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
import java.text.SimpleDateFormat;
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

import de.fau.amos4.model.fields.*;
import de.fau.amos4.util.FieldOrder;
import de.fau.amos4.util.GroupName;
import de.fau.amos4.util.ValidFormat;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.fields.Denomination;
import de.fau.amos4.model.fields.LevelOfEducation;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.PersonGroup;
import de.fau.amos4.model.fields.ProfessionalTraining;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.model.fields.TypeOfContract1;
import de.fau.amos4.model.fields.TypeOfEmployment;
import de.fau.amos4.model.fields.YesNo;

@Entity
@Table
public class Employee
{
    /*
    General Attributes
     */
	
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String token;

    @ManyToOne
    @JoinColumn(name="client_id")
    Client client;

    @ValidFormat("^[0-9]{5}$")
    @Column
    int personnelNumber;

    /*
    Personal Data
     */

    @GroupName("PersonalData")
    @FieldOrder(1.0f)
    @ValidFormat("^[a-zA-Z ]*$")
    @Column
    String firstName;

    @ValidFormat("^\\w{1,30}$")
    @Column
    String maidenName;

    @Column
    @ValidFormat("^\\w{1,30}$")
    String familyName;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String placeOfBirth;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String countryOfBirth;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String street;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String zipCode;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String houseNumber;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String city;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String socialInsuranceNumber;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    Sex sex;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    @Enumerated(EnumType.STRING)
    YesNo disabled;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String citizenship;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String employerSocialSavingsNumber;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String iban;

    @ValidFormat("^\\w{1,30}$") // TODO: implement real expectation as RegEx (This is just a dummy RegEx)
    @Column
    String bic;

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
    private TypeOfContract1 typeOfContract;
    
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
    
    @Column
    int taxOfficeNumber; //4 digits

    @Column
    long identificationNumber; //11 digits

    @Column
    int taxClass; //1 digit


    @Column
    float factor; //0.001 - 0.999

    @Column
    float numberOfExemptionsForChildren; //0 - 99.5, only .0 and .5 are allowed

    @Column
    @Enumerated(EnumType.STRING)
    Denomination denomination;

    /*
    Social insurance
     */
    @Column
    long statutoryHealthInsurance; //8 digits

    @Column
    Parenthood parenthood;

    @Column
    @Enumerated(EnumType.STRING)
    HealthInsurance healthInsurance;

    @Column
    @Enumerated(EnumType.STRING)
    PensionInsurance pensionInsurance;

    @Column
    @Enumerated(EnumType.STRING)
    UnemploymentInsurance unemploymentInsurance;

    @Column
    @Enumerated(EnumType.STRING)
    NursingCareInsurance nursingCareInsurance;

    @Column
    String accidentInsuranceRiskTariff; //12 chars

    
    @Column
    @Enumerated(EnumType.STRING)
    TypeOfContract1 typeOfContract1;
    
    @Column
    Date contractFixedDate;
    
    @Column
    Date contractConcludeDate;
    
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
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.typeOfContract1", null, locale), getTypeOfContract().toString());
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (personnelNumber != employee.personnelNumber) return false;
        if (taxOfficeNumber != employee.taxOfficeNumber) return false;
        if (identificationNumber != employee.identificationNumber) return false;
        if (taxClass != employee.taxClass) return false;
        if (Float.compare(employee.factor, factor) != 0) return false;
        if (Float.compare(employee.numberOfExemptionsForChildren, numberOfExemptionsForChildren) != 0) return false;
        if (statutoryHealthInsurance != employee.statutoryHealthInsurance) return false;
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
        if (zipCode != null ? !zipCode.equals(employee.zipCode) : employee.zipCode != null) return false;
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
        if (typeOfContract1 != employee.typeOfContract1) return false;
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
        return !(accidentInsuranceRiskTariff != null ? !accidentInsuranceRiskTariff.equals(employee.accidentInsuranceRiskTariff) : employee.accidentInsuranceRiskTariff != null);

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
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
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
        result = 31 * result + taxOfficeNumber;
        result = 31 * result + (int) (identificationNumber ^ (identificationNumber >>> 32));
        result = 31 * result + taxClass;
        result = 31 * result + (factor != +0.0f ? Float.floatToIntBits(factor) : 0);
        result = 31 * result + (numberOfExemptionsForChildren != +0.0f ? Float.floatToIntBits(numberOfExemptionsForChildren) : 0);
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (contractFixedDate != null ? contractFixedDate.hashCode() : 0);
        result = 31 * result + (contractConcludeDate != null ? contractConcludeDate.hashCode() : 0);
        result = 31 * result + (typeOfContract1 != null ? typeOfContract1.hashCode() : 0);
        result = 31 * result + (int) (statutoryHealthInsurance ^ (statutoryHealthInsurance >>> 32));
        result = 31 * result + (parenthood != null ? parenthood.hashCode() : 0);
        result = 31 * result + (healthInsurance != null ? healthInsurance.hashCode() : 0);
        result = 31 * result + (pensionInsurance != null ? pensionInsurance.hashCode() : 0);
        result = 31 * result + (unemploymentInsurance != null ? unemploymentInsurance.hashCode() : 0);
        result = 31 * result + (nursingCareInsurance != null ? nursingCareInsurance.hashCode() : 0);
        result = 31 * result + (accidentInsuranceRiskTariff != null ? accidentInsuranceRiskTariff.hashCode() : 0);
        return result;
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
                ", zipCode='" + zipCode + '\'' +
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
                ", taxOfficeNumber=" + taxOfficeNumber +
                ", identificationNumber=" + identificationNumber +
                ", taxClass=" + taxClass +
                ", factor=" + factor +
                ", numberOfExemptionsForChildren=" + numberOfExemptionsForChildren +
                ", denomination=" + denomination +
                ", typeOfContract1=" + typeOfContract1 +
                ", contractFixedDate=" + contractFixedDate +
                ", contractConcludeDate=" + contractConcludeDate +
                ", statutoryHealthInsurance=" + statutoryHealthInsurance +
                ", parenthood=" + parenthood +
                ", healthInsurance=" + healthInsurance +
                ", pensionInsurance=" + pensionInsurance +
                ", unemploymentInsurance=" + unemploymentInsurance +
                ", nursingCareInsurance=" + nursingCareInsurance +
                ", accidentInsuranceRiskTariff='" + accidentInsuranceRiskTariff + '\'' +
                '}';
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

	public TypeOfContract1 getTypeOfContract() {
		return typeOfContract;
	}

	public void setTypeOfContract(TypeOfContract1 typeOfContract) {
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


	
	}
