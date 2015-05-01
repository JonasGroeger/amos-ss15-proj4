package personal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employees")
public class Employee {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	int Id = 0;
    String FamilyName = "";
	String FirstName = "";
    String MaidenName = "";
    Date BirthDate;
    String PlaceOfBirth = "";
    String CountryOfBirth = "";
	String Street = "";
    String ZIPCode = ""; 
    String HouseNumber = "";
    String City = "";
    String Title ="";
    String SocialInsuranceNumber = "";
    // TODO: Implement Sex enum
    // TODO: Implement MaritalStatus enum
    // TODO: Implement DisabledStatus enum
    String Citizenship = "";
    String EmployerSocialSavingsId = "";
    String IBAN = "";
    String BIC = "";
    
    public Employee()
    {
    	
    }
    
    public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFamilyName() {
		return FamilyName;
	}
	public void setFamilyName(String familyName) {
		FamilyName = familyName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstNAme) {
		FirstName = firstNAme;
	}
	public String getMaidenName() {
		return MaidenName;
	}
	public void setMaidenName(String maidenName) {
		MaidenName = maidenName;
	}
	public Date getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}
	public String getPlaceOfBirth() {
		return PlaceOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		PlaceOfBirth = placeOfBirth;
	}
    public String getCountryOfBirth() {
		return CountryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		CountryOfBirth = countryOfBirth;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getZIPCode() {
		return ZIPCode;
	}
	public void setZIPCode(String zIPCode) {
		ZIPCode = zIPCode;
	}
	public String getHouseNumber() {
		return HouseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		HouseNumber = houseNumber;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getSocialInsuranceNumber() {
		return SocialInsuranceNumber;
	}
	public void setSocialInsuranceNumber(String socialInsuranceNumber) {
		SocialInsuranceNumber = socialInsuranceNumber;
	}
	public String getCitizenship() {
		return Citizenship;
	}
	public void setCitizenship(String citizenship) {
		Citizenship = citizenship;
	}
	public String getEmployerSocialSavingsId() {
		return EmployerSocialSavingsId;
	}
	public void setEmployerSocialSavingsId(String employerSocialSavingsId) {
		EmployerSocialSavingsId = employerSocialSavingsId;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getBIC() {
		return BIC;
	}
	public void setBIC(String bIC) {
		BIC = bIC;
	}
	
	public String getText()
	{
		// TO DO: Implement full export.
		return String.format(("Employee Name: %s %s\n" + 
	                          "Employee citizenship %s"),
				              this.FirstName,
				              this.FamilyName,
				              this.Citizenship);
	}
}
