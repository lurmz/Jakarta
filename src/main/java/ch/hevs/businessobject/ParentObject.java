package ch.hevs.businessobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;

// annotation
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name="seq", sequenceName="my_seq", allocationSize=1)
public class ParentObject {

	// attributes
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
	private Long id;
	@Column(nullable = false)
	private String name;
	
	// getter and setter
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//constructors
	public ParentObject(String name) {
		this.name = name;
	}
	
	public ParentObject() {
		this.name = "Unknown";
	}
	
}
