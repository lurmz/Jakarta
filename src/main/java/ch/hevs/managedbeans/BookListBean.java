package ch.hevs.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.Publisher;
import ch.hevs.businessobject.Writer;
import ch.hevs.libraryservice.Library;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

// annotations
@ManagedBean
@Named("bookListBean")
@ViewScoped
public class BookListBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long selectedCategory;
	private String searchKeyword;
	private List<Book> books = new ArrayList<>();
	private List<Category> categories = new ArrayList<>();
	private List<Writer> writers = new ArrayList<>();
	private List<Publisher> publishers = new ArrayList<>();
	private Library libraryService;
	
	@PostConstruct
    public void init() {
        try {
            InitialContext ctx = new InitialContext();
            libraryService = (Library) ctx.lookup("java:global/63-41-MINIPROJECT-BOOK-0.0.1-SNAPSHOT/LibraryBean!ch.hevs.libraryservice.Library");
            fetchAllBooks();
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup library service", e);
        }
    }
	
	public void fetchAllBooks() {
        books = libraryService.getBooks();
    }
	
	// search and filter
	public void searchBooks() {
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            books = libraryService.getBooksByName(searchKeyword);
        } else {
            fetchAllBooks();
        }
    }
	
    public void filterByCategory() {
        if (selectedCategory != null && selectedCategory > 0) {
            books = libraryService.getBooksByCategory(selectedCategory);
        } else {
            fetchAllBooks(); 
        }
    }

    // getter and setter
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
    
    public List<Book> getBooks() {
    	return books;
    }

	public List<Category> getCategories() {
        categories = libraryService.getCategories();
		return categories;
	}

	public List<Writer> getWriters() {
        writers = libraryService.getWriters();
		return writers;
	}

	public List<Publisher> getPublishers() {
        publishers = libraryService.getPublishers();
		return publishers;
	}
	
	public Long getSelectedCategory() {
		return selectedCategory;
	}
	
	public void setSelectedCategory(Long selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
    
}
