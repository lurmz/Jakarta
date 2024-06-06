package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

// annotation
@Entity
public class Publisher extends ParentObject{
	
	// attribute
	@Embedded
	private ContactInfo contactInfo;
	
	// relation
	@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<Book>();

		
	// getter and setter
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void setBook(Book b) {
		this.books.add(b);
		b.setPublisher(this);
	}
		
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	//constructors
	public Publisher(String name) {
		super(name);
	}
	
	public Publisher() {
		super();	
	}

}
