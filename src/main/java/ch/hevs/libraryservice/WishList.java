package ch.hevs.libraryservice;

import java.util.List;

import ch.hevs.businessobject.Book;
import jakarta.ejb.Local;

// annotation
@Local
public interface WishList {

	// "Insight into your wishlist"
	void addBook(Book book);
	void deleteBook(Book book);
	List<Book> getWishList();
	
}
