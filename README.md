# Employee-API
This project is my first of many using Java + Maven + MySQL (Docker Container) + Spring Web + Hibernate.

The purpose of this API is to provide RESTful API for managing (CRUD operations) employees. 

Application is build using 3-Layer Architecture:
1. DAO (Repository) - Persistance Layer - provides comminication with Data Base.
2. Service - Business Logic - implementation of the methods.
3. Controller - API - provides responses for HTTP requests.

Since this project won't have a frontend, all functionalities will be tested in Postman.


## Step #1:
- Generate project with Spring Initializr - Dependencies:
  a) Lombok - Reduce boilerplate code.
  b) Spring Web - Build RESTful app.
  c) Spring Data JPA - conectivity with DB + Hibernate.
  d) MySQL Driver.
