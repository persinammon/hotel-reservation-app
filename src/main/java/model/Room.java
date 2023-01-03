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
        return "<Room: roomNumber=" + this.roomNumber + ", price=" + ((Double) this.price).toString() +
                ", roomType=" + this.enumeration.toString() + ">";
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof Room))
            return false;
        Room roomObj = (Room) obj;
        return this.roomNumber == roomObj.roomNumber;
    }

    @Override
    public int hashCode() {
        String blob = this.roomNumber;
        return blob.hashCode();
    }

}
