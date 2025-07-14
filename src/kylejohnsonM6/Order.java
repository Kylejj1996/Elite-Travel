package kylejohnsonM6;

//Class that represents an order
public class Order {
    //Variables 
    String name;
    int idNumber;
    double totalPrice;

    //Constructor
    public Order(String name, int idNumber, double totalPrice) {
        this.name = name;
        this.idNumber = idNumber;
        this.totalPrice = totalPrice;
    }

    //Method to convert the order details to a string
    @Override
    public String toString() {
        return "Order ID: " + idNumber
                + "\nLodge: " + name
                + "\nTotal Price: " + String.format("$%.2f", totalPrice);
    }
}
