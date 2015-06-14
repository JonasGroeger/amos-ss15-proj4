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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Entity
@Table
public class Employee
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name="client_id")
    Client client;

    @Column
    int personnelNumber;

    @Column
    String firstName;

    @Column
    String maidenName;

    @Column
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
    String token;


    public Employee()
    {
    }

    public Map<String,String> getFields() {
        Map<String,String> allFields = new LinkedHashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat df;
        if(locale.getLanguage().equals("de")) {
            df = new SimpleDateFormat("DD.mm.yyyy");
        } else {
            df = new SimpleDateFormat("DD/mm/yyyy");
        }

        allFields.put( AppContext.getApplicationContext().getMessage("employee.id", null, locale), Long.toString(getId()));
        allFields.put(AppContext.getApplicationContext().getMessage("client.companyName", null, locale), getClient().getCompanyName());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.personnelNumber", null, locale), Long.toString(getPersonnelNumber()));
        allFields.put( AppContext.getApplicationContext().getMessage("employee.firstName", null, locale), getFirstName());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.familyName", null, locale), getFamilyName());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.maidenName", null, locale), getMaidenName());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.birthDate", null, locale), df.format(getBirthDate()));
        allFields.put( AppContext.getApplicationContext().getMessage("employee.placeOfBirth", null, locale), getPlaceOfBirth());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.countryOfBirth", null, locale), getCountryOfBirth());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.street", null, locale), getStreet());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.houseNumber", null, locale), getHouseNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.additionToAddress", null, locale), getAdditionToAddress());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.city", null, locale), getCity());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.zipCode", null, locale), getZipCode());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.sex", null, locale), getSex().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.maritalStatus", null, locale), getMaritalStatus().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.disabled", null, locale), getDisabled().toString());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.citizenship", null, locale), getCitizenship());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.socialInsuranceNumber", null, locale), getSocialInsuranceNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.employerSocialSavingsNumber", null, locale), getEmployerSocialSavingsNumber());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.iban", null, locale), getIban());
        allFields.put( AppContext.getApplicationContext().getMessage("employee.bic", null, locale), getBic());
        //allFields.put( AppContext.getApplicationContext().getMessage("employee.token", null, locale), getToken());
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


    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("client", client)
                .append ("personnelNumber", personnelNumber)
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
                .append("token", token)
                .toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder()
                .append(id, employee.id)
                .append(client, employee.client)
                .append(personnelNumber, employee.personnelNumber)
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
                .append(token, employee.token)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(id)
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
                .append(token)
                .toHashCode();
    }
}
