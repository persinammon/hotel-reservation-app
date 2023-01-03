package model;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ModelTest {

    @Test
    @DisplayName("Customer creation with valid name and email works")
    public void createCustomerWorks() {
        Customer customer1 = new Customer("Jamey", "Dogman", "jamey.dogman@hotmail.com");
        assertNotNull(customer1, "Customer was not created"); //second parameter is an optional failure message
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe, johndoe",
            "Jill, Doey, j.doey@himalayankitchen",
            "Jack, Delaney, jackie@delaney.org"
    })
    @DisplayName("Customer creation fails with invalid emails")
    public void createInvalidCustomerEmail_throwsError(String firstName, String lastName, String email) {
        assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, lastName, email));
    }

    @Test
    @DisplayName("String representation of Customer has all information")
    public void stringCustomer_containsAllValues() {
        String firstName = "Jorge";
        String lastName = "Drooey";
        String email = "jaredooey@gmail.com";
        Customer cust = new Customer(firstName, lastName, email);
        String custString = cust.toString();
        assertAll(() -> assertTrue(custString.contains(firstName)),
                () -> assertTrue(custString.contains(lastName)),
                () -> assertTrue(custString.contains(email)));
    }

    @Test
    @DisplayName("String representation of Room has all information")
    public void stringRoom_containsAllValues() {
        String roomNumber = "434";
        Double price = 5.0;
        RoomType enumeration = RoomType.SINGLE;
        Room room = new Room(roomNumber, price, enumeration);
        String roomString = room.toString();
        assertAll(() -> assertTrue(roomString.contains(roomNumber)),
                () -> assertTrue(roomString.contains(price.toString())),
                () -> assertTrue(roomString.contains(enumeration.toString())));
    }

    @Test
    @DisplayName("String representation of Free Room has all information")
    public void stringFreeRoom_containsAllValues() {
        String roomNumber = "434";
        Double price = 5.0;
        RoomType enumeration = RoomType.SINGLE;
        Room room = new FreeRoom(roomNumber, enumeration);
        String roomString = room.toString();
        assertAll(()->assertTrue(roomString.contains("free")||roomString.contains("Free")),
                ()->assertTrue(roomString.contains(roomNumber)),
                ()->assertTrue(roomString.contains(((Double) 0.0).toString())),
                ()->assertTrue(roomString.contains(enumeration.toString())));
    }

    @Test
    @DisplayName("String representation of Reservation has all information")
    public void stringReservation_containsAllValues() {
        Customer cust = new Customer("Jupiter", "Dionysus", "jdude@gmail.com");
        Room room = new Room("500", 2.0, RoomType.DOUBLE);
        String custString = cust.toString();
        String roomString = room.toString();
        Date checkInDate = new Date();
        try {
            Thread.sleep(1000); //1 second, Date's smallest granularity is by the second
        } catch (Exception e) {}
        finally {
            Date checkOutDate = new Date();
            Reservation reserve = new Reservation(cust, room, checkInDate, checkOutDate);
            String reserveString = reserve.toString();
            assertAll(() -> assertTrue(reserveString.contains(custString)),
                    () -> assertTrue(reserveString.contains(roomString)),
                    () -> assertTrue(reserveString.contains(checkInDate.toString())),
                    () -> assertTrue(reserveString.contains(checkOutDate.toString())));
        }
    }

    @Test
    @DisplayName("Equivalent customers equal each other")
    public void equalsCustomer_returnsTrue() {
        Customer cust1 = new Customer("Jenny", "Deer", "jennydeer@gmail.com");
        Customer cust2 = new Customer("Jolly", "Denim", "jennydeer@gmail.com");
        assertTrue(cust1.equals(cust2));
    }

    @Test
    @DisplayName("Nonequivalent customers do not equal each other")
    public void notEqualsCustomer_returnsFalse() {
        Customer cust1 = new Customer("Jenny", "Deer", "jennydeer@gmail.com");
        Customer cust2 = new Customer("Joy", "Deer", "joydeer@gmail.com");
        assertFalse(cust1.equals(cust2));
    }

    @Test
    @DisplayName("Equivalent rooms equal each other")
    public void equalsRoom_returnsTrue() {
        Room room1 = new Room("400", 5.0, RoomType.DOUBLE);
        Room room2 = new Room("400", 5.0, RoomType.DOUBLE);
        assertTrue(room1.equals(room2));
    }

    @Test
    @DisplayName("Nonequivalent rooms do not equal each other")
    public void notEqualsRoom_returnsFalse() {
        Room room1 = new Room("400", 5.0, RoomType.DOUBLE);
        Room room2 = new Room("401", 5.0, RoomType.DOUBLE);
        assertFalse(room1.equals(room2));
    }

    /** Hashing functions were overridden, but relying on hashCode function in implementation
     *  to work rather than testing new hashing. **/

}
