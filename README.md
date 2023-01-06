# Hotel Reservation Application

This Java application is written from scratch to allow customers to create 
new reservations at a hotel. The application architecture is layered into a Model
layer for basic data classes, stateful Service for data storage functionality that the 
Resource layer can take advantage of, Resources for providing a backend API for
the application's interface, and, finally, the command line menu for interacting
with the user. No database was used, instead the Singleton design pattern guaranteed
one instance of a class and data was persisted using a Java Collection data structure.

### Stack 
- Java version 19
- Apache Maven to build project. Used `mvn archetype:generate` to generate project structure
- JUnit 5 to write unit tests
- Mockito to mock Service class
- IntelliJ is the recommended IDE for running this code

### How to Run

Run the following terminal command in the same directory as `hotelreservation-1.0-SNAPSHOT-jar-with-dependencies.jar`.

```
java -jar hotelreservation-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Note on Project

The test suite in `src/test/java` is in particular very comprehensive.
For example, there are parameterized unit tests, standard unit tests, and 
a fake implemented using Mockito.

### Note on Branching History

`dev` branch is to save changes when convenient.
`main` branch is for milestones in progress, and is meant to be kept
as clean as possible. `dev-src` and `dev-testsuite` are not actively used.

