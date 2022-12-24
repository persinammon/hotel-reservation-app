package model;

public class Room implements IRoom {

    private String roomNumber;
    private double price;
    private RoomType enumeration;

    public Room(String roomNumber, double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room with room number " + this.roomNumber + ", price " + ((Double) this.price).toString() +
                ", room type " + this.enumeration.toString();
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public double getRoomPrice() {
        return this.price;
    }

    public RoomType getRoomType() {
        return this.enumeration;
    }

    public boolean isFree() {
        return false;
    }
}
