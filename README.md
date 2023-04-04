# Employee-API
This project is my first of many using Java + Maven + MySQL (Docker Container) + Spring Web + Hibernate.

The purpose of this API is to provide RESTful API for managing (CRUD operations) employees. 

Application is build using 3-Layer Architecture:
1. DAO (Repository) - Persistance Layer - provides comminication with Data Base.
2. Service - Business Logic - implementation of the methods.
3. Controller - API - provides responses for HTTP requests.

Since this project won't have a frontend, all functionalities will be tested in Postman.  



*The steps taken to create the project are outlined below, along with an explanation of the technology.*

## Step #1:
- Generate project with Spring Initializr - Dependencies:  
  a) Lombok - Reduce boilerplate code.  
  b) Spring Web - Build RESTful app.  
  c) Spring Data JPA - conectivity with DB + Hibernate.  
  d) MySQL Driver.

REST API:   
 * Network architecture style and design pattern.  
 * Used for the creation of API for communicating between clients and servers.  
 * Operates on standard HTTP protocols (GET, POST, PUT, DELETE -> CRUD operations).   
 * Defines an interface that is independent of programming language, platform or device - it can be used with any system that supports HTTP protocol.   
 * The REST API allows for separation of user interface from server implementation and provides ease of integration with other applications.  
  
MVC - Model-View-Controller:
an architectural pattern used in software engineering to organize code into three main components:
1.	Model - represents data and business logic of the application.
2.	View – represents the presentation layer, or what the user sees.
3.	Controller – controls the flow of data between the model and view and responds to user action.  

By separating these three components, applications become more modular, easier to test and maintain.  
Additionally, changes to one part of the application do not affect the others, making it easier to develop and modify code.


Java Persistence API (JPA):  
 * It is a standard specification for Object-Relational Mapping (ORM) frameworks in Java  
 * Provides a set of interfaces and annotations that allow developers to map Java classes to relational database tables and perform database operations using object-oriented programming techniques  
 * JPA is built on top of the Java Database Connectivity (JDBC) API and provides a high-level, object-oriented API for managing database interactions
 * Allows developers to write database-independent code and supports multiple database vendors
 * Defines a set of annotations that developers can use to map Java classes to database tables and specify relationships between entities
 * provides a query language called JPQL (Java Persistence Query Language) that developers can use to perform complex database queries
 * Hibernate is one of the most popular JPA implementations, but there are other implementations available as well, such as EclipseLink and Apache OpenJPA.

is a Java specification for managing relational data in applications using Object-Relational Mapping (ORM) techniques.JPA provides a high-level abstraction layer over relational databases, allowing developers to work with Java objects instead of SQL statements. With JPA, developers can use Java annotations to define how Java classes should be mapped to database tables, specify relationships between entities, and perform CRUD (Create, Read, Update, Delete) operations on database records.JPA also provides support for caching, transaction management, and query language. JPA is part of the Java Enterprise Edition (Java EE) specification, but it can also be used in Java Standard Edition (Java SE) applications with the help of third-party libraries such as Hibernate, EclipseLink, and OpenJPA.  

Spring Data is a part of the Spring Framework that provides an easier  and more consistent approach to work with data access technologies, such  as databases. It offers abstractions and APIs that simplify data access  code and reduce boilerplate. Spring Data includes modules for various  data access technologies and supports features like automatic repository  generation and query creation. In summary, Spring Data helps developers  write cleaner, maintainable, and more productive data access code in  Java applications.  

Hibernate is a Java-based ORM (Object-Relational Mapping) framework that simplifies the process of mapping Java objects to relational database tables. It provides a high-level abstraction layer over relational databases, allowing developers to work with Java objects instead of SQL statements.Hibernate uses Java annotations or XML mappings to define the relationship between Java objects and database tables. It provides features like lazy loading, caching, and transaction management, making it easier to work with large datasets and complex database relationships.Hibernate is widely used in enterprise Java applications, especially those that use Spring Framework, due to its seamless integration with Spring. It supports a variety of databases, including MySQL, Oracle, PostgreSQL, and SQL Server.

