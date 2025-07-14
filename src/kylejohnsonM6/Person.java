package kylejohnsonM6;

public abstract class Person {
//Private Variables

    int idNumber;
    String name;
    String address;
    String loginName;
    String password;
    String phoneNumber;

//Default Constructor
    public Person() {
        idNumber = 00000;
        name = "Name";
        address = "Default Address";
        loginName = "Login Name";
        password = "Password";
        phoneNumber = "000-000-0000";
    }

//Constructor
    public Person(int idNumber, String name, String address, String loginName, String password, String phoneNumber) {
        this.idNumber = idNumber;
        this.name = name;
        this.address = address;
        this.loginName = loginName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
//Getter and setters for private variables   

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
        return "ID Number: " + idNumber + "\nName: " + name + "\nAddress: " + address + "\nLogin Name: " + loginName + "\nPassword: " + password + "\nPhone Number: " + phoneNumber;
    }
}
