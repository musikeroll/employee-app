# employee-app

This is a Spring Boot app written in Kotlin. It uses Spring Web, JPA and H2 Database. 
It also uses jjwt library to generate and validate JWT tokens. Initial list of Employees are loaded to H2 database via CommandLineRunner.

It exposes four (4) end points:

### /employee/hello
* Outputs the text 'Hello World'. Used to check if the app is really running.

### /employee/login
* POST method
* Accepts username and password as input then checks with H2 database
* If found, it generates a JWT token as return as a response in the header
* Everything else, it just returns a 200 OK response

### /employee/all
* GET method
* Checks the request header for 'Authorization'
* Validates the bearer token if it can be parsed by jjwt library 
* Checks the token if it's not yet expired
* If valid, returns the list of Employees found in the H2 database
* Everything else, returns a 401 UNAUTHORIZED

### /employee/{id}
* GET method
* Checks the request header for 'Authorization'
* Validates the bearer token if it can be parsed by jjwt library
* Checks the token if it's not yet expired
* Returns the Employee record associated with ID if found in the H2 database
* Returns a 200 OK if there's no record for it
* Everything else, returns a 401 UNAUTHORIZED


The main challenge encountered is the usage of jjwt library. 
I initially used a lower version (0.11.2). With this, things don't work consistently,
Especially the issuedAt and expiration date values are always wrong. Then I learned
about the latest version (0.12.5), and from there, everything went smooth sailing.