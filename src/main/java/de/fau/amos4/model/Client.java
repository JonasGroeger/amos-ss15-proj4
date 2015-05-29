package de.fau.amos4.model;

import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Table
public class Client
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String companyName;

    @Column
    Boolean activated;

    @Column
    String confirmationString;
    
    @Column
    String email;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getConfirmationString() {
		return confirmationString;
	}

	public void setConfirmationString(String confirmationString) {
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

	public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
