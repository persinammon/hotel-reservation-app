package service;

import model.Reservation;
import model.IRoom;
import model.Customer;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Date;


public final class ReservationService {

    private static ReservationService reservationservice;
    private Set<IRoom> rooms;
    private Collection<Reservation> reservations;
    private Map<Customer, Collection<Reservation>> customerbookings;

    private Map<IRoom, Collection<Reservation>> roomreservations;

    private ReservationService () {
        rooms = new HashSet<IRoom>();
        reservations = new HashSet<Reservation>();
        customerbookings = new HashMap<Customer, Collection<Reservation>>();
        roomreservations = new HashMap<IRoom, Collection<Reservation>>();
    }

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
            throw new Exception("Room of same room number already in hotel dataset.");
        }
    }

    public Optional<IRoom> getARoom(String roomId) {
        IRoom foundRoom = null;
        for (IRoom r : rooms) {
            if (r.getRoomNumber().equals(roomId)) foundRoom = r;
        }
        return Optional.ofNullable(foundRoom);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
        Reservation res = new Reservation(customer, room, checkInDate,checkOutDate);
        CustomerService cs = CustomerService.getInstance();
        if (cs.getCustomer(customer.getEmail()).isEmpty()) throw new Exception("Customer account does not exist.");
        if (!rooms.contains(room)) throw new Exception("Room does not exist.");
        if (checkInDate.after(checkOutDate)) throw new Exception("Reservation dates are invalid.");
        if (roomreservations.get(room) != null) {
            for (Reservation r : roomreservations.get(room)) {
                Date oldcheckIn = r.getCheckInDate();
                Date oldcheckOut = r.getCheckOutDate();
                if ((checkInDate.after(oldcheckIn) && checkOutDate.before(oldcheckOut)) ||
                        (checkInDate.before(oldcheckIn) && checkOutDate.after(oldcheckIn)) ||
                        (checkInDate.after(oldcheckIn) && checkInDate.before(oldcheckOut))) {
                    throw new Exception("Room is booked for those dates.");
                }
            }
        }

        /**
         * Adding to the data structures reservations, roomreservations, and customerbookings is atomic.
         */
        reservations.add(res);

        if (roomreservations.get(room) != null) {
            roomreservations.get(room).add(res);
        } else {
            Collection<Reservation> resSet = new HashSet<Reservation>();
            resSet.add(res);
            roomreservations.put(room, resSet);
        }

        if (customerbookings.get(customer) != null) {
            customerbookings.get(customer).add(res);
        } else {
            Collection<Reservation> custSet = new HashSet<Reservation>();
            custSet.add(res);
            customerbookings.put(customer, custSet);
        }

        return res;
   }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        /**
         *  Assumes previously saved reservation date ranges are sanitized.
         */
        Collection<IRoom> roomsInRange = new HashSet<IRoom>();
        for (Reservation r : reservations) {
            if (r.getCheckOutDate().before(checkInDate) || r.getCheckInDate().after(checkOutDate)) {
                roomsInRange.add(r.getRoom());
            }
        }
        return roomsInRange;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> bookings = (customerbookings.get(customer) == null) ?
                new HashSet<Reservation>() : customerbookings.get(customer);
        return bookings;
    }

    public void printAllReservations() {
        for (Reservation r : reservations) {
            System.out.println(r.toString());
        }
    }
}
