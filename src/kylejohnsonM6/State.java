package kylejohnsonM6;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.*;//Import for swing application
import java.sql.*;

public abstract class State {

    static State loginState, customerView, employeeView, managerView, current;
    //The cardLayout for switching between panels
    static CardLayout cardLayout = new CardLayout();
    //The JFrame and JPanel
    static JFrame mainFrame = new JFrame();
    JPanel mainPanel = new JPanel();
    static final JPanel layoutPanel = new JPanel(cardLayout);
    static Connection con;
    static JLabel[] connectLabels = new JLabel[4];//An array for the 4 labels for the connection status
    static JLabel lblStatusLogin = new JLabel("Connecting...");//Label for LoginState
    static JLabel lblStatusReg = new JLabel("Connecting...");//Label for registerPanel
    static JLabel lblStatusCust = new JLabel("Connecting...");//Label for CustomerView
    static JLabel lblStatusEmp = new JLabel("Connecting...");//Label for EmployeeView

    //Constructor 
    public State() {
        //Adding the labels to the arraylist
        connectLabels[0] = lblStatusLogin;
        connectLabels[1] = lblStatusReg;
        connectLabels[2] = lblStatusCust;
        connectLabels[3] = lblStatusEmp;
    }

    //Method to check if there is a connection to the database, if there is not it attempts to reconnect
    public boolean tryConnection() {
        try {
            //Checking to see if the connection is valid
            if (con != null && con.isValid(2)) {
                return true;//Returning the connection is valid
            }
            openConnection();//Attempting to open a new connection
            
            //Checking to see if the new connection is valid
            if (con != null && con.isValid(2)) {
                System.out.println("Connection re-established.");
                return true;//Returning the connection is valid
            }

        } catch (SQLException ex) {
            System.out.println("Connection attempt failed");
            ex.printStackTrace();
        }
        //If connection fails, returns false
        return false;
    }

    //Method to open the connection to the database
    public void openConnection() {
        String mySQLURL = "jdbc:mysql://13.58.236.216:3306/kjohnsonsu24";
        String userName = "kjohnsonsu24";
        String password = "cpt_Tstc1";
        //Try catch for opening the connection
        try {
            con = DriverManager.getConnection(mySQLURL, userName, password);
            //System.out.println("connection status " + con.isValid(0));
        } catch (SQLException ex) {
            System.out.println("Connection to database failed.");
        }
    }

    //Method to close the connection
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException ex) {
            System.out.println("Error closing connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //Method to update the connection status label
    private void updateConnectionStatus(boolean connected) {
        //Using Ternary Operators to set the text and color for the label
        String lblTxt = connected ? "Connected" : "Disconnected";
        Color clrTxt = connected ? new Color(9,82,86) : new Color(159,74,84);

        for (JLabel label : connectLabels) {
            label.setText(lblTxt);
            label.setForeground(clrTxt);
        }
        mainPanel.repaint();//Updating the Panels with the updated labels
    }

    //Explanation For Timer class lambda expression: (When reading about the timer class it said that the timer class provides a method call that is 
    //used by a thread to schedule a task. It also said that each timer thread is associated with a background thread. It is thread safe.) So to my understanding the 
    //timer i created, creates a background thread with the lambda expression to define the action to perform every 3 seconds which keeps checking the connection
    //and updating the labels
    
    //Method to check the connection
    public void connectionCheck() {
        //Using a lambda timer thread to check every 3 seconds if there is a connection
        new Timer(3000, e -> {
            boolean connected = tryConnection();
            if (connected) {//If there is a connection, it is updating the labels to true or false
                updateConnectionStatus(true);//Updating the connection status
            } else {
                System.out.println("Attempting to reconnect...");
                updateConnectionStatus(false);
            }

        }).start();//Starting the timer
    }

    //Abstract Methods
//    void enter() {
//    }
//
//    void update() {
//    }
    abstract void save();

    abstract void load();

}
