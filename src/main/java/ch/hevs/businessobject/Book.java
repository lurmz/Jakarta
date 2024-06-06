package ch.hevs.businessobject;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

// annotation
@Entity
public class Book extends ParentObject{
	
	// attributes
	private float price;
	private int publicationYear;
	private int nbrPage;
	private int rating;
	private String imgURL;

	// relations
	@ManyToOne(fetch = FetchType.EAGER)
	private Writer writer;
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
	@ManyToOne(fetch = FetchType.EAGER)
	private Publisher publisher;

	// getter and setter
	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public int getNbrPage() {
		return nbrPage;
	}

	public void setNbrPage(int nbrPage) {
		this.nbrPage = nbrPage;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	
	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	//constructors
	public Book() {
		this.imgURL = "https://s.gr-assets.com/assets/nophoto/book/111x148-bcc042a9c91a29c1d680899eff700a03.png";
	}

	public Book(String name, float price, int publicationYear, int nbrPage, int rating, String imgURL) {
		super(name);
		this.price = price;
		this.publicationYear = publicationYear;
		this.nbrPage = nbrPage;
		this.rating = rating;
		this.imgURL = imgURL;
	}
	
}