package ch.hevs.businessobject;
 
import jakarta.persistence.Embeddable;
 
// annotation
@Embeddable
public class ContactInfo {
	
	// attribute
	private String email;
    private String phoneNumber;
    
    // getter and setter
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
	// constructor
    public ContactInfo() {
    	
    }
 
}