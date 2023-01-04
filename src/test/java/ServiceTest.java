package service;

import model.Customer;
import model.Reservation;
import model.IRoom;
import model.Room;
import model.RoomType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;

import java.util.Collection;
import java.util.Optional;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServiceTest {

    private CustomerService testCustomerService;

    private CustomerService testCustomerServiceMock;

    private ReservationService testReservationService;

    private ReservationService testReservationServiceMock;

    @BeforeEach
    void init() {
        this.testCustomerService = CustomerService.getInstance();
        this.testCustomerServiceMock = mock(CustomerService.class);
        this.testReservationServiceMock = new ReservationService(testCustomerServiceMock);
        when(this.testCustomerServiceMock.getCustomer("jojododo@hotmail.com"))
                .thenReturn(Optional.of(new Customer("Jojo", "Dodo", "jojododo@hotmail.com")));
        when(this.testCustomerServiceMock.getCustomer("jayjaydoodoo@gmail.com"))
                .thenReturn(Optional.of(new Customer("JayJay", "DooDoo", "jayjaydoodoo@gmail.com")));
        this.testReservationService = ReservationService.getInstance();
    }

    /**
     *  Due to unit tests having to test behavior and not implementation details,
     *  unit tests are testing multiple methods at once (mutators and accessors).
     */

    @Test
    @DisplayName("Data persists in Customer Service")
    public void newInstanceCustomerService_returnsSameData() {
        String email = "janine.dory@gmail.com";
        String firstName = "Janine";
        String lastName = "Dory";
        try {
            testCustomerService.addCustomer(email, firstName, lastName);
        } catch (Exception e) { System.out.println(e.getMessage());}
        CustomerService cs2 = CustomerService.getInstance();
        Optional<Customer> cust = cs2.getCustomer(email);
        cust.ifPresent(customer -> assertEquals(customer, new Customer(firstName, lastName, email)));
        if (cust.isEmpty()) fail("Customer data not persisted.");
    }

    @Test
    @DisplayName("Data persists in Reservation Service")
    public void newInstanceReservationService_returnsSameData() {
        Room room = new Room("404", 6.0, RoomType.SINGLE);
        try {
            testReservationService.addRoom(room);
        } catch (Exception e) { System.out.println(e.getMessage());}
        ReservationService rs2 = ReservationService.getInstance();
        if (rs2.getARoom("404").isEmpty()) fail("Room data not persisted.");
    }

    @Test
    @DisplayName("Adding a new customer works")
    public void addAndGetCustomer_returnsCustomer() {
        String email = "jeremy.dory@gmail.com";
        String firstName = "Jeremy";
        String lastName = "Dory";
        try {
            testCustomerService.addCustomer(email, firstName, lastName);
        } catch (Exception e) { System.out.println(e.getMessage());}
        Optional<Customer> cust = testCustomerService.getCustomer(email);
        cust.ifPresent(customer -> assertEquals(customer, new Customer(firstName, lastName, email)));
        if (cust.isEmpty()) fail("New customer not found.");
    }

    @Test
    @DisplayName("Getting all customers works")
    public void getAllCustomers_returnsAllCustomers() {
        try {
            testCustomerService.addCustomer("jd@gmail.com", "John", "Doe");
            testCustomerService.addCustomer("jellybeanz@gmail.com", "James", "Dai");
        } catch (Exception e) {System.out.println(e.getMessage());}
        Collection<Customer> customers = testCustomerService.getAllCustomers();
        assertAll( () -> customers.contains(new Customer("John", "Doe", "jd@gmail.com")),
                () -> customers.contains(new Customer("James", "Dai", "jellybeanz@gmail.com")));
        assertTrue(customers.size() == 2);
    }

    @Test
    @DisplayName("Adding a new room works")
    public void addAndGetRoom_returnsRoom() {
        IRoom room = new Room("404", 7.0, RoomType.SINGLE);
        try {
            testReservationService.addRoom(room);
        } catch (Exception e) { System.out.println(e.getMessage());}
        Optional<IRoom> roomOpt = testReservationService.getARoom("404");
        roomOpt.ifPresent(r -> assertEquals(r, room));
        if (roomOpt.isEmpty()) fail("New room not found.");
    }

    @Test
    @DisplayName("Adding a new reservation works, also checks getting a customer reservation works")
    public void reserveRoomAndGetReservation_returnsReservation() {
        Date checkInDate = new Date();
        Date checkOutDate;
        try {
            Thread.sleep(1000);
        } catch(Exception e) {}
        finally {
            checkOutDate = new Date();
        }
        Room room = new Room("404", 6.0, RoomType.SINGLE);
        try {
            testReservationServiceMock.addRoom(room);
        } catch(Exception e) { System.out.println(e.getMessage());}
        Customer cust = new Customer("Jojo", "Dodo", "jojododo@hotmail.com");
        Reservation reserve = new Reservation(cust, room, checkInDate, checkOutDate);
        try {
             reserve = testReservationServiceMock.reserveARoom(cust,
                    room, checkInDate, checkOutDate);
        } catch (Exception e) {System.out.println(e.getMessage());}
        assertTrue(testReservationServiceMock.getCustomersReservation(cust).contains(reserve));
    }

//    @ParameterizedTest
//    @CsvSource{
//
//    }

    @ParameterizedTest
    @CsvSource({
        "1998, 0, 3, 1998, 0, 5, 3", //in between, len(2) and room numbers are 200 and 202
        "1998, 3, 1, 1998, 3, 14, 3", //way out
        "1998, 0, 21, 1998, 1, 2, 2", //overlap with room 1, len(1) and room number is 202
            "1998, 0, 1, 1998, 4, 28, 2"
    })
    @DisplayName("Search rooms given dates works")
    public void findRoomsGivenDates_returnsCorrectRooms(int y1, int m1, int d1, int y2, int m2, int d2, int ans) {
        Room room1 = new Room("200", 6.0, RoomType.DOUBLE);
        Room room2 = new Room("202", 5.0, RoomType.DOUBLE);
        try {
            testReservationServiceMock.addRoom(room1);
            testReservationServiceMock.addRoom(room2);
        } catch (Exception e) {}

        Calendar calendar = Calendar.getInstance();
        calendar.set(1998, Calendar.JANUARY, 20);
        Date checkIn1 = calendar.getTime();
        calendar.set(1998, Calendar.JANUARY, 31);
        Date checkOut1 = calendar.getTime();
        calendar.set(1998, Calendar.FEBRUARY, 7);
        Date checkIn2 = calendar.getTime();
        calendar.set(1998, Calendar.FEBRUARY, 14);
        Date checkOut2 = calendar.getTime();

        Customer customer1 = new Customer("Jojo", "Dodo", "jojododo@hotmail.com");
        try {
            testReservationServiceMock.reserveARoom(customer1, room1, checkIn1, checkOut1);
            testReservationServiceMock.reserveARoom(customer1, room1, checkIn2, checkOut2);
        } catch (Exception e) {}

        calendar.set(y1, m1, d1);
        Date checkIn3 = calendar.getTime();
        calendar.set(y2, m2, d2);
        Date checkOut3 = calendar.getTime();
        HashMap<Integer, Collection<IRoom>> possibleAns = new HashMap<Integer, Collection<IRoom>>();
        possibleAns.put(0, new HashSet<IRoom>());
        possibleAns.put(1, Stream.of(room1)
                .collect(Collectors.toCollection(HashSet::new)));
        possibleAns.put(2, Stream.of(room2)
                .collect(Collectors.toCollection(HashSet::new)));
        possibleAns.put(3, Stream.of(room1, room2)
                .collect(Collectors.toCollection(HashSet::new)));
        assertEquals(possibleAns.get((Integer) ans), testReservationServiceMock.findRooms(checkIn3, checkOut3));
    }
}
