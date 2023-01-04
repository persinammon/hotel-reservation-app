package api;

import service.CustomerService;
import service.ReservationService;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Optional;
import java.util.Date;
import java.util.Collection;
import java.util.HashSet;

public final class HotelResource {
    private static HotelResource hotelresource;
    private CustomerService customerservice;
    private ReservationService reservationservice;
    private HotelResource () {
        customerservice = CustomerService.getInstance();
        reservationservice = ReservationService.getInstance();
    }

    public static HotelResource getInstance() {
        if (hotelresource == null) {
            hotelresource = new HotelResource();
        }
        return hotelresource;
    }

    public Optional<Customer> getCustomer(String email) {
        return customerservice.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        try {
            customerservice.addCustomer(email, firstName, lastName);
        } catch(Exception e) {}
    }

    public Optional<IRoom> getRoom(String roomNumber) {
        return reservationservice.getARoom(roomNumber);
    }

    public void bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Optional<Customer> customer = customerservice.getCustomer(customerEmail);
        if (customer.isPresent()) {
            try {
                reservationservice.reserveARoom(customer.get(), room, checkInDate, checkOutDate);
            } catch (Exception e) {}
        }
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Optional<Customer> customer = customerservice.getCustomer(customerEmail);
        if (customer.isPresent()) return reservationservice.getCustomersReservation(customer.get());
        return (Collection<Reservation>) new HashSet<Reservation>();
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationservice.findRooms(checkIn, checkOut);
    }
}
