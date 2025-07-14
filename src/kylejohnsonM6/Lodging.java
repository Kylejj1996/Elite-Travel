package kylejohnsonM6;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Lodging {
//Members Variables 
    int lodgeID;
    String name;
    String address;
    String phoneNumber;
    String description;
    int maxOccupants;
    double basePricePerNight;
    private ArrayList<ImageIcon> images = new ArrayList();//ArrayList to store images for each lodge

//Default Constructor
    public Lodging() {
        lodgeID = 0;
        name = "Default name";
        address = "Address";
        phoneNumber = "000-000-0000";
        description = "Default Description";
        maxOccupants = 0;
        basePricePerNight = 0;
    }

//Constructor
    public Lodging(int lodgeID, String name, String address, String phoneNumber, String description, int maxOccupants, double basePricePerNight) {
        this.lodgeID = lodgeID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.maxOccupants = maxOccupants;
        this.basePricePerNight = basePricePerNight;
        this.images = new ArrayList();
    }
    //Method to add the image path to the list
    public void addImage(ImageIcon image){
        images.add(image);
    }
    //Method to get all of the image paths
    public ArrayList<ImageIcon> getImages(){
        return images;
    }
    @Override
    public String toString() {
        return "LodgeID: " + lodgeID + "\nName: " + name + "\nAddress: " + address + "\nPhone Number: " + phoneNumber + "\nDescription: " + description + "\nMax Occupants: " + maxOccupants
                + "\nBase Price Per Night: " + String.format("$%.2f", basePricePerNight);

    }

}
