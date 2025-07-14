package kylejohnsonM6;

public class HotelRoom {

    //Members Variables
    int roomNumber;
    int maxOccupants;

    //Default Constructor
    public HotelRoom() {
        roomNumber = 0;
        maxOccupants = 0;
    }

    //Constructor     
    public HotelRoom(int roomNumber, int maxOccupants) {
        this.roomNumber = roomNumber;
        this.maxOccupants = maxOccupants;
    }

    //Overiding the toString() method
    @Override
    public String toString() {

        return "Room number: " + roomNumber + "\nMax Occupants: " + maxOccupants;
    }
}
