package service;

import model.Customer;
import model.Reservation;
import model.IRoom;
import model.Room;
import model.RoomType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Collection;
import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTest {

    /**
     *  Due to unit tests having to test behavior and not implementation details,
     *  unit tests are testing multiple methods at once (mutators and accessors).
     */

    @Test
    @DisplayName("Data persists in Customer Service")
    public void newInstanceCustomerService_returnsSameData() {
        CustomerService cs = CustomerService.getInstance();
        String email = "janine.dory@gmail.com";
        String firstName = "Janine";
        String lastName = "Dory";
        try {
            cs.addCustomer(email, firstName, lastName);
        } catch (Exception e) { System.out.println(e.getMessage());}
        CustomerService cs2 = CustomerService.getInstance();
        Optional<Customer> cust = cs2.getCustomer(email);
        cust.ifPresent(customer -> assertEquals(customer, new Customer(firstName, lastName, email)));
        if (cust.isEmpty()) fail("Customer data not persisted.");
    }

    @Test
    @DisplayName("Data persists in Reservation Service")
    public void newInstanceReservationService_returnsSameData() {
        ReservationService rs = ReservationService.getInstance();
        Customer cust = new Customer("Janine", "Dory", "janine.dory@gmail.com");
        Room room = new Room("404", 6.0, RoomType.SINGLE);
        Date checkInDate = new Date();
        Date checkOutDate;
        try {
            Thread.sleep(1000);
        } catch(Exception e){}
        finally {
            checkOutDate = new Date();
        }
        try {
            rs.reserveARoom(cust, room, checkInDate, checkOutDate);
        } catch (Exception e) { System.out.println(e.getMessage());}
        ReservationService rs2 = ReservationService.getInstance();
        Collection<Reservation> res = rs2.getCustomersReservation(cust);
        assertTrue(res.contains(new Reservation(cust, room, checkInDate, checkOutDate)));
    }

    @Test
    @DisplayName("Adding a new customer works")
    public void addAndGetCustomer_returnsCustomer() {
        CustomerService cs = CustomerService.getInstance();
        String email = "jeremy.dory@gmail.com";
        String firstName = "Jeremy";
        String lastName = "Dory";
        try {
            cs.addCustomer(email, firstName, lastName);
        } catch (Exception e) { System.out.println(e.getMessage());}
        Optional<Customer> cust = cs.getCustomer(email);
        cust.ifPresent(customer -> assertEquals(customer, new Customer(firstName, lastName, email)));
        if (cust.isEmpty()) fail("New customer not found.");
    }

    @Test
    @DisplayName("Getting all customers works")
    public void getAllCustomers_returnsAllCustomers() {
        CustomerService cs = CustomerService.getInstance();
        try {
            cs.addCustomer("jd@gmail.com", "John", "Doe");
            cs.addCustomer("jellybeanz@gmail.com", "James", "Dai");
        } catch (Exception e) {System.out.println(e.getMessage());}
        Collection<Customer> customers = cs.getAllCustomers();
        assertAll( () -> customers.contains(new Customer("John", "Doe", "jd@gmail.com")),
                () -> customers.contains(new Customer("James", "Dai", "jellybeanz@gmail.com")));
        assertTrue(customers.size() == 2);
    }

    @Test
    @DisplayName("Adding a new room works")
    public void addAndGetRoom_returnsRoom() {
        ReservationService rs = ReservationService.getInstance();
        IRoom room = new Room("404", 7.0, RoomType.SINGLE);
        try {
            rs.addRoom(room);
        } catch (Exception e) { System.out.println(e.getMessage());}
        Optional<IRoom> roomOpt = rs.getARoom("404");
        roomOpt.ifPresent(r -> assertEquals(r, room));
        if (roomOpt.isEmpty()) fail("New room not found.");
    }

    @Test
    @DisplayName("Adding a new reservation works")
    public void reserveRoomAndGetReservation_returnsReservation() {
        //Needs a mockito
//        ReservationService rs = ReservationService.getInstance();
//        try {
//            rs.addRoom(new Room("404", 6.0, RoomType.SINGLE));
//        } catch(Exception e) { System.out.println(e.getMessage());}
//        rs.reserveARoom(new Customer("Joyride", "Donuts", "joyridedonuts@gmail.com"),
//                new Room("404", 6.0, RoomType.SINGLE), );
//        rs.g
//
//        Optional<Customer> cust = cs.getCustomer(email);
//        cust.ifPresent(customer -> assertEquals(customer, new Customer(firstName, lastName, email)));
//        if (cust.isEmpty()) fail("New customer not found.");
    }

    @Test
    @DisplayName("Getting a customer's reservations works")
    public void getCustomerReservations_returnsAllReservations() {
        //needs a double
    }

    @Test
    @DisplayName("Search rooms given dates works")
    public void findRoomsGivenDates_returnsCorrectRooms() {
        //needs to add reservations to work (Mockito?)
    }
}
