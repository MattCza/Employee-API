# Employee-API
This project is my first of many using Java + Maven + MySQL (Docker Container) + Spring Web + Hibernate.  
Backend in Docker container.  

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
Now, implement them in EmployeeService class and inject dependency from Repository.  

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

2 main methods of dependency injection:  
– Setter-based dependency injection -> used when you have optional parameters.  
– Constructor-based dependency injection -> used when you have mandatory parameters.  

@Service:  
Annotation for marking a class as a service class. A service class is a type of Spring-managed component that contains business logic or other functionality that can be used by other parts of the application. By annotating a class with @Service, Spring will automatically detect and create an instance of the service class, and it will be available for dependency injection throughout the application. This allows other components, such as controllers, to use the functionality provided by the service class without having to manually instantiate it or manage its life-cycle. @Service is typically used in conjunction with other Spring annotations, such as @Autowired, to inject dependencies into the service class.  

@Autowired:  
Annotation that is used to automatically inject dependencies into a class. When you annotate a field, constructor, or method with @Autowired, Spring will automatically identify the dependencies of the class and inject them at runtime.  
Spring with version 4.3, if a class, which is configured as a Spring bean, has only one constructor, the @Autowired annotation can be omitted and Spring will use that constructor and inject all necessary dependencies.  

Bean:  
The objects that form the backbone of application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.  
Spring IoC container is the program that injects dependencies into an object and make it ready for our use.

---
## Step 6:  
Create Controller class - to provide mapping of end user HTTP requests to DB CRUD operations.    


```
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }
}
```

@RestController:  
Convenient annotation that combines @Controller and @ResponseBody, which eliminates the need to annotate every request handling method of the Controller class with the @ResponseBody annotation.  
@RestController is a special annotation that is used to mark a class as a controller for processing RESTful web requests. It indicates that the methods in that class are responsible for handling incoming HTTP requests and returning the appropriate HTTP responses. When a request is made to the URL mapped to a @RestController method, the method is invoked and its return value is converted into an HTTP response, typically in JSON format.  

@RequestMapping:  
When you add the @RequestMapping annotation to a method, it specifies the URL path that should be used to invoke that method when a matching HTTP request is received. You can also specify other attributes of the HTTP request, such as the HTTP method, request headers, and query parameters, using additional parameters of the @RequestMapping annotation.  

@PostMapping:  
When you add the @PostMapping annotation to a method, it specifies that the method should handle HTTP POST requests to a specific URL path. The @PostMapping annotation is a specialized version of the more general @RequestMapping annotation, which can be used to map any type of HTTP request to a method.  

ResponseEntity:  
You can use ResponseEntity as the return type of a controller method to customize the HTTP response that is returned to the client. By using ResponseEntity, you can set the HTTP status code, headers, and body of the response based on the result of your controller method.  

@RequestBody:  
Annotation that is used to bind the HTTP request body to an object in a controller method. When you add the @RequestBody annotation to a method parameter, Spring will automatically map the request body to the specified parameter type. This is particularly useful when processing JSON or XML payloads in HTTP requests, as it allows you to easily convert the payload into a Java object.  

---
## Step 7:  
Create a ResourceNotFoundException class to handle events where the user tries to get an object that does not exist.  

```
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public Object getFieldValue() {return fieldValue;}
    public String getResourceName() {return resourceName;}
    public String getFieldName() {return fieldName;}
}
```

@ResponseStatus
Annotation that can be used to specify the HTTP status code to be returned by a controller method in case of an exception. By default, when an exception is thrown from a controller method, Spring MVC returns a 500 Internal Server Error status code. However, in some cases, you may want to return a different HTTP status code to the client based on the nature of the exception. As in the example above (value = HttpStatus.NOT_FOUND).  

---
## Step 8:  
Create Get Employee by ID.  

Service:  
```
public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "ID", id)
        );
    }
```
Optional, orElseThrow()  
In Java 8 and later, Optional is a container object that  may or may not contain a value. It is used to avoid null pointer  exceptions when working with objects that may be null. The orElseThrow() method is a method of the Optional class that allows you to throw an exception if the Optional object is empty.  
The orElseThrow() method takes a Supplier argument that provides the exception to be thrown. The Supplier takes no arguments and returns an exception object. This allows you to create the exception object only if it is needed, which can be more efficient than creating the exception object before calling the orElseThrow() method.  


Controller:  
```
@GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) {
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }
```


---
## Step 9:  
Update Employee by ID. It can be done better - what if there are null values? Not Post but Update :) TODO  

Service:  
```
public Employee updateEmployee(Employee employee, long id) {
        Employee currentEmployee = getEmployeeById(id);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setEmail(employee.getEmail());
        currentEmployee.setPhoneNumber(employee.getPhoneNumber());
        saveEmployee(currentEmployee);
        return currentEmployee;
    }
```

Controller:  
```
@PostMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long employeeId){
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, employeeId), HttpStatus.CREATED);
    }
```
  
---
## Step 10:  
Delete Employee by ID.  

Service:  
```
public void deleteEmployee(long id) {
        getEmployeeById(id);
        employeeRepository.deleteById(id);
    }
```
  
Controller:  
```
@DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
    }
```

---
## Step 10:  
Docker container Backend.  

- Build application:  
```
./mvnw package
```
An error with Java version mismatch popped up - so I changed the pom.xml file to Java 17.  

Run the app:  
```
java -jar ./target/Employee-API-0.0.1-SNAPSHOT.jar
```



