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
import ch.hevs.exception.LibraryException;
import ch.hevs.libraryservice.Library;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@ManagedBean
@Named("libraryManagementBean")
@SessionScoped
public class LibraryManagementBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Book book;
    private List<Category> categories = new ArrayList<>();
    private List<Writer> writers = new ArrayList<>();
    private List<Publisher> publishers = new ArrayList<>();
    private long selectedCategoryId;
    private long selectedWriterId;
    private long selectedPublisherId;
    private Library library;
    private String errorMsgBookWorm = "";

    @PostConstruct
    public void init() throws NamingException {
        InitialContext ctx = new InitialContext();
        library = (Library) ctx
                .lookup("java:global/63-41-MINIPROJECT-BOOK-0.0.1-SNAPSHOT/LibraryBean!ch.hevs.libraryservice.Library");
        book = new Book();
        refreshLists();
    }

    public String addBook() throws NamingException {
        try {
            Category category = library.findCategoryById(selectedCategoryId);
            book.setCategory(category);

            Writer writer = library.findWriterById(selectedWriterId);
            book.setWriter(writer);

            Publisher publisher = library.findPublisherById(selectedPublisherId);
            book.setPublisher(publisher);

            checkRequest();

            library.addBook(book);
            book = new Book();
            refreshLists();
            return "success?faces-redirect=true";
        } catch (LibraryException e) {
            errorMsgBookWorm = e.getMessage();
            return "noaccess?faces-redirect=true";
        }
    }

    public String deleteBook(long bookid) {
        try {
            library.deleteBook(bookid);
            refreshLists();
            return "books?faces-redirect=true";
        } catch (LibraryException e) {
            errorMsgBookWorm = e.getMessage();
            return "noaccess?faces-redirect=true";
        }
    }

    public String deleteWriter(long writerid) {
        try {
            library.deleteWriter(writerid);
            refreshLists();
            return "writers?faces-redirect=true";
        } catch (LibraryException e) {
            errorMsgBookWorm = e.getMessage();
            return "noaccess?faces-redirect=true";
        }
    }

    // helper method
    public void checkRequest() {
        if (book.getName() == null || book.getName().isEmpty()) {
            book.setName("Unknown");
        }
        if (book.getPrice() <= 0) {
            book.setPrice(0.0f);
        }
        int currentYear = java.time.Year.now().getValue();
        if (book.getPublicationYear() < 0 || book.getPublicationYear() > currentYear) {
            book.setPublicationYear(0);
        }
        if (book.getNbrPage() <= 0) {
            book.setNbrPage(0);
        }
        if (book.getRating() <= 0 || book.getRating() > 5) {
            book.setRating(0);
        }
        if (book.getImgURL() == null || book.getImgURL().isEmpty()) {
            book.setImgURL("https://s.gr-assets.com/assets/nophoto/book/111x148-bcc042a9c91a29c1d680899eff700a03.png");
        }
    }
    
    private void refreshLists() {
        categories = library.getCategories();
        writers = library.getWriters();
        publishers = library.getPublishers();
    }

    // getter and setter
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(long selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public long getSelectedWriterId() {
        return selectedWriterId;
    }

    public void setSelectedWriterId(long selectedWriterId) {
        this.selectedWriterId = selectedWriterId;
    }

    public long getSelectedPublisherId() {
        return selectedPublisherId;
    }

    public void setSelectedPublisherId(long selectedPublisherId) {
        this.selectedPublisherId = selectedPublisherId;
    }

    public String getErrorMsgBookWorm() {
        return errorMsgBookWorm;
    }

    public void setErrorMsgBookWorm(String errorMsgBookWorm) {
        this.errorMsgBookWorm = errorMsgBookWorm;
    }
    
}