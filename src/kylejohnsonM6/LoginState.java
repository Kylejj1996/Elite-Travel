package kylejohnsonM6;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;//Import for swing application
import java.sql.*;

public class LoginState extends State {

    static ArrayList<Customer> customers = new ArrayList();//ArrayList holding Customers
    ArrayList<TravelAgencyEmployee> employees = new ArrayList();//Arraylist to hold Employees
    static boolean isManager;
    //Components for loginPanel and registerPanelz
    JLabel lblSuccess;
    JLabel lblError;
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtRegUsername;
    JTextField txtPhone;
    JPasswordField txtRegPass;
    JLabel lblLoginFail;
    Label lblUsername;
    Label lblPassword;
    JLabel lblTitle;
    JTextField txtLogUsername;
    JPasswordField txtLogPass;
    Button btnLogin;
    Button btnRegister;
    Button btnCustomerView;
    Button btnExit;

    public LoginState() {
        openConnection();
        connectionCheck();//Calling the method that checks the connection and updates the label displayed
        //Setting the size of the mainFrame, setting visible to true, and the def
        mainFrame.setSize(1200, 1000);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setBackground(new Color(144, 174, 173));//Changing the background color
        mainPanel.setBorder(BorderFactory.createTitledBorder("Elite Travel Login"));
        mainPanel.setSize(1200, 1000);
        mainPanel.setLayout(null);

        //Creating components for loginPanel
        lblUsername = new Label("Username:");
        lblPassword = new Label("Password:");
        lblTitle = new JLabel("Elite Travel Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitle.setForeground(Color.BLACK);
        
        lblLoginFail = new JLabel("Invalid login, please try again.");
        lblLoginFail.setFont(new Font("Arial", Font.BOLD, 14));
        lblLoginFail.setForeground(Color.RED);
        lblLoginFail.setVisible(false);
        
        lblSuccess = new JLabel("Registration sucessful!");
        lblSuccess.setFont(new Font("Arial", Font.BOLD, 20));
        lblSuccess.setVisible(false);
        
        txtLogUsername = new JTextField();
        txtLogPass = new JPasswordField();

        btnLogin = new Button("Login");
        btnRegister = new Button("Register");
        btnCustomerView = new Button("Guest");
        btnExit = new Button("Exit");

        //Adusting where each component should go in the panel
        lblUsername.setBounds(445, 350, 100, 30);
        txtLogUsername.setBounds(555, 350, 200, 30);
        lblPassword.setBounds(445, 450, 100, 30);
        txtLogPass.setBounds(555, 450, 200, 30);
        
        lblTitle.setBounds(450, 200, 400, 50);
        lblLoginFail.setBounds(500, 500, 300, 30);
        lblSuccess.setBounds(450, 740, 300, 50);

        btnLogin.setBounds(310, 550, 180, 50);
        btnRegister.setBounds(510, 550, 180, 50);
        btnCustomerView.setBounds(710, 550, 180, 50);
        
        btnExit.setBounds(510, 800, 180, 50);

        //Setting the connection status label
        lblStatusLogin.setBounds(560, 870, 200, 20);
        //Adding the label to the arraylist
        //connectLabels[0] = lblStatusLogin;
        

        //Adding components to loginPanel
        mainPanel.add(lblUsername);
        mainPanel.add(lblPassword);
        mainPanel.add(lblTitle);
        mainPanel.add(lblLoginFail);
        mainPanel.add(lblSuccess);
        mainPanel.add(txtLogUsername);
        mainPanel.add(txtLogPass);
        mainPanel.add(btnLogin);
        mainPanel.add(btnRegister);
        mainPanel.add(btnCustomerView);
        mainPanel.add(btnExit);
        mainPanel.add(lblStatusLogin);


//--------------------------------------------------------------------------------------------------------------------------
        //registerPanel
        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(new Color(144, 174, 173));//Changing the background color
        registerPanel.setBorder(BorderFactory.createTitledBorder("Elite Travel Register"));
        registerPanel.setSize(1200, 1000);
        registerPanel.setLayout(null);

        //Creating components for registerPanel
        Label lblName = new Label("Name:");
        Label lblAddress = new Label("Address:");
        Label lblRegUsername = new Label("Username:");
        Label lblPhone = new Label("Phone Number:");
        Label lblRegPass = new Label("Password:");
        JLabel lblAcctTitle = new JLabel("Create Account");
        lblAcctTitle.setFont(new Font("Arial", Font.BOLD, 36));
        lblAcctTitle.setForeground(Color.BLACK);
        JLabel lblPassReq1 = new JLabel("Length");
        JLabel lblPassReq2 = new JLabel("Special Character");
        JLabel lblPassReq3 = new JLabel("Uppercase");

        lblError = new JLabel("Please fill in required fields!");
        lblError.setFont(new Font("Arial", Font.BOLD, 16));
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);

        JLabel lblPassX1 = new JLabel("\u2718");
        lblPassX1.setVisible(true);
        lblPassX1.setForeground(Color.RED);
        JLabel lblPassCheck1 = new JLabel("\u2714");
        lblPassCheck1.setVisible(false);
        lblPassCheck1.setForeground(Color.GREEN);

        JLabel lblPassX2 = new JLabel("\u2718");
        lblPassX2.setVisible(true);
        lblPassX2.setForeground(Color.RED);
        JLabel lblPassCheck2 = new JLabel("\u2714");
        lblPassCheck2.setVisible(false);
        lblPassCheck2.setForeground(Color.GREEN);

        JLabel lblPassX3 = new JLabel("\u2718");
        lblPassX3.setVisible(true);
        lblPassX3.setForeground(Color.RED);
        JLabel lblPassCheck3 = new JLabel("\u2714");
        lblPassCheck3.setVisible(false);
        lblPassCheck3.setForeground(Color.GREEN);

        txtName = new JTextField();
        txtAddress = new JTextField();
        txtRegUsername = new JTextField();
        txtPhone = new JTextField();
        txtRegPass = new JPasswordField();
        Button btnBack = new Button("Back");
        Button btnSubmit = new Button("Submit");

        //Adjusting the posistion of each component
        lblName.setBounds(410, 300, 100, 30);
        lblAddress.setBounds(410, 350, 100, 30);
        lblRegUsername.setBounds(410, 400, 100, 30);
        lblPhone.setBounds(410, 450, 120, 30);
        lblRegPass.setBounds(410, 500, 100, 30);
        lblError.setBounds(480, 650, 300, 50);
        lblAcctTitle.setBounds(450, 200, 400, 50);

        lblPassX1.setBounds(480, 540, 50, 30);
        lblPassCheck1.setBounds(480, 540, 50, 30);
        lblPassX2.setBounds(620, 540, 50, 30);
        lblPassCheck2.setBounds(620, 540, 50, 30);
        lblPassX3.setBounds(720, 540, 50, 30);
        lblPassCheck3.setBounds(720, 540, 50, 30);

        lblPassReq1.setBounds(430, 540, 500, 30); //Length
        lblPassReq2.setBounds(510, 540, 500, 30);//Special Character
        lblPassReq3.setBounds(650, 540, 500, 30);//Uppercase
        
        txtName.setBounds(545, 300, 200, 30);
        txtAddress.setBounds(545, 350, 200, 30);
        txtRegUsername.setBounds(545, 400, 200, 30);
        txtPhone.setBounds(545, 450, 200, 30);
        txtRegPass.setBounds(545, 500, 200, 30);

        btnBack.setBounds(20, 820, 130, 30);
        btnSubmit.setBounds(500, 600, 180, 50);
        
        lblStatusReg.setBounds(560, 870, 200, 20);
        
        registerPanel.add(lblName);
        registerPanel.add(lblAddress);
        registerPanel.add(lblRegUsername);
        registerPanel.add(lblPhone);
        registerPanel.add(lblPassX1);
        registerPanel.add(lblPassCheck1);
        registerPanel.add(lblPassX2);
        registerPanel.add(lblPassCheck2);
        registerPanel.add(lblPassX3);
        registerPanel.add(lblPassCheck3);
        registerPanel.add(lblPassReq1);
        registerPanel.add(lblPassReq2);
        registerPanel.add(lblPassReq3);
        registerPanel.add(lblRegPass);
        registerPanel.add(lblError);
        registerPanel.add(txtName);
        registerPanel.add(txtAddress);
        registerPanel.add(txtRegUsername);
        registerPanel.add(txtPhone);
        registerPanel.add(txtRegPass);
        registerPanel.add(btnBack);
        registerPanel.add(btnSubmit);
        registerPanel.add(lblStatusReg);
        registerPanel.add(lblAcctTitle);

        //Adding the LoginState panels to the layoutPanel for CardLayout
        layoutPanel.add(mainPanel, "Login");
        layoutPanel.add(registerPanel, "Register");

        //Adding the layoutPanel to the mainFrame
        mainFrame.add(layoutPanel);
        mainFrame.setVisible(true);

        //Setting the LoginPanel to show when the program runs
        cardLayout.show(layoutPanel, "Login");

        //Actiong listener for exit button to close the program
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        //Action Listener for the Login Button
        btnLogin.addActionListener(e -> {
            String username = txtLogUsername.getText();
            String password = new String(txtLogPass.getPassword());
            login(username, password);
        });

        //ActiongListener for Register Button
        btnSubmit.addActionListener(e -> {
            String name = txtName.getText();
            String address = txtAddress.getText();
            String regUsername = txtRegUsername.getText();
            String phone = txtPhone.getText();
            String regPass = new String(txtRegPass.getPassword());

            // Checking if any of the required fields are empty
            if (name.isEmpty() || address.isEmpty() || regUsername.isEmpty() || phone.isEmpty() || regPass.isEmpty()) {
                lblError.setVisible(true);//Printing a error label
                return; // Exiting the method 
            }

            try {
                register(name, address, regUsername, phone, regPass);

            } catch (Exception ex) {
                System.out.println("Error occured when trying to register user. " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        //Action Listener for when the user selects register on the loginPanel
        btnRegister.addActionListener(e -> {
            //Showing the RegisterPanel with CardLayout
            cardLayout.show(layoutPanel, "Register");
        });

        //Action Listner for when the user selects CustomerView
        btnCustomerView.addActionListener(e -> {
            //Showing the CustomerViewPanel with CardLayout
            cardLayout.show(layoutPanel, "CustomerView");

        });

        //Action Listener for when the user clicks back on the register panel
        btnBack.addActionListener(e -> {
            lblLoginFail.setVisible(false);
            //Showing the Login Panel with CardLayout
            cardLayout.show(layoutPanel, "Login");
        });
        //Key Listener for when the user successfully registers, the successlbl displays, when the user types in the username textBox, the label will disappear
        txtLogUsername.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (txtLogUsername.getText().length() > 0) {
                    lblSuccess.setVisible(false);
                }
            }
        });
        //Key Listener to add checks when the user has more than 8 character, and uppercase and special character
        txtRegPass.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char[] password = txtRegPass.getPassword();//Converting the password as a character array
                boolean hasUpper = false;
                boolean hasSpecial = false;

                if (txtRegPass.getPassword().length > 7) {//if text is greater than 8, Green check
                    lblPassX1.setVisible(false);
                    lblPassCheck1.setVisible(true);
                } else {
                    lblPassX1.setVisible(true);
                    lblPassCheck1.setVisible(false);
                }

                for (int i = 0; i < password.length; i++) {
                    char ch = password[i];//Getting each character in the array to check it
                    if (Character.isUpperCase(ch)) {
                        hasUpper = true;
                    }

                    if (!Character.isLetterOrDigit(ch)) {
                        hasSpecial = true;
                    }
                }
                //Setting the visibility of the special and upper characters needed
                if (hasUpper) {
                    lblPassX3.setVisible(false);
                    lblPassCheck3.setVisible(true);
                } else {
                    lblPassX3.setVisible(true);
                    lblPassCheck3.setVisible(false);
                }

                if (hasSpecial) {
                    lblPassX2.setVisible(false);
                    lblPassCheck2.setVisible(true);
                } else {
                    lblPassX2.setVisible(true);
                    lblPassCheck2.setVisible(false);
                }
            }
        });
        //Calling the methods to populate the customers and employees 
        populateCustomers();
        populateEmployees();

        managerView = new ManagerView();
        managerView.load();//Loading employees
    }

    //Method to display Loging Menu
//    void enter() {
//        System.out.println("Login Menu");
//        managerView.load();//Loading employees
//        load();//Loading customers
//
//    }
//    //Method to update the state, based on user input 
//    void update() {
//
//        System.out.println("1. Login");
//        System.out.println("2. Register");
//        System.out.println("3. Continue as Guest");
//        System.out.println("4. Exit");
//
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        switch (input) {
//            case "1":
//                login();
//                break;
//            case "2":
//                register();
//                break;
//            case "3":
//                current = customerView;
//                break;
//            case "4":
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Error, Please try again!");
//                enter();
//                update();
//        }
//
//    }
    //Method to add customers to the arrayList from the database, if connection fails it adds them from the file
    private void populateCustomers() {
        if (customers.isEmpty()) {
            //A thread to populate the customers array from the database
            Thread dbThread = new Thread(() -> {
                try {
                    if (tryConnection()) {
                        customers.clear();//Clearing the customers ArrayList
                        String query = "select customerID, name, address, userName, phoneNumber, password from Customers";
                        PreparedStatement ps = con.prepareStatement(query);
                        ResultSet rs = ps.executeQuery();
                        //Storing table informationg to variables, adding them to the customers array
                        while (rs.next()) {
                            int id = rs.getInt("customerID");
                            String name = rs.getString("name");
                            String address = rs.getString("address");
                            String loginName = rs.getString("userName");
                            String phoneNumber = rs.getString("phoneNumber");
                            String password = rs.getString("password");
                            Customer customer = new Customer(id, name, address, loginName, phoneNumber, password);//Creating new customer objects from the customer class
                            customers.add(customer);//Adding the customers to the customers ArrayList
                        }
                    } else {
                        load(); //Loading the customers from the file if the connection fails
                    }
                    //System.out.println(customers);
                } catch (SQLException ex) {
                    System.out.println("SQL error when populating the customers from the database into the arraylist. " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            dbThread.start();
        }
    }

    //Method to add customers to the arrayList from the database, if connection fails it adds them from the file
    private void populateEmployees() {
        if (employees.isEmpty()) {
            //A thread to populate the employees array from the database
            Thread dbThread = new Thread(() -> {
                try {
                    if (tryConnection()) {
                        employees.clear();//Clearing the customers ArrayList
                        String query = "select employeeID, name, address, userName, phoneNumber, password, isManager, startDate, salary "
                                + "from Employees";
                        PreparedStatement ps = con.prepareStatement(query);
                        ResultSet rs = ps.executeQuery();
                        //Storing table informationg to variables, adding them to the customers array
                        while (rs.next()) {
                            int idNumber = rs.getInt("employeeID");
                            String name = rs.getString("name");
                            String address = rs.getString("address");
                            String loginName = rs.getString("userName");
                            String phoneNumber = rs.getString("phoneNumber");
                            String password = rs.getString("password");
                            boolean isTheManager = rs.getBoolean("isManager");
                            String startDate = rs.getString("startDate");
                            double salary = rs.getDouble("salary");
                            TravelAgencyEmployee employee = new TravelAgencyEmployee(idNumber, name, address, loginName, password, phoneNumber, isTheManager, startDate, salary);//Creating new customer objects from the customer class
                            employees.add(employee);//Adding the customers to the customers ArrayList
                        }
                    } else {
                        load();//Loading the employees from the file if the connection fails
                    }
                } catch (SQLException ex) {
                    System.out.println("SQL error when populating employees from the database into the arraylist." + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            dbThread.start();
        }
    }

    //login Method 
    public void login(String userName, String password) {
        boolean loginSuccess = false;
        //For loop to find if the user input the correct name and password for an employee or manager
        for (int i = 0; i < employees.size(); i++) {
            TravelAgencyEmployee employee = employees.get(i);
            //Decrypting the password 
            String storedPass = employee.getPassword();
            String decryptPass = decrypt(storedPass);//Passing the stored password to get decrypted
            if (employee.getLoginName().equals(userName) && decryptPass.equals(password)) {
                if (employee.isManager) {
                    isManager = true;//Setting the permissions to Manager for access in the employeeView class
                    current = managerView;
                    loginSuccess = true;
                } else {
                    isManager = false;//Setting the permissions to not a Manager. 
                    lblLoginFail.setVisible(false);
                    txtLogUsername.setText("");
                    txtLogPass.setText("");
                    //Using CardLayout to show EmployeeView
                    cardLayout.show(layoutPanel, "EmployeeView");
                    EmployeeView.imagePanel.removeAll();
                    EmployeeView.imagePanel.revalidate();
                    EmployeeView.imagePanel.repaint();
                    loginSuccess = true;
                }

            }
        }
        //For loop to find if the user input the correct name and password for a customer
        for (int i = 0; i < customers.size(); i++) {
            Customer newCustomer = customers.get(i);//Creating a Customer object
            String storedPass = newCustomer.getPassword();
            String decryptPass = decrypt(storedPass);

            if (newCustomer.getLoginName().equals(userName) && decryptPass.equals(password)) {
                CustomerView customerName = new CustomerView();
                customerName.customerUserName = newCustomer.getLoginName();
                lblLoginFail.setVisible(false);
                txtLogUsername.setText("");
                txtLogPass.setText("");
                cardLayout.show(layoutPanel, "CustomerView");
                loginSuccess = true;
            }
        }
        if (!loginSuccess) {
            lblLoginFail.setVisible(true);
        }
    }

    //Method to register 
    private void register(String name, String address, String loginName, String phoneNumber, String password) {
        // Assigning ID based on size of employee list
        int idNumber = customers.size() + 1;
        if (validPass(password)) {
            //Encrypting the password before creating customer object
            String encryptPass = encrypt(password);
            Customer customer = new Customer(idNumber, name, address, loginName, encryptPass, phoneNumber);
            customers.add(customer);//Adding customer to ArrayList

            //A thread to handle customer registration in the database
            Thread dbThread = new Thread(() -> {
                try {
                    //Checking the connection before accessing the database
                    if (tryConnection()) {
                        System.out.println("IN REGISTER METHOD");
                        String query = "INSERT INTO Customers (name, address, userName, phoneNumber, password) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(query);

                        //Adding customer information to the database
                        ps.setString(1, name);
                        ps.setString(2, address);
                        ps.setString(3, loginName);
                        ps.setString(4, encryptPass);
                        ps.setString(5, phoneNumber);
                        ps.executeUpdate();

                        // Clear input fields and show success message
                        lblSuccess.setVisible(true);
                        txtName.setText("");
                        txtAddress.setText("");
                        txtRegUsername.setText("");
                        txtPhone.setText("");
                        txtRegPass.setText("");
                        lblError.setVisible(false); // Clearing the error label
                        cardLayout.show(layoutPanel, "Login");
                    } else {
                        save(); //Loading the customers from the file if the connection fails
                    }
                } catch (SQLException ex) {
                    lblError.setVisible(true);
                    ex.printStackTrace();
                }
            });

            dbThread.start(); //Starting the thread for database operation
        } else {
            System.out.println("Password must be 8 characters, include 1 capital letter, and 1 special character.");
            validPass(password);
        }

    }

    //Validating password method
    public boolean validPass(String password) {
        if (password.length() < 8) {
            return false;//If password length is less than 8 characters, return false
        }

        boolean hasUpper = false;
        boolean hasSpecial = false;

        //Checking over each character in the password array to make user there is an uppercase and one special character
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
            if (hasUpper && hasSpecial) {
                return true;
            }
        }
        return hasUpper && hasSpecial;
    }

    //Method to encrypt users password   
    //Method needs to be static for when manager creates a employee, the ManagerView class can access this method
    //to encrypt the employees password before it is saved.
    public static String encrypt(String password) {
        char[] characters = password.toCharArray();//Converting password string to character array
        for (int i = 0; i < characters.length; i++) {
            characters[i] += 4;//Moving each character by 4 posistions
        }
        return new String(characters);//Returning encrypted password as a new string
    }

    //Method to decrypt users password
    public String decrypt(String password) {
        char[] characters = password.toCharArray();//Converting password string to character array
        for (int i = 0; i < characters.length; i++) {
            characters[i] -= 4;//Moving each character by 4 posistions to change back to correct password
        }
        return new String(characters);//Returning decrypted password as a new string
    }

    @Override
    //Method to save customer information to the file
    void save() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt"))) {
                for (int i = 0; i < customers.size(); i++) {
                    Customer customer = customers.get(i);
                    //Writing the string representation of the customer to the file
                    bw.write(customer.getIdNumber() + "|" + customer.getName() + "|" + customer.getAddress()
                            + "|" + customer.getLoginName() + "|" + customer.getPhoneNumber() + "|" + customer.getPassword());
                    bw.newLine();//Writing a new line to seperate the customers information
                }
                //Closing the writer after writing all customers
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    void load() {
        customers.clear();//Clearing the list before loading to prevent duplicate entries
        System.out.println("Loading customers from file");
        try {
            BufferedReader br = new BufferedReader(new FileReader("customers.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                //Splitting the input line by | to access individual fields
                String[] cDetails = line.split("\\|");
                int id = Integer.parseInt(cDetails[0]);
                String name = cDetails[1];
                String address = cDetails[2];
                String loginName = cDetails[3];
                String phoneNumber = cDetails[4];
                String password = cDetails[5];
                //Creating customer and adding it to the customers list
                Customer customer = new Customer(id, name, address, loginName, password, phoneNumber);//Creating new customer objects from the customer class
                customers.add(customer);//Adding the new customer to the customers ArrayList
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
