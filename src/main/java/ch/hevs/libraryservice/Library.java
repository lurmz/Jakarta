package ch.hevs.libraryservice;

import java.util.List;

import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.Publisher;
import ch.hevs.businessobject.Writer;
import ch.hevs.exception.LibraryException;
import jakarta.ejb.Local;

// annotation
@Local
public interface Library {
	
	// "Insight into our data" 
	List<Book> getBooks();
	List<Writer> getWriters();
	List<Category> getCategories();
	List<Publisher> getPublishers();
	
	// search and filter 
	List<Book> getBooksByName(String name);
	List<Book> getBooksByCategory(Long categoryId);
	
	// "Management of the Library"
	void addBook(Book book) throws LibraryException;
	void deleteBook(long bookid) throws LibraryException;
	void deleteWriter(long writerid) throws LibraryException;
	
	// helper methods
	Book findBookById(Long bookId);
	Category findCategoryById(Long categoryId);
	Writer findWriterById(Long writerId);
	Publisher findPublisherById(Long publisherId);
	
}
