package kylejohnsonM6;

public class OrderDetails {
//Members Variables

    String name;
    String description;
    String date;
    int idNumber;
    double balanceOwed;

//Default Constructor
    public OrderDetails() {
        name = "Name";
        description = "Description";
        date = "00/00/0000";
        idNumber = 00000;
        balanceOwed = 0.00;
    }

//Constructor    
    public OrderDetails(String name, String description, String date, int idNumber, double balaceOwed) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.idNumber = idNumber;
        this.balanceOwed = balaceOwed;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + "\nDate: " + date + "\nID Number: " + idNumber
                + "\nBalance Owed: " + balanceOwed;
    }
}
