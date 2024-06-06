# Course 63-41: Jakarta EE Mini Project
 
## Students: 
  - 604_FT_F: Luana Ramirez
  - 604_FT_D: Johanna Summermatter
 
# Build project:
  - Delete your existing database and start it
  - Add two new user to your database:
    -   user: "bookworm" pw: "bookwormP"
    -   user: "librarian" pw: "librarianP"
  - Right-click on the project and select "Maven build..." and enter "wildfly:deploy" (the JUnit Tests are made during the build)
 
# The URL of our application is:
  - http://localhost:8080/63-41-MINIPROJECT-BOOK-0.0.1-SNAPSHOT/faces/welcome.xhtml
 
 
# General information about our project:


## Topic: 
  - Book / Library

## Entities: 
  - Book
  - Writer
  - Category
  - Publisher
 
## Used technologies:
 
### Presentation layer:
  - JSF (including managed beans and facelets): **mandatory**
 
### Business/service layer:
  - Stateless EJB: **mandatory**
  - Stateful EJB: **to be chosen**
  - Reference to EJBs: JNDI **mandatory**
 
### Persistence layer (JPA):
  - Entities: **mandatory**
  - Entity Manager, PC: **mandatory**
  - Mapping associations and relations: **mandatory**
  - Transitive persistence (i.e. cascade): **mandatory**
  - JPQL: **mandatory**
  - Inheritance: **to be chosen**
  - Embedding: **to be chosen**
 
### General: 
  - Security: **to be chosen**
 
## Implemented functionalities:
  - Get Books from the database
  - Get Writers from the database
  - Get Categories from the database
  - Get Publishers from the database
  - Get Books and their relations from the database
  - Have an image preview of the book
  - Have two different user (librarian and bookworm) with different access
  - Stateful implementation of a wishlist for the bookworms
  - Add books for the librarians
  - Delete books / writers for the librarians
  - Automatic Populatation of the database using a Json file for the data
  - Used Bootstrap and own style to improve the design