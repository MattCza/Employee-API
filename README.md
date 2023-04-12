# Employee-API
This project is my first of many using Java + Maven + MySQL (Docker Container) + Spring Web + Hibernate.

The purpose of this API is to provide RESTful API for managing (CRUD operations) employees. 

Application is build using 3-Layer Architecture:
1. DAO (Repository) - Persistence Layer - provides communication with Data Base.
2. Service - Business Logic - implementation of the methods.
3. Controller - API - provides responses for HTTP requests.

Since this project won't have a frontend, all functionalities will be tested in Postman.  


---
*The steps taken to create the project are outlined below, along with an explanation of the technology.*

## Step #1:
- Generate project with Spring Initializr - Dependencies:  
  a) Lombok - Reduce boilerplate code.  
  b) Spring Web - Build RESTful app.  
  c) Spring Data JPA - connectivity with DB + Hibernate.  
  d) MySQL Driver.
---
REST API:   
 * Network architecture style and design pattern.  
 * Used for the creation of API for communicating between clients and servers.  
 * Operates on standard HTTP protocols (GET, POST, PUT, DELETE -> CRUD operations).   
 * Defines an interface that is independent of programming language, platform or device - it can be used with any system that supports HTTP protocol.   
 * The REST API allows for separation of user interface from server implementation and provides ease of integration with other applications.  
 * Uses stateless communication between the client and the server. This means that each request from the client to the server contains all the information necessary for the server to understand the request, and that the server does not maintain any information about the client's state between requests.  
---  
MVC - Model-View-Controller:
an architectural pattern used in software engineering to organize code into three main components:
1.	Model - represents data and business logic of the application.
2.	View – represents the presentation layer, or what the user sees.
3.	Controller – controls thesuch as manually applying database schema changes using scripts, flow of data between the model and view and responds to user action.  

By separating these three components, applications become more modular, easier to test and maintain.  
Additionally, changes to one part of the application do not affect the others, making it easier to develop and modify code.

---
Java Persistence API (JPA):  
 * It is a standard specification for Object-Relational Mapping (ORM) frameworks in Java.  
 * Provides a set of interfaces and annotations that allows to map Java classes to relational database tables and perform database operations using object-oriented programming techniques.  
 * JPA is built on top of the Java Database Connectivity (JDBC) API and provides a high-level, object-oriented API for managing database interactions.  
 * Allows to write database-independent code and supports multiple database vendors.  
 * Provides a query language called JPQL (Java Persistence Query Language) to perform complex database queries.  
 * Hibernate is one of the most popular JPA implementations.  
---
Java Database Connectivity (JDBC):
 * Java API that allows applications to interact with relational DB - such as MySQL, PostgreSQL, Oracle, Microsoft SQL Server.  
 * Provides a set of interfaces and classes to execute SQL statements, retrieve and manipulate data, and manage DB connections.  
 * Provides standardized way for Java apps to interact with DB, making it possible to write DB-independent code.  
 * Allows to write efficient and secure code - you can use prepared statements to avoid SQL injection attacks.  
---
Spring Data:
 * Part of the Spring Framework, that provides an easier and more consistent approach to work with data access technologies.  
 * Offers abstractions and APIs that simplify data access code and reduce boilerplate.  
---
Hibernate:
 * Java-based ORM framework, that simplifies the process of mapping objects to relational DB tables.  
 * Provides high-level abstraction layer over relational databases, allowing to work with objects instead of SQL statements.  
 * Uses Java annotations or XML mapping - to define the relationship between object and tables.  
 * Provides lazy loading, caching, and transaction management - useful with large datasets and complex DB relationships.  
---
## Step #2:  
Create DB in docker container and establish connection:  

  a) Start Docker:   
```
sudo systemctl start docker  
```

  b) Create Container:  
```
sudo docker run --name employee-api-mysql -e MYSQL_ROOT_PASSWORD=*** -d -p 3306:3306 mysql  
```

  c) Start created container:  
```
sudo docker start 6b2210afe132b9d72d2fa8e1b7a1d9f81ac7448973798d4edbe5311ebc74d768  
```

Connect to DB and create table named employee - create column id.  

---

Create connection to DB:  

  a) Inject dependency to pom.xml - to create .env file (more secure storing connection config.):  
