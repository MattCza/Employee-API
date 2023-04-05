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
 * It is a standard specification for Object-Relational Mapping (ORM) frameworks in Java.  
 * Provides a set of interfaces and annotations that allows to map Java classes to relational database tables and perform database operations using object-oriented programming techniques.  
 * JPA is built on top of the Java Database Connectivity (JDBC) API and provides a high-level, object-oriented API for managing database interactions.  
 * Allows to write database-independent code and supports multiple database vendors.  
 * Provides a query language called JPQL (Java Persistence Query Language) to perform complex database queries.  
 * Hibernate is one of the most popular JPA implementations.  

Java Database Connectivity (JDBC):
 * Java API that allows applications to interact with relational DB - such as MySQL, PostgreSQL, Oracle, Microsoft SQL Server.  
 * Provides a set of interfaces and classes to execute SQL statements, retrive and manipulate data, and manage DB connections.  
 * Provides standardized way for Java apps to interact with DB, making it possible to write DB-independent code.  
 * Allows to write efficient and secure code - you can use prepared statements to avoid SQL injection attacks.  
 
Spring Data:
 * Part of the Spring Framework, that provides an easire and more consistent approach to work with data access technologies.  
 * Offers abstractions and APIs that simplify data access code and reduce boilerpate.  

Hibernate:
 * Java-based ORM framework, that simplifies the process of mapping objects to relational DB tables.  
 * Provides high-level abstraction layer over relational databases, allowing to work with objects instead of SQL statements.  
 * Uses Java annotations or XML mapping - to define the relationship between object and tables.  
 * Provides lazy loading, caching, and transaction management - useful with large datasets and complex DB relationships.  

