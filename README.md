# Hotel Reservation Application

This Java application is written from scratch to allow customers to create 
new reservations at a hotel. The web application architecture is layered into Models
for basic data classes, stateful Services for data storage functionality that the 
Resource layer can take advantage of, Resources for providing a backend API for
the application's interface, and, finally, the command line menu for interacting
with users. No database was used, instead the Singleton design pattern guaranteed
one instance of a class and data was persisted using a Java Collection data structure.

### Stack 
- Java version 19
- Apache Maven to build project. Used `mvn archetype:generate` to generate project structure
- JUnit 5 to write unit tests
- Mockito to mock Service class
- IntelliJ is the recommended IDE for running this code

### Note on Branching History

`dev` branch is to save changes when convenient, and does not necessarily compile.
`main` branch always compiles. `dev-src` and `dev-testsuite` are not actively used.