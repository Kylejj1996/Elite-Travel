package kylejohnsonM6;

import java.util.ArrayList;

public class TravelAgencyEmployee extends Person {

    boolean isManager;
    String hireDate;
    double salary;

//Default Constructor
    public TravelAgencyEmployee() {
        isManager = false;
        hireDate = "00/00/0000";
        salary = 0.00;
    }

//Constructor    
    public TravelAgencyEmployee(int idNumber, String name, String address, String loginName, String password, String phoneNumber, boolean isManager, String hireDate, double salary) {
        super(idNumber, name, address, loginName, password, phoneNumber);
        this.isManager = isManager;
        this.hireDate = hireDate;
        this.salary = salary;
    }
    
    //Getter and Setters
    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

    @Override
    public String toString() {
        return super.toString() + "\nIs a Manager: " + isManager + "\nHire Date: " + hireDate + "\nSalary: " + String.format("$%.2f", salary) + "\n";
    }
}
