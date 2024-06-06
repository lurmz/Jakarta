package ch.hevs.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Book;
import ch.hevs.libraryservice.WishList;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJBAccessException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@ManagedBean
@Named("wishlistBean")
@SessionScoped
public class WishlistBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WishList wishList;
	private String errorMsgLibrarian = "Shhh! The Wishlist is for Dreamers Only! Hello Librarian! "
			+ "You're doing a great job organizing these books, but the wishlist area is reserved "
			+ "for the bookworms to dream a little. How about you check out the new arrivals instead?";
	private String errorMsgDisplayed;
	
	@PostConstruct
    public void init() {
        try {
            InitialContext ctx = new InitialContext();
            wishList = (WishList) ctx.lookup("java:global/63-41-MINIPROJECT-BOOK-0.0.1-SNAPSHOT/WishListBean!ch.hevs.libraryservice.WishList");
        } catch (NamingException e) {
            e.getMessage();
        }
    }

	public List<Book> getWishListBooks() {
		try {
			return wishList.getWishList();
		} catch (EJBAccessException e) {
			errorMsgDisplayed = errorMsgLibrarian;
        	redirectErrorPage("noaccess.xhtml");
        	return Collections.emptyList();
		}
	}

	public void removeBookFromWishList(Book b) {
		wishList.deleteBook(b);
	}

	public void addBookToWishList(Book book) {
		try {
            if (!isBookInWishList(book)) {
                wishList.addBook(book);
            }
        } catch (EJBAccessException e) {
        	errorMsgDisplayed = errorMsgLibrarian;
        	redirectErrorPage("noaccess.xhtml");
        }
	}
	
	// helper method
	public boolean isBookInWishList(Book book) {
		List<Book> books = wishList.getWishList();
        for (Book book2 : books) {
        	if (book2.getName().equalsIgnoreCase(book.getName())) {
        		return true;
        	}
        }
        return false;
    }
	
	private void redirectErrorPage(String page) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.getResponseComplete()) {
            try {
                facesContext.getExternalContext().redirect(page);
                facesContext.responseComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	// getter and setter
	public String getErrorMsgDisplayed() {
		return errorMsgDisplayed;
	}

	public void setErrorMsgDisplayed(String errorMsgDisplayed) {
		this.errorMsgDisplayed = errorMsgDisplayed;
	}	

}