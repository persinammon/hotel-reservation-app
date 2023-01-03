package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.ParemeterizedTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
    @DisplayName("Tests that customer creation fails with invalid emails")
    public void createInvalidCustomerEmail_throwsError() {
        //assertThrows();
    }

    @ParameterizedTest
    @CsvSource({
        //fill in
    })
    @DisplayName("Tests that customer creation with invalid types for names fails")
    public void createInvalidCustomerName_throwsError() {
        //fill in
    }

}
