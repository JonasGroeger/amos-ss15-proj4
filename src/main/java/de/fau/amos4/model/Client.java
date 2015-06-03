package de.fau.amos4.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Title;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table
public class Client
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="client")
    private List<Employee> employees;

    @Column
    private String companyName;

    @Column(unique = true)
    private String email;

    @Column
    private String passwordHash;

    @Column
    @Enumerated(EnumType.STRING)
    private ClientRole role;

    @Column
    private Boolean activated;

    @Column
    private String confirmationString;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Title title;
    
    @Column
    private String firstName;

    @Column
    private String familyName;
    
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    Date birthDate;
    
    @Column
    private String officeNumber;
    
    @Column
    private String phoneNumber;
    
    @Column
    private String companyType;
    
    @Column
    private String country;
    
    @Column
    private String streetName;
    
    @Column
    private String zipCode;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public ClientRole getRole()
    {
        return role;
    }

    public void setRole(ClientRole role)
    {
        this.role = role;
    }

    public boolean isActivated()
    {
        return activated;
    }

    public void setActivated(boolean activated)
    {
        this.activated = activated;
    }

    public String getConfirmationString()
    {
        return confirmationString;
    }

    public void setConfirmationString(String confirmationString)
    {
        this.confirmationString = confirmationString;
    }
    
    public Title getTitle()
    {
        return title;
    }

    public void setTitle(Title title)
    {
        this.title = title;
    }
    
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getFamilyName()
    {
        return familyName;
    }

    public void setLastName(String familyName)
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
    
    public String getOfficeNumber()
    {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber)
    {
        this.officeNumber = officeNumber;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    public String getCompanyType()
    {
        return companyType;
    }

    public void setCompanyType(String companyType)
    {
        this.companyType = companyType;
    }
    
    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }
    
    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public void generateConfirmationString() {
		String RandomString = RandomStringUtils.random(24, "ABCDEFGHIJKLMNOPQRST123456789");
		this.setConfirmationString(RandomString);
	}
	
	public Boolean tryToActivate(String enteredConfirmationCode)
	{
		if(enteredConfirmationCode.equals(this.getConfirmationString()))
		{
			this.activated = true;
		}
		
		return this.activated;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        return new EqualsBuilder()
                .append(id, client.id)
                .append(employees, client.employees)
                .append(companyName, client.companyName)
                .append(email, client.email)
                .append(passwordHash, client.passwordHash)
                .append(role, client.role)
                .append(activated, client.activated)
                .append(confirmationString, client.confirmationString)
                .append(title, client.title)
                .append(firstName, client.firstName)
                .append(familyName, client.familyName)
                .append(birthDate, client.birthDate)
                .append(officeNumber, client.officeNumber)
                .append(phoneNumber, client.phoneNumber)
                .append(companyType, client.companyType)
                .append(country, client.country)
                .append(streetName, client.streetName)
                .append(zipCode, client.zipCode)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(employees)
                .append(companyName)
                .append(email)
                .append(passwordHash)
                .append(role)
                .append(activated)
                .append(confirmationString)
                .append(title)
                .append(firstName)
                .append(familyName)
                .append(birthDate)
                .append(officeNumber)
                .append(phoneNumber)
                .append(companyType)
                .append(country)
                .append(streetName)
                .append(zipCode)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("employees", employees)
                .append("companyName", companyName)
                .append("email", email)
                .append("passwordHash", passwordHash)
                .append("role", role)
                .append("activated", activated)
                .append("confirmationString", confirmationString)
                .append("title", title)
                .append("firstName", firstName)
                .append("familyName", familyName)
                .append("birthDate", birthDate)
                .append("officeNumber", officeNumber)
                .append("phoneNumber", phoneNumber)
                .append("companyType", companyType)
                .append("country", country)
                .append("streetName", streetName)
                .append("zipCode", zipCode)
                .toString();
    }
}
