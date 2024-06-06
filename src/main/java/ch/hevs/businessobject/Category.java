package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

// annotation
@Entity 
public class Category extends ParentObject {

	// relation
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
		b.setCategory(this);
	}
	
	//constructors
	public Category(String name) {
		super(name);
	}
	
	public Category() {
		super();
	}

}