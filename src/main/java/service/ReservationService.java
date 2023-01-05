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


public class ReservationService {

    private static ReservationService reservationservice;
    private Set<IRoom> rooms;
    private Collection<Reservation> reservations;
    private Map<Customer, Collection<Reservation>> customerbookings;

    private Map<String, Collection<Reservation>> roomreservations;



    private ReservationService () {
        rooms = new HashSet<IRoom>();
        reservations = new HashSet<Reservation>();
        customerbookings = new HashMap<Customer, Collection<Reservation>>();
        roomreservations = new HashMap<String, Collection<Reservation>>();
    }

    private CustomerService testCustomerService;
    public ReservationService(CustomerService customerServiceMock) {
        /**
         *  This is a public facing constructor for unit testing with a mock.
         */
        rooms = new HashSet<IRoom>();
        reservations = new HashSet<Reservation>();
        customerbookings = new HashMap<Customer, Collection<Reservation>>();
        roomreservations = new HashMap<String, Collection<Reservation>>();
        testCustomerService = customerServiceMock;
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

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
        Reservation res = new Reservation(customer, room, checkInDate,checkOutDate);

        //allows injecting mock class for testing
        CustomerService cs = (testCustomerService == null) ? CustomerService.getInstance() : testCustomerService;

        if (cs.getCustomer(customer.getEmail()).isEmpty()) throw new Exception("Customer account does not exist.");
        if (!rooms.contains(room)) throw new Exception("Room does not exist.");
        if (checkInDate.after(checkOutDate)) throw new Exception("Reservation dates are invalid.");
        if (roomreservations.get(room.getRoomNumber()) != null) {
            for (Reservation r : roomreservations.get(room.getRoomNumber())) {
                Date oldcheckIn = r.getCheckInDate();
                Date oldcheckOut = r.getCheckOutDate();
                if ((checkInDate.after(oldcheckIn) && checkOutDate.before(oldcheckOut)) ||
                        (checkInDate.before(oldcheckIn) && checkOutDate.after(oldcheckIn)) ||
                        (checkInDate.after(oldcheckIn) && checkInDate.before(oldcheckOut)) ||
                        (checkInDate.equals(oldcheckIn)) ||
                        (checkOutDate.equals(oldcheckOut))) {
                    throw new Exception("Room is booked for those dates.");
                }
            }
        }

        /**
         * Adding to the data structures reservations, roomreservations, and customerbookings is atomic.
         */
        this.reservations.add(res);

        if (this.roomreservations.get(room.getRoomNumber()) != null) {
            this.roomreservations.get(room.getRoomNumber()).add(res);
        } else {
            Collection<Reservation> resSet = new HashSet<Reservation>();
            resSet.add(res);
            this.roomreservations.put(room.getRoomNumber(), resSet);
        }

        if (this.customerbookings.get(customer) != null) {
            customerbookings.get(customer).add(res);
        } else {
            Collection<Reservation> custSet = new HashSet<Reservation>();
            custSet.add(res);
            this.customerbookings.put(customer, custSet);
        }

        return res;
   }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        /**
         *  Assumes previously saved reservation date ranges are sanitized.
         */
        Collection<IRoom> roomsInRange = new HashSet<IRoom>();
        for (IRoom room : rooms) {
            Collection<Reservation> roomRes = roomreservations.get(room.getRoomNumber());
            Boolean isFree = true;
            if (roomRes != null) {
                for (Reservation res : roomRes) {
                    //if check in date is in between the old dates OR check out date between OR it covers the entire span
                    if ((checkInDate.before(res.getCheckOutDate()) && checkInDate.after(res.getCheckInDate())) ||
                            (checkOutDate.before(res.getCheckOutDate()) && checkOutDate.after(res.getCheckInDate())) ||
                            (checkInDate.before(res.getCheckInDate()) && checkOutDate.after(res.getCheckOutDate()))) {
                        isFree = false;
                    }
                }
            }
            if (isFree) roomsInRange.add(room);
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
