package ch.hevs.libraryservice;

import java.util.List;

import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.Publisher;
import ch.hevs.businessobject.Writer;
import ch.hevs.exception.LibraryException;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.TypedQuery;

// annotation
@Stateless
public class LibraryBean implements Library {

	// Attribute
	private String errorMsg = "Oops, Only Librarians Can Rearrange the Shelves! Hey there, Bookworm! "
			+ "Looks like you've wandered into the librarian's lair. Don't worry, the books are safe, but "
			+ "adding or removing them is a bit out of your reach. Why not explore the shelves for some hidden gems instead?";

	// EntityManager
	@PersistenceContext(unitName = "bookPU", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	// SessionContext
	@Resource
	private SessionContext ctx;

	// -------------------------------------------------------------------------------------------
	// "Insight into our data"
	// -------------------------------------------------------------------------------------------
	@Override
	public List<Book> getBooks() {
		TypedQuery<Book> query = em.createQuery(
				"SELECT b FROM Book b " + "JOIN FETCH b.writer " + "JOIN FETCH b.publisher " + "JOIN FETCH b.category",
				Book.class);
		return query.getResultList();
	}

	@Override
	public List<Category> getCategories() {
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		return query.getResultList();
	}

	@Override
	public List<Publisher> getPublishers() {
		TypedQuery<Publisher> query = em.createQuery("SELECT p FROM Publisher p", Publisher.class);
		return query.getResultList();
	}

	@Override
	public List<Writer> getWriters() {
		TypedQuery<Writer> query = em.createQuery("SELECT w FROM Writer w", Writer.class);
		return query.getResultList();
	}

	// -------------------------------------------------------------------------------------------
	// search and filter
	// -------------------------------------------------------------------------------------------
	@Override
	public List<Book> getBooksByName(String name) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name", Book.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}

	@Override
	public List<Book> getBooksByCategory(Long categoryId) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.category.id = :categoryId", Book.class);
		query.setParameter("categoryId", categoryId);
		return query.getResultList();
	}

	// -------------------------------------------------------------------------------------------
	// "Management of the Library"
	// -------------------------------------------------------------------------------------------
	@Override
	public void addBook(Book book) throws LibraryException {
		if (ctx.isCallerInRole("librarian")) {
			em.merge(book);
		} else {
			throw new LibraryException(errorMsg);
		}
	}

	@Override
	public void deleteBook(long bookid) throws LibraryException {
		if (ctx.isCallerInRole("librarian")) {
			Book book = findBookById(bookid);
			em.remove(book);
		} else {
			throw new LibraryException(errorMsg);
		}
	}

	@Override
	public void deleteWriter(long writerid) throws LibraryException {
		if (ctx.isCallerInRole("librarian")) {
			Writer writer = findWriterById(writerid);
			em.remove(writer);
		} else {
			throw new LibraryException(errorMsg);
		}
	}

	// -------------------------------------------------------------------------------------------
	// helper methods
	// -------------------------------------------------------------------------------------------
	@Override
	public Book findBookById(Long bookId) {
		if (bookId == null) {
			return null;
		}

		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b " + "LEFT JOIN FETCH b.writer "
				+ "LEFT JOIN FETCH b.publisher " + "LEFT JOIN FETCH b.category " + "WHERE b.id = :id", Book.class);
		query.setParameter("id", bookId);
		return query.getSingleResult();
	}

	@Override
	public Category findCategoryById(Long categoryId) {
		if (categoryId == null) {
			return null;
		}

		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.id = :id", Category.class);
		query.setParameter("id", categoryId);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Writer findWriterById(Long writerId) {
		if (writerId == null) {
			return null;
		}

		TypedQuery<Writer> query = em.createQuery("SELECT w FROM Writer w WHERE w.id = :id", Writer.class);
		query.setParameter("id", writerId);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Publisher findPublisherById(Long publisherId) {
		if (publisherId == null) {
			return null;
		}

		TypedQuery<Publisher> query = em.createQuery("SELECT p FROM Publisher p WHERE p.id = :id", Publisher.class);
		query.setParameter("id", publisherId);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}