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


public class ModelTest {

    @Test
    @DisplayName("Customer creation with valid name and email works")
    public void createCustomerWorks() {
        Customer customer1 = new Customer("Jamey", "Dogman", "jamey.dogman@hotmail.com");
        assertNotNull(customer1, "Customer was not created"); //second parameter is an optional failure message
    }

    @Test
    @DisplayName("Room creation with valid room number works")
    public void createRoomWorks() {

    }

    @Test
    @DisplayName("Reservation creation with valid parameters works")
    public void createReservationWorks() {

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

    @DisplayName("Unimplemented: Customer creation with null values for names fails")
    public void createInvalidCustomerName_throwsError() {
        //fill in
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
            Thread.sleep(2000); //2 seconds, Date's smallest granularity is by the second
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
    @DisplayName("")
    public void equalsCustomer_returnsTrue() {

    }

    @Test
    @DisplayName("")
    public void notEqualsCustomer_returnsFalse() {

    }

    @Test
    @DisplayName("")
    public void equalsRoom_returnsTrue() {

    }

    @Test
    @DisplayName("")
    public void notEqualsRoom_returnsFalse() {

    }

    /** Hashing functions were overridden, but relying on hashCode function in implementation
     *  to work rather than testing new hashing. **/

}
