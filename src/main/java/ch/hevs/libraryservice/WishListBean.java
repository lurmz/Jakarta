package ch.hevs.libraryservice;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.businessobject.Book;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateful;

// annotation
@Stateful
@RolesAllowed(value = { "bookworm" })
public class WishListBean implements WishList {
	
	// attributes
	private List<Book> books;
	
	@PostConstruct
	public void initialize() {
		books = new ArrayList<Book>();
	}

	@Override
	public void addBook(Book b) {
		books.add(b);
	}

	@Override
	public void deleteBook(Book b) {
		books.remove(b);
	}

	@Override
	public List<Book> getWishList() {
		return books;
	}
	
	@PreDestroy
	public void clear() {
		books = null;
	}

}
