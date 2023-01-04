package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public final class AdminResource {
    private static AdminResource adminresource;
    private CustomerService customerservice;
    private ReservationService reservationservice;

    private AdminResource () {
        customerservice = CustomerService.getInstance();
        reservationservice = ReservationService.getInstance();
    }

    public static AdminResource getInstance() {
        if (adminresource == null) {
            adminresource = new AdminResource();
        }
        return adminresource;
    }

    public Optional<Customer> getCustomer(String email) {
        return customerservice.getCustomer(email);
    }


    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            try {
                reservationservice.addRoom(room);
            } catch(Exception e) {}
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationservice.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerservice.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationservice.printAllReservations();;
    }
}
