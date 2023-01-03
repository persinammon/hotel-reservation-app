package model;
import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "<Reservation:  customer=" + this.customer + ", room=" + room.toString() +
                ", checkInDate=" + this.checkInDate.toString() +
                ", checkOutDate=" + this.checkOutDate.toString() + ">";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof Reservation))
            return false;
        Reservation resObj = (Reservation) obj;
        return (this.customer == resObj.getCustomer()) &&
                (this.room == resObj.getRoom()) &&
                (this.checkInDate == resObj.getCheckInDate()) &&
                (this.checkOutDate == resObj.getCheckOutDate());
    }

    @Override
    public int hashCode() {
        String blob = this.customer.toString() + this.room.toString() + this.checkInDate.toString() +
                this.checkOutDate.toString();
        return blob.hashCode();
    }

}
