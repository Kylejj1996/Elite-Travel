package kylejohnsonM6;

import java.util.ArrayList;

public class Hotel extends Lodging {

//4 Members Variables, 3 Unique Variables
    boolean hasFreeBreakfast;
    int vacancies;
    boolean valetParking;
    double parkingFee;
    boolean hasPool;
    boolean petsAllowed;
    double roomServicePrice;
    public static ArrayList<Hotel> hotels = new ArrayList();//Declaring arrayList to store house objects

//Default Constructor
    public Hotel() {
        hasFreeBreakfast = false;
        vacancies = 0;
        valetParking = false;
        parkingFee = 0.00;
        hasPool = false;
        petsAllowed = false;
        roomServicePrice = 0.00;
    }
    //Getter and setters
    public boolean isHasFreeBreakfast() {
        return hasFreeBreakfast;
    }

    public void setHasFreeBreakfast(boolean hasFreeBreakfast) {
        this.hasFreeBreakfast = hasFreeBreakfast;
    }
    public int getLodgeID(){
        return lodgeID;
    }
    
    public void setLodgeID(int lodgeID){
        this.lodgeID = lodgeID;
    }
    
    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public boolean isValetParking() {
        return valetParking;
    }

    public void setValetParking(boolean valetParking) {
        this.valetParking = valetParking;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    public boolean isHasPool() {
        return hasPool;
    }

    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public double getRoomServicePrice() {
        return roomServicePrice;
    }

    public void setRoomServicePrice(double roomServicePrice) {
        this.roomServicePrice = roomServicePrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
//Constructor 
    public Hotel(int lodgeID, String name, String address, String phoneNumber, String description, int maxOccupants, double basePricePerNight, boolean hasFreeBreakfast, int vacancies, boolean valetParking, double parkingFee, boolean hasPool, boolean petsAllowed, double roomServicePrice) {
        super(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight);//Using super to refrence Lodging Variables
        this.hasFreeBreakfast = hasFreeBreakfast;
        this.vacancies = vacancies;
        this.valetParking = valetParking;
        this.parkingFee = parkingFee;
        this.hasPool = hasPool;
        this.petsAllowed = petsAllowed;
        this.roomServicePrice = roomServicePrice;
    }

    @Override
    public String toString() {

        return super.toString() + "\nFree breakfast: " + hasFreeBreakfast
                + "\nVacancies: " + vacancies
                + "\nValet Parking: " + valetParking
                + "\nParking Fee: " + String.format("$%.2f", parkingFee)
                + "\nHas a pool: " + hasPool
                + "\nPets Allowed: " + petsAllowed
                + "\nRoom Service Price: " + String.format("$%.2f", roomServicePrice) + "\n";
    }


}
