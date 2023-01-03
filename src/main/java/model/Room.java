package model;

public class Room implements IRoom {

    private final String roomNumber;
    private final double price;
    private final RoomType enumeration;

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

    public final String getRoomNumber() {
        return this.roomNumber;
    }

    public final double getRoomPrice() {
        return this.price;
    }

    public final RoomType getRoomType() {
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
