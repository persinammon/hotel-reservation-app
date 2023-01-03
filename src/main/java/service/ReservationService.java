package service;

import model.Reservation;
import model.IRoom;
import model.Customer;

import java.util.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

public final class ReservationService {

    private static ReservationService reservationservice;
    private Set<IRoom> rooms;
    private Collection<Reservation> reservations;
    private Map<Customer, Collection<Reservation>> customerbookings;

    private ReservationService () {}

    public static ReservationService getInstance() {
        if (reservationservice == null) {
            reservationservice = new ReservationService();
        }
        return reservationservice;
    }

    public void addRoom(IRoom room) throws Exception {
        if (!rooms.contains(room)) {
            rooms.add(room);
        } else {
            throw new Exception("Room of same room number already in dataset.");
        }
    }

    public Optional<IRoom> getARoom(String roomId) {
        IRoom foundRoom = null;
        for (IRoom r : rooms) {
            if (r.getRoomNumber() == roomId) foundRoom = r;
        }
        return Optional.ofNullable(foundRoom);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return (Reservation) null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return (Collection<IRoom>) null;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return customerbookings.get(customer);
    }

    public void printAllReservations() {

    }
}
