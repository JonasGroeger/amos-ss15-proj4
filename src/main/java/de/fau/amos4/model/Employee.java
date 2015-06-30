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
import de.fau.amos4.model.fields.TypeOfContract;
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

    @Column
    int personnelNumber;

    /*
    Personal Data
     */

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

    @Column
    String placeOfBirth;

    @Column
    String countryOfBirth;

    @Column
    String street;

    @Column
    String zipCode;

    @Column
    String houseNumber;

    @Column
    String city;

    @Column
    String socialInsuranceNumber;

    @Column
    @Enumerated(EnumType.STRING)
    Sex sex;

    @Column
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @Column
    @Enumerated(EnumType.STRING)
    YesNo disabled;

    @Column
    String citizenship;

    @Column
    String employerSocialSavingsNumber;

    @Column
    String iban;

    @Column
    String bic;

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
    Date planedDateApprenticeshipBegins;
    
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
		return planedDateApprenticeshipBegins;
	}

	public void setPlanedDateApprenticeshipBegins(
			Date planedDateApprenticeshipBegins) {
		this.planedDateApprenticeshipBegins = planedDateApprenticeshipBegins;
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
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((citizenship == null) ? 0 : citizenship.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
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
		result = prime * result + fri;
		result = prime * result
				+ ((healthInsurance == null) ? 0 : healthInsurance.hashCode());
		result = prime
				* result
				+ ((holidayEntitlement == null) ? 0 : holidayEntitlement
						.hashCode());
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
		result = prime * result + mon;
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
				+ ((planedDateApprenticeshipBegins == null) ? 0
						: planedDateApprenticeshipBegins.hashCode());
		result = prime * result
				+ ((probationPeriod == null) ? 0 : probationPeriod.hashCode());
		result = prime
				* result
				+ ((professionalTraining == null) ? 0 : professionalTraining
						.hashCode());
		result = prime * result + sat;
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime
				* result
				+ ((socialInsuranceNumber == null) ? 0 : socialInsuranceNumber
						.hashCode());
		result = prime
				* result
				+ (int) (statutoryHealthInsurance ^ (statutoryHealthInsurance >>> 32));
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + sun;
		result = prime * result + taxClass;
		result = prime * result + taxOfficeNumber;
		result = prime * result + thu;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + tue;
		result = prime * result
				+ ((typeOfContract == null) ? 0 : typeOfContract.hashCode());
		result = prime
				* result
				+ ((typeOfEmployment == null) ? 0 : typeOfEmployment.hashCode());
		result = prime
				* result
				+ ((unemploymentInsurance == null) ? 0 : unemploymentInsurance
						.hashCode());
		result = prime * result + wed;
		result = prime * result + weeklyWorkingHours;
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
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
		if (fri != other.fri)
			return false;
		if (healthInsurance != other.healthInsurance)
			return false;
		if (holidayEntitlement == null) {
			if (other.holidayEntitlement != null)
				return false;
		} else if (!holidayEntitlement.equals(other.holidayEntitlement))
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
		if (mon != other.mon)
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
		if (planedDateApprenticeshipBegins == null) {
			if (other.planedDateApprenticeshipBegins != null)
				return false;
		} else if (!planedDateApprenticeshipBegins
				.equals(other.planedDateApprenticeshipBegins))
			return false;
		if (probationPeriod != other.probationPeriod)
			return false;
		if (professionalTraining != other.professionalTraining)
			return false;
		if (sat != other.sat)
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
		if (sun != other.sun)
			return false;
		if (taxClass != other.taxClass)
			return false;
		if (taxOfficeNumber != other.taxOfficeNumber)
			return false;
		if (thu != other.thu)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tue != other.tue)
			return false;
		if (typeOfContract != other.typeOfContract)
			return false;
		if (typeOfEmployment != other.typeOfEmployment)
			return false;
		if (unemploymentInsurance != other.unemploymentInsurance)
			return false;
		if (wed != other.wed)
			return false;
		if (weeklyWorkingHours != other.weeklyWorkingHours)
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", token=" + token + ", client=" + client
				+ ", personnelNumber=" + personnelNumber + ", firstName="
				+ firstName + ", maidenName=" + maidenName + ", familyName="
				+ familyName + ", birthDate=" + birthDate + ", placeOfBirth="
				+ placeOfBirth + ", countryOfBirth=" + countryOfBirth
				+ ", street=" + street + ", zipCode=" + zipCode
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
				+ dateApprenticeshipBegins
				+ ", planedDateApprenticeshipBegins="
				+ planedDateApprenticeshipBegins + ", weeklyWorkingHours="
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
				+ accidentInsuranceRiskTariff + ", getPersonalDataFields()="
				+ getPersonalDataFields() + ", getTaxesFields()="
				+ getTaxesFields() + ", getAdditionToAddress()="
				+ getAdditionToAddress() + ", getId()=" + getId()
				+ ", getClient()=" + getClient() + ", getPersonnelNumber()="
				+ getPersonnelNumber() + ", getFirstName()=" + getFirstName()
				+ ", getMaidenName()=" + getMaidenName() + ", getFamilyName()="
				+ getFamilyName() + ", getBirthDate()=" + getBirthDate()
				+ ", getPlaceOfBirth()=" + getPlaceOfBirth()
				+ ", getCountryOfBirth()=" + getCountryOfBirth()
				+ ", getStreet()=" + getStreet() + ", getZipCode()="
				+ getZipCode() + ", getHouseNumber()=" + getHouseNumber()
				+ ", getCity()=" + getCity() + ", getSocialInsuranceNumber()="
				+ getSocialInsuranceNumber() + ", getSex()=" + getSex()
				+ ", getMaritalStatus()=" + getMaritalStatus()
				+ ", getDisabled()=" + getDisabled() + ", getCitizenship()="
				+ getCitizenship() + ", getEmployerSocialSavingsNumber()="
				+ getEmployerSocialSavingsNumber() + ", getIban()=" + getIban()
				+ ", getBic()=" + getBic() + ", getToken()=" + getToken()
				+ ", getTaxOfficeNumber()=" + getTaxOfficeNumber()
				+ ", getIdentificationNumber()=" + getIdentificationNumber()
				+ ", getTaxClass()=" + getTaxClass() + ", getFactor()="
				+ getFactor() + ", getNumberOfExemptionsForChildren()="
				+ getNumberOfExemptionsForChildren() + ", getDenomination()="
				+ getDenomination() + ", getHealthInsurance()="
				+ getHealthInsurance() + ", getPensionInsurance()="
				+ getPensionInsurance() + ", getStatutoryHealthInsurance()="
				+ getStatutoryHealthInsurance()
				+ ", getUnemploymentInsurance()=" + getUnemploymentInsurance()
				+ ", getParenthood()=" + getParenthood()
				+ ", getNursingCareInsurance()=" + getNursingCareInsurance()
				+ ", getAccidentInsuranceRiskTariff()="
				+ getAccidentInsuranceRiskTariff() + ", getEntryDate()="
				+ getEntryDate() + ", getFirstDay()=" + getFirstDay()
				+ ", getPlaceOfEmployment()=" + getPlaceOfEmployment()
				+ ", getDescriptionOfProfession()="
				+ getDescriptionOfProfession() + ", getJobPerformed()="
				+ getJobPerformed() + ", getTypeOfEmployment()="
				+ getTypeOfEmployment() + ", getProbationPeriod()="
				+ getProbationPeriod() + ", getOtherJobs()=" + getOtherJobs()
				+ ", getLowIncomeEmployment()=" + getLowIncomeEmployment()
				+ ", getLevelOfEducation()=" + getLevelOfEducation()
				+ ", getProfessionalTraining()=" + getProfessionalTraining()
				+ ", getDateApprenticeshipBegins()="
				+ getDateApprenticeshipBegins()
				+ ", getPlanedDateApprenticeshipBegins()="
				+ getPlanedDateApprenticeshipBegins()
				+ ", getWeeklyWorkingHours()=" + getWeeklyWorkingHours()
				+ ", getHolidayEntitlement()=" + getHolidayEntitlement()
				+ ", getTypeOfContract()=" + getTypeOfContract()
				+ ", getMon()=" + getMon() + ", getTue()=" + getTue()
				+ ", getWed()=" + getWed() + ", getThu()=" + getThu()
				+ ", getFri()=" + getFri() + ", getSat()=" + getSat()
				+ ", getSun()=" + getSun() + ", getCostCentre()="
				+ getCostCentre() + ", getDepartmentNumber()="
				+ getDepartmentNumber()
				+ ", getEmployedInConstructionIndustrySince()="
				+ getEmployedInConstructionIndustrySince()
				+ ", getPersonGroup()=" + getPersonGroup() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
}
