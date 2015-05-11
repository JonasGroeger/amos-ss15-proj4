package personal;

import personal.fields.Disabled;
import personal.fields.MartialStatus;
import personal.fields.Sex;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class Employee
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String firstName;

    @Column
    String lastName;

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

    @Enumerated(EnumType.STRING)
    Sex sex;

    @Enumerated(EnumType.STRING)
    MartialStatus martialStatus;

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

    public Employee()
    {
    }

    public String getAdditionToAddress()
    {
        return additionToAddress;
    }

    public void setAdditionToAddress(String additionToAddress)
    {
        this.additionToAddress = additionToAddress;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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

    public MartialStatus getMartialStatus()
    {
        return martialStatus;
    }

    public void setMartialStatus(MartialStatus martialStatus)
    {
        this.martialStatus = martialStatus;
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

    @Override
    public String toString()
    {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public String dump()
    {
        return "Employee{" + '\n' +
                "id=" + id + '\n' +
                ", firstName='" + firstName + '\'' + '\n' +
                ", lastName='" + lastName + '\'' + '\n' +
                ", maidenName='" + maidenName + '\'' + '\n' +
                ", familyName='" + familyName + '\'' + '\n' +
                ", birthDate=" + birthDate + '\n' +
                ", placeOfBirth='" + placeOfBirth + '\'' + '\n' +
                ", countryOfBirth='" + countryOfBirth + '\'' + '\n' +
                ", street='" + street + '\'' + '\n' +
                ", zipCode='" + zipCode + '\'' + '\n' +
                ", houseNumber='" + houseNumber + '\'' + '\n' +
                ", city='" + city + '\'' + '\n' +
                ", socialInsuranceNumber='" + socialInsuranceNumber + '\'' + '\n' +
                ", sex=" + sex + '\n' +
                ", martialStatus=" + martialStatus + '\n' +
                ", disabled=" + disabled + '\n' +
                ", citizenship='" + citizenship + '\'' + '\n' +
                ", employerSocialSavingsNumber='" + employerSocialSavingsNumber + '\'' + '\n' +
                ", iban='" + iban + '\'' + '\n' +
                ", bic='" + bic + '\'' + '\n' +
                '}';
    }
}
