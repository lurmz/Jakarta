package ch.hevs.test;
 
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
 
import org.junit.Test;
 
import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.ContactInfo;
import ch.hevs.businessobject.Publisher;
import ch.hevs.businessobject.Writer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;
 
public class PopulateDBTest extends TestCase {
 
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws SQLException, ClassNotFoundException {
		EntityTransaction tx = null;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU_unitTest");
            EntityManager em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Map<String, Object> data = JsonDataLoader.loadJsonData("bookdata.json");
 
            if (data != null) {
				List<Map<String, String>> categories = (List<Map<String, String>>) data.get("categories");
                List<Map<String, Object>> books = (List<Map<String, Object>>) data.get("books");
                List<Map<String, Object>> writers = (List<Map<String, Object>>) data.get("writers");
                List<Map<String, Object>> publishers = (List<Map<String, Object>>) data.get("publishers");
                Map<String, List<Map<String, Object>>> associations = (Map<String, List<Map<String, Object>>>) data.get("associations");
 
                // add the categories
                for (Map<String, String> categoryData : categories) {
                    Category category = new Category(categoryData.get("name"));
                    em.persist(category);
                }
 
                // add the books
                for (Map<String, Object> bookData : books) {
                    Book book = new Book(
                            (String) bookData.get("name"),
                            ((Number) bookData.get("price")).floatValue(),
                            (int) bookData.get("year"),
                            (int) bookData.get("pages"),
                            (int) bookData.get("rating"),
                            (String) bookData.get("imageUrl")
                    );
                    em.persist(book);
                }
                // add the writers
                for (Map<String, Object> writerData : writers) {
                    String name = (String) writerData.get("name");
                    String email = (String) writerData.get("email");
                    String phoneNumber = (String) writerData.get("phoneNumber");
 
                    Writer writer = new Writer(name); 
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setEmail(email);
                    contactInfo.setPhoneNumber(phoneNumber);
 
                    writer.setContactInfo(contactInfo); 
                    em.persist(writer);
                }
 
                // add the publishers
                for (Map<String, Object> publisherData : publishers) {
                    String name = (String) publisherData.get("name");
                    String email = (String) publisherData.get("email");
                    String phoneNumber = (String) publisherData.get("phoneNumber");
 
                    Publisher publisher = new Publisher(name); 
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setEmail(email);
                    contactInfo.setPhoneNumber(phoneNumber);
 
                    publisher.setContactInfo(contactInfo); 
                    em.persist(publisher);
                }
                em.flush();
 
                // add the relations
                List<Map<String, Object>> categoryBooks = associations.get("category_books");
                for (Map<String, Object> cb : categoryBooks) {
                    String categoryName = (String) cb.get("category");
                    List<String> bookTitles = (List<String>) cb.get("books");
 
                    Category category = em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                            .setParameter("name", categoryName)
                            .getSingleResult();
 
                    for (String bookTitle : bookTitles) {
                        Book book = em.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class)
                                .setParameter("name", bookTitle)
                                .getSingleResult();
                        category.setBook(book);
                    }
                }
 
                List<Map<String, Object>> writerBooks = associations.get("writer_books");
                for (Map<String, Object> wb : writerBooks) {
                    String writerName = (String) wb.get("writer");
                    List<String> bookTitles = (List<String>) wb.get("books");
 
                    Writer writer = em.createQuery("SELECT w FROM Writer w WHERE w.name = :name", Writer.class)
                            .setParameter("name", writerName)
                            .getSingleResult();
 
                    for (String bookTitle : bookTitles) {
                        Book book = em.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class)
                                .setParameter("name", bookTitle)
                                .getSingleResult();
                        writer.setBook(book);
                    }
                }
 
                List<Map<String, Object>> publisherBooks = associations.get("publisher_books");
                for (Map<String, Object> pb : publisherBooks) {
                    String publisherName = (String) pb.get("publisher");
                    List<String> bookTitles = (List<String>) pb.get("books");
 
                    Publisher publisher = em.createQuery("SELECT p FROM Publisher p WHERE p.name = :name", Publisher.class)
                            .setParameter("name", publisherName)
                            .getSingleResult();
 
                    for (String bookTitle : bookTitles) {
                        Book book = em.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class)
                                .setParameter("name", bookTitle)
                                .getSingleResult();
                        publisher.setBook(book);
                    }
                }
 
                tx.commit();
            }
 
            em.close();
            emf.close();
        }
}