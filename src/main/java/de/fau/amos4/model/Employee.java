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

import de.fau.amos4.util.ValidFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.format.annotation.DateTimeFormat;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.fields.Denomination;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;

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

    @Column
    @ValidFormat("^\\w{1,30}$")
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
    Disabled disabled;

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
    
    @Column
    String employment;
    
    @Column
    String temporaryEmployment;

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
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.employment", null, locale), getEmployment());
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.temporaryEmployment", null, locale), getTemporaryEmployment());
        //allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.token", null, locale), getToken());
        return allFields;
    }

    public Map<String,String> getTaxesFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.taxOfficeNumber", null, locale), Integer.toString(getTaxOfficeNumber()));
        allFields.put( AppContext.getApplicationContext().getMessage("EMPLOYEE.identificationNumber", null, locale), Long.toString(getIdentificationNumber()));
        allFields.put(AppContext.getApplicationContext().getMessage("EMPLOYEE.taxClass", null, locale), Integer.toString(getTaxClass()));
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

    public int getPersonnelNumber() {return personnelNumber;}

    public void setPersonnelNumber(int personnelNumber) { this.personnelNumber = personnelNumber;}

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getEmployment()
    {
        return employment;
    }

    public void setEmployment(String employment)
    {
        this.employment = employment;
    }
    public String getTemporaryEmployment()
    {
        return temporaryEmployment;
    }

    public void setTemporaryEmployment(String temporaryEmployment)
    {
        this.temporaryEmployment = temporaryEmployment;
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

    public Disabled getDisabled()
    {
        return disabled;
    }

    public void setDisabled(Disabled disabled)
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
        if (employment != null ? !employment.equals(employee.employment) : employee.employment != null) return false;
        if (temporaryEmployment != null ? !temporaryEmployment.equals(employee.temporaryEmployment) : employee.temporaryEmployment != null)
            return false;
        return denomination == employee.denomination;

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
        result = 31 * result + (employment != null ? employment.hashCode() : 0);
        result = 31 * result + (temporaryEmployment != null ? temporaryEmployment.hashCode() : 0);
        result = 31 * result + taxOfficeNumber;
        result = 31 * result + (int) (identificationNumber ^ (identificationNumber >>> 32));
        result = 31 * result + taxClass;
        result = 31 * result + (factor != +0.0f ? Float.floatToIntBits(factor) : 0);
        result = 31 * result + (numberOfExemptionsForChildren != +0.0f ? Float.floatToIntBits(numberOfExemptionsForChildren) : 0);
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
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
                ", employment='" + employment + '\'' +
                ", temporaryEmployment='" + temporaryEmployment + '\'' +
                ", taxOfficeNumber=" + taxOfficeNumber +
                ", identificationNumber=" + identificationNumber +
                ", taxClass=" + taxClass +
                ", factor=" + factor +
                ", numberOfExemptionsForChildren=" + numberOfExemptionsForChildren +
                ", denomination=" + denomination +
                '}';
    }

}
