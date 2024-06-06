package ch.hevs.businessobject;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

//annotation
@Entity 
public class Writer extends ParentObject{
	
	// attribute
	@Embedded
	private ContactInfo contactInfo;
		
	// relation
	@OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Book> books = new ArrayList<Book>();

	// getter and setter
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void setBook(Book b) {
		books.add(b);
		b.setWriter(this);
	}
	
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	//constructors
	public Writer() {
	}
	
	public Writer(String name) {
		super(name);
	}
	
}
