# Hotel Reservation Application

This Java application allows customers to create new reservations at a hotel. 
The application architecture is layered into 
- Model for basic data classes
- Service for manipulating data and storing application state
- Resources for defining backend API
- command line menu for interacting with the user, can be swapped with a GUI as a project extension

No database was used, instead the Singleton design pattern guaranteed
one instance of class and data was persisted using relevant data structures.

I created this project to practice:
- Writing tests using Java unit test best practice naming, code coverage tool, object mocks
- Using `enums` for clean code and static type checking at compile time
- Using Optionals to avoid non-clean Null Pointer Exceptions, ternary operators and try-catch-finally for cleaner code
- specific access modifiers for maintainable code
- Java Date-Time API
- Maven build tool

Things I did not like was Mockito extends classes to create mock objects, so 
I had to break the Singleton design pattern by removing the final access modifier on the service classes.
Another thing was model classes had to be imported and accessed across all layers.

### Note on Code

The test suite in [`src/test/java`](https://github.com/persinammon/hotel-reservation-app/tree/main/src/test/java) is in particular very comprehensive.
There are parameterized unit tests, standard unit tests, and 
a fake implemented using Mockito. Test coverage is 100% class coverage, 
method coverage ranging from 25% to 86-100%, and line coverage
ranging from 34% to 92-100%. Tests for getter and setters were not
written, lowering method coverage percentages.

The best feature in my opinion is [`findRooms`](https://github.com/persinammon/hotel-reservation-app/blob/main/src/main/java/service/ReservationService.java#L120)
because it supports room search for weeks out in the CLI. Unfortunately that application feature is mostly implemented in the CLI.

[`reserveARoom`](https://github.com/persinammon/hotel-reservation-app/blob/main/src/main/java/service/ReservationService.java#L73) is the backbone of the app with relative complexity too.

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

### Note on Branching History

`dev` branch is to save changes when convenient.
`main` branch is for milestones in progress, and is meant to be kept
as clean as possible. `dev-src` and `dev-testsuite` are not actively used.

