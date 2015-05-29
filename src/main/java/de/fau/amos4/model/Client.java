package de.fau.amos4.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
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

    @Column
    private String email;

    @Column
    private boolean activated;

    @Column
    private String confirmationString;

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
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("employees", employees)
                .append("companyName", companyName)
                .toString();
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
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(employees)
                .append(companyName)
                .toHashCode();
    }
}
