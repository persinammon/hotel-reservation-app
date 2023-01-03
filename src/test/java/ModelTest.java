package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;


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

    @DisplayName("Unimplemented: Customer creation with null values for names fails")
    public void createInvalidCustomerName_throwsError() {
        //fill in
    }

    @DisplayName("String representation of Customer has all information")
    public void stringCustomer_containsAllValues() {
    }
    @DisplayName("String representation of Customer has all information")
    public void stringRoom_containsAllValues() {

    }

    @DisplayName("String representation of Customer has all information")
    public void stringFreeRoom_containsAllValues() {

    }

    @DisplayName("String representation of Customer has all information")
    public void stringReservation_containsAllValues() {

    }

    @DisplayName("String representation of Customer has all information")
    public void equalsCustomer_returnsTrue() {

    }

    @DisplayName("String representation of Customer has all information")
    public void notEqualsCustomer_returnsFalse() {

    }

    @DisplayName("String representation of Customer has all information")
    public void equalsRoom_returnsTrue() {

    }

    @DisplayName("String representation of Customer has all information")
    public void notEqualsRoom_returnsFalse() {

    }

    /** Hashing functions were overridden, but relying on hashCode function in implementation
     *  to work rather than testing new hashing. **/

}
