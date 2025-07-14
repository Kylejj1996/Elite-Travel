package kylejohnsonM6;

import java.util.ArrayList;

public class Customer extends Person {
    double balanceOwed;
    double totalSpending;

//Default Constructor
    public Customer() {
        balanceOwed = 0;
        totalSpending = 0;

    }

//Constructor    
    public Customer(int idNumber, String name, String address, String loginName, String password, String phoneNumber) {
        super(idNumber, name, address, loginName, password, phoneNumber);
    }

    @Override
    public String toString() {
        return super.toString() + "\nBalance Owed: " + balanceOwed
                + "\nTotal Spending: " + totalSpending + "\n";
    }
}