```
<dependency>
	<groupId>me.paulschwarz</groupId>
	<artifactId>spring-dotenv</artifactId>
	<version>2.5.4</version>
</dependency>
```

  b) Create .env file and initialize variables - and make .env.example - for reference purpose:    
```
DB_USERNAME="root"  
DB_PASSWORD="***"  
DB_URL="jdbc:mysql://localhost"  
DB_PORT="3306"  
```

  c) Add line in .gitignore file to skip .env file.  
  
  d) Establish connection in application.properties file:  
```
spring.datasource.url=${env.DB_URL}:${env.DB_PORT}/employee?useSSL=false  
spring.datasource.username=${env.DB_USERNAME}  
spring.datasource.password=${env.DB_PASSWORD}  
```

And make Hibernate configuration:  
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect  
spring.jpa.hibernate.ddl-auto=update  
```

---

Hibernate dialect:  
Configuration setting that enables Hibernate to interact with different databases and handle the database-specific syntax and behavior differences in a standardized way.  

spring.jpa.hibernate.ddl-auto=update:   
Configuration property to automatically create or update database tables based on the entity classes defined in the application.  
ddl-auto=update is a convenient way to automatically create or update database tables during development phase. It is recommended to use a more controlled approach in production environments.  

---
### Error occurred:  
```
java.sql.SQLNonTransientConnectionException: Public Key Retrieval is not allowed   
```

Adding client option in mysql-connector resolved the problem:  
allowPublicKeyRetrieval=true  

Be careful! This options allows attacker for a MITM attack. Use this in testing environment only.  
After first connection you can remove this option.  

---  
## Step 3:  
Create representation of an entity to class.  

Use annotations:  
@Data - Lombock annotation - creates Getters, Setters, Constructors, toString, equals, hashCode.  
@Entity - JPA annotation - specifies that the class is an entity.  
@Table(name = "") - annotation specifies the table in the database with which this entity is mapped.  
@Id – specifies the primary key of the entity.  
@GeneratedValue – specifies the generation strategies for the values of primary keys.  
@Column – specifies the column name from DB.  

SQL naming convention is to divide words by underscore, and to use singular form, more info why at:  
https://stackoverflow.com/questions/338156/table-naming-dilemma-singular-vs-plural-names  

---
## Step 4:  
Create Repository that extends JPA Repository interface.  

```
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}  
```

JpaRepository:  
Interface provided by the Spring Data JPA library. It extends the CrudRepository interface and provides additional methods for querying data from a database.  
The JpaRepository interface defines methods for basic CRUD operations, such as save(), delete(), and findById(), as well as more advanced querying methods using JPA's query language or native SQL. It also supports pagination and sorting of data.  
To use it, create interface that extends JpaRepository, and specify the entity type and primary key type, and Spring Data JPA will generate the implementation at runtime.  

Spring Data JPA:  
Internally provides @Repository annotation so we don’t need to provide this annotation to EmployeeRepository interface. By default Spring Data JPA made JpaRepository methods transactional So we don’t need to add @Transactional annotation in Service class.  

@Repository:  
Indicates that a class is a repository, which is a data access object that provides CRUD operations for a specific entity type. When a class is annotated with @Repository, Spring's component-scanning mechanism will detect it and automatically create a bean instance of the repository class. This bean can then be injected into other Spring-managed beans using the @Autowired annotation or by defining it as a dependency in a Spring configuration file. The purpose of using @Repository is to provide a standardized way to define and use repositories in a Spring-based application.  

The repository pattern is a popular design pattern for managing persistence in a software application.  
It provides an abstraction of the data layer, and it is a way of centralising the handling of the domain objects.  

@Transactional:  
Annotation used to specify that a method or a class should be executed within a transaction. In other words, it is a way to ensure that a set of database operations are either committed together as a single unit of work, or rolled back if an error occurs during any part of the transaction.  


---
## Step 5:  
Create Service interface and its implementation.  

For naming convention I will use "IEmployeeService" name. Interface will help to keep track of the methods in service layer of Application.  
```
public interface IEmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
}
```

For now those 2 methods will do the work just fine.  
Now, implement them in EmployeeService class.  

```
@Service
public class EmployeeService implements IEmployeeService {
    private IEmployeeRepository employeeRepository;
    //@Autowired
    public EmployeeService(IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
```




