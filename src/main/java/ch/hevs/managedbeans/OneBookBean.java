package ch.hevs.managedbeans;

import java.io.Serializable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Book;
import ch.hevs.libraryservice.Library;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("oneBookBean")
@SessionScoped
public class OneBookBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Library library;
	private Book selectedBook;
	private String returnPage;

	@PostConstruct
	public void init() {
		try {
			InitialContext ctx = new InitialContext();
			library = (Library) ctx.lookup("java:global/63-41-MINIPROJECT-BOOK-0.0.1-SNAPSHOT/LibraryBean!ch.hevs.libraryservice.Library");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public String viewBookDetails(Long bookId, String fromPage) {
		this.selectedBook = library.findBookById(bookId);
		this.returnPage = fromPage;
		return "oneBookDetails?faces-redirect=true";
	}

	// helper method
	public String goBack() {
		return returnPage;
	}
	
	// getter and setter
	public Book getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}
	
}
