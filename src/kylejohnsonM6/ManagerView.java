package kylejohnsonM6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagerView extends State {

    TravelAgencyEmployee newEmployee = new TravelAgencyEmployee();
    static ArrayList<TravelAgencyEmployee> employees = new ArrayList();//ArrayList for new Employees
    boolean isManager;

    //Method to display ManagerView
    void enter() {
        System.out.println("Entering Manager view");
        // Setting the isManager variable based on the LoginState
        isManager = LoginState.isManager;
        load();//Calling the load method to load employees from the file
    }

    //Method to update the state, based on user input 
    void update() {
        //Displaying menu options 
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employees");
        System.out.println("3. Remove Employee");
        System.out.println("4. List Employees");
        System.out.println("5. Make Employee A Manager");
        System.out.println("6. Manage Lodges(Switch to Employee View)");
        System.out.println("7. Log Out");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();//User input 
        switch (input) {
            case "1":
                addEmployee();
                break;
            case "2":
                editEmployee();
                break;
            case "3":
                removeEmployee();
                break;
            case "4":
                listEmployees();
                break;
            case "5":
                makeEmployeeManager();
                break;
            case "6":
                current = employeeView;//Switch to employee view 
                break;
            case "7":
                current = loginState;//Switch to loginState menu/Logout
                break;
            default:
                System.out.println("Error, Please try again!");
                enter();
                update();
        }

    }

    private void editEmployee() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < employees.size(); i++) {//Listing a menu of employees for the user to select which one to edit
            System.out.println((i + 1) + ". " + employees.get(i).toString());
            System.out.println("");
        }
        System.out.println("Select which employee to edit by ID number: ");
        int idNum = Integer.parseInt(scanner.nextLine()) - 1;//User input to edit employee by ID
        if (idNum >= 0 && idNum < employees.size()) {
            TravelAgencyEmployee employee = employees.get(idNum);//Creating a employee object from the employees arrayList
            System.out.println("Edit employyee information or leave it blank to keep the current information\n");

            System.out.println("Edit employee Name: " + employee.getName());
            String name = scanner.nextLine();//User input
            if (!name.isEmpty()) {//If name is not empty returns true, it will be updated with new name, if it is false it will leave the original name
                employee.setName(name);//Employee object is called if true, setting it to the new value entered
            }
            //Comments above explain what is happening with the rest
            System.out.println("Edit Employee address: " + employee.getAddress());
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                employee.setAddress(address);
            }
            System.out.println("Edit Employee Login Name: " + employee.getLoginName());
            String login = scanner.nextLine();
            if (!login.isEmpty()) {
                employee.setLoginName(login);
            }
            System.out.println("Edit Employee password: ");
            String password = scanner.nextLine();
            if (!password.isEmpty()) {
                String encryptPass = LoginState.encrypt(password);//Encrypting the password using the LoginState encryption
                employee.setPassword(encryptPass);
            }
            System.out.println("Edit Employee Phone number: " + employee.getPhoneNumber());
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                employee.setPhoneNumber(phone);
            }
            System.out.println("Edit Employee hire date (DD/MM/YYYY): " + employee.getHireDate());
            String hire = scanner.nextLine();
            if (!hire.isEmpty()) {
                employee.setHireDate(hire);
            }
            System.out.println("Edit Employee Salary: " + String.format("$%.2f", employee.getSalary()));
            String salary = scanner.nextLine();
            if (!salary.isEmpty()) {
                Double newSalary = Double.parseDouble(salary);
                employee.setSalary(newSalary);
            }
            save();//Save Method to update employee information
        } else {
            System.out.println("Invalid ID number. Try again.");
        }
    }

    //Method to add a new Employee 
    private void addEmployee() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Employee Name: ");
        String name = s.nextLine();
        System.out.println("Enter Address: ");
        String address = s.nextLine();
        System.out.println("Enter Login Name: ");
        String loginName = s.nextLine();
        System.out.println("Enter Password: ");
        String password = s.nextLine();
        System.out.println("Enter Phone Number: ");
        String phoneNumber = s.nextLine();
        System.out.println("Enter Hire Date (DD/MM/YYYY): ");
        String hireDate = s.nextLine();
        System.out.println("Enter Salary: ");
        double salary = Double.parseDouble(s.nextLine());

        // Assigning ID based on size of employee list
        int idNumber = employees.size() + 1;
        String encryptPass = LoginState.encrypt(password);//Encrypting the password using the LoginState encryption
        //Creating an employee object and adding it to the Arraylist of employees
        newEmployee = new TravelAgencyEmployee(idNumber, name, address, loginName, encryptPass, phoneNumber, false, hireDate, salary);
        employees.add(newEmployee);
        save();//Caling the save Method
        System.out.println("Employee added successfully!");

    }

    //Method to remove an employee
    private void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < employees.size(); i++) {//Listing a menu of employees for the user to select which one to remove
            System.out.println((i + 1) + ". " + employees.get(i).toString());
            System.out.println("");
        }
        System.out.println("Enter Employee ID number to remove: ");
        int idNum = Integer.parseInt(scanner.nextLine());//User input to remove employee by ID
        boolean removed = false;

        //For loop going through the employees list and removing the employee selected
        for (int i = 0; i < employees.size(); i++) {
            if (idNum == (employees.get(i).idNumber)) {
                employees.remove(i);
                removed = true;
                System.out.println("Employee removed successfully!");
                save();
                break;
            }
        }
        if (!removed) {//If the employee with the specified ID is not found it will tell the user
            System.out.println("Employee not found.");
        }
    }

    //Method to list all of the employees
    private void listEmployees() {
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i).toString());
            System.out.println("");
        }
    }

    //Method to make a employee a manager 
    private void makeEmployeeManager() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee ID Number: ");
        int id = Integer.parseInt(scanner.nextLine());//User input for employees ID number
        boolean found = false;
        //For loop to loop through employees to find the chosen employee to promote
        for (TravelAgencyEmployee employee : employees) {
            if (employee.getIdNumber() == id) {
                employee.isManager = true;
                found = true;
                System.out.println("Employee has been promoted!");
                save();
                break;
            }
        }
        if (!found) {//If the employee with the specified name is not found it will tell the user
            System.out.println("Employee not found.");
        }
    }

    //Save method to save employees to the file
    @Override
    void save() {
        //Need something here to prevent duplicate loding####################################################################
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt"));
            for (int i = 0; i < employees.size(); i++) {
                //Writing the string representation of the employees directly to the file
                TravelAgencyEmployee employee = employees.get(i);
                bw.write(employee.getIdNumber() + "|" + employee.getName() + "|" + employee.getAddress() + "|"
                        + employee.getLoginName() + "|" + employee.getPassword() + "|" + employee.getPhoneNumber() + "|"
                        + employee.isIsManager() + "|" + employee.getHireDate() + "|" + employee.getSalary());
                bw.newLine();
            }
            bw.close();//Closing the writer after writing all customers
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load method to load employees from the file
    @Override
    void load() {
        employees.clear();//Clearing the list before loading to prevent duplicate entries
        try {
            BufferedReader br = new BufferedReader(new FileReader("employees.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] eDetails = line.split("\\|");
                int id = Integer.parseInt(eDetails[0]);
                String name = eDetails[1];
                String address = eDetails[2];
                String loginName = eDetails[3];
                String password = eDetails[4];
                String phoneNumber = eDetails[5];
                boolean isManager = Boolean.parseBoolean(eDetails[6]);
                String hireDate = eDetails[7];
                double salary = Double.parseDouble(eDetails[8]);
                //Creating TravelAgencyObject and adding it to the employees list
                TravelAgencyEmployee employee = new TravelAgencyEmployee(id, name, address, loginName, password, phoneNumber, isManager, hireDate, salary);//Employee objects from the text file
                employees.add(employee);//Saving the new employee objects to the employees ArrayList
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
