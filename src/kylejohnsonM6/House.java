package kylejohnsonM6;

import java.util.ArrayList;

public class House extends Lodging {

    //2 Members Variables, 2 Unique Variables
    int numberOfBedrooms;
    boolean hasGarage;
    double cleaningFee;
    boolean hasBackyard;
    public static ArrayList<House> houses = new ArrayList();//Initiallizing the arrayList

//Default Constructor
    public House() {
        numberOfBedrooms = 0;
        cleaningFee = 0.00;
        hasGarage = false;
        hasBackyard = false;

    }
    //Getter and setters
    public int getLodgeID(){
        return lodgeID;
    }
    
    public void setLodgeID(int lodgeID){
        this.lodgeID = lodgeID;
    }
    
    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(double cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public boolean isHasBackyard() {
        return hasBackyard;
    }

    public void setHasBackyard(boolean hasBackyard) {
        this.hasBackyard = hasBackyard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxOccupants() {
        return maxOccupants;
    }

    public void setMaxOccupants(int maxOccupants) {
        this.maxOccupants = maxOccupants;
    }

    public double getBasePricePerNight() {
        return basePricePerNight;
    }

    public void setBasePricePerNight(double basePricePerNight) {
        this.basePricePerNight = basePricePerNight;
    }


    //Constructor   
    public House(int lodgeID, String name, String address, String phoneNumber, String description, int maxOccupants, double basePricePerNight, int numberOfBedrooms, boolean hasGarage, double cleaningFee, boolean hasBackyard) {
        super(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight);
        this.numberOfBedrooms = numberOfBedrooms;
        this.hasGarage = hasGarage;
        this.cleaningFee = cleaningFee;
        this.hasBackyard = hasBackyard;
    }


    @Override
    public String toString() {
        return super.toString() + "\n" + "Number of Bedrooms: " + numberOfBedrooms
                + "\n" + "Cleaning Fee: " + String.format("$%.2f", cleaningFee)
                + "\n" + "Has a Garage: " + hasGarage
                + "\n" + "Has a Backyard: " + hasBackyard + "\n";
    }
}
