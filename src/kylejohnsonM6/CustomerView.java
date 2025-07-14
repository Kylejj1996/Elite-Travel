package kylejohnsonM6;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.*;//Import for swing application
import javax.swing.filechooser.FileSystemView;

public class CustomerView extends State {
    ArrayList<Order> currentOrder = new ArrayList();
    private boolean loadInProgress = false;
    private String selectedLodge = "";
    //Components for customerView Panel
    String customerUserName;
    DefaultListModel lodgingModel;
    DefaultListModel lodgingDetails;
    JComboBox cbxLodge;
    JScrollPane jspImages;
    JList lstLodging;
    JList lstDetails;
    JPanel imagePanel;
    JPanel panelOrderHistory;
    JComboBox cbxMStart;
    JComboBox cbxDStart;
    JComboBox cbxYStart;
    JComboBox cbxMEnd;
    JComboBox cbxDEnd;
    JComboBox cbxYEnd;
    JComboBox cbxOMStart;
    JComboBox cbxODStart;
    JComboBox cbxOYStart;
    JComboBox cbxOMEnd;
    JComboBox cbxODEnd;
    JComboBox cbxOYEnd;
    JTextArea txtReceipt;
    JLabel lblError;
    JLabel lblErrorDate;
    JLabel lblSuccess;
    JLabel lblCancel;
    JLabel lblOrderDetails;
    JLabel lblCustOnly;
    JLabel lblTitle;
    Button btnConfirmOrder;
    Button btnCancel;
    Button btnAddLodging;
    Button btnNew;
    Button btnRefresh;

    public CustomerView() {
        mainPanel.setBackground(new Color(144, 174, 173));//Changing the background color
        mainPanel.setBorder(BorderFactory.createTitledBorder("Elite Travel Customers"));
        mainPanel.setSize(1200, 1000);
        mainPanel.setLayout(null);

        //Adding the CustomerView mainPanel to the cardLayout
        layoutPanel.add(mainPanel, "CustomerView");

        //Creating a Color object to set the color of the borders
        Color border = new Color(36, 72, 85);
        //Panel to hold lodging images
        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(251, 233, 208));
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));//Setting the imagePanel with a flowLayout

        //JScrollPane for the images 
        jspImages = new JScrollPane(imagePanel);
        jspImages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspImages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jspImages.setBounds(200, 730, 940, 120);
        jspImages.setBorder(BorderFactory.createLineBorder(border, 4));

        //----Components for panelOrderHistory----
        Label lblOrder = new Label("Order History");
        lblOrder.setBounds(940, 260, 100, 20);
        Label lblOStart = new Label("Start Dates:");
        lblOStart.setBounds(90, 10, 100, 20);
        Label lblOEnd = new Label("End Dates:");
        lblOEnd.setBounds(90, 190, 100, 20);
        Button btnOrderHistory = new Button("Submit");
        btnOrderHistory.setBounds(90, 330, 100, 30);

        //Panel for the customer reports
        panelOrderHistory = new JPanel();
        panelOrderHistory.setBorder(BorderFactory.createLineBorder(border, 4));
        panelOrderHistory.setBackground(new Color(251, 233, 208));
        panelOrderHistory.setBounds(860, 280, 250, 400);
        panelOrderHistory.setLayout(null);

        //Comboboxess for the OrderHistory Panel
        cbxOMStart = new JComboBox();
        cbxOMStart.setBounds(120, 40, 100, 20);
        cbxODStart = new JComboBox();
        cbxODStart.setBounds(120, 70, 100, 20);
        cbxOYStart = new JComboBox();
        cbxOYStart.setBounds(120, 100, 100, 20);
        cbxOMEnd = new JComboBox();
        cbxOMEnd.setBounds(120, 220, 100, 20);
        cbxODEnd = new JComboBox();
        cbxODEnd.setBounds(120, 250, 100, 20);
        cbxOYEnd = new JComboBox();
        cbxOYEnd.setBounds(120, 280, 100, 20);

        //labels for comboboxes
        Label lblOMStart = new Label("Month:");
        lblOMStart.setBounds(50, 40, 50, 20);
        Label lblODStart = new Label("Day:");
        lblODStart.setBounds(50, 70, 50, 20);
        Label lblOYStart = new Label("Year:");
        lblOYStart.setBounds(50, 100, 50, 20);
        Label lblOMEnd = new Label("Month:");
        lblOMEnd.setBounds(50, 220, 50, 20);
        Label lblODEnd = new Label("Day:");
        lblODEnd.setBounds(50, 250, 50, 20);
        Label lblOYEnd = new Label("Year:");
        lblOYEnd.setBounds(50, 280, 50, 20);
        
        lblTitle = new JLabel("Elite Travel Booking");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitle.setForeground(Color.BLACK);
        
        //Adding components to the panelOrderHistory
        panelOrderHistory.add(lblOStart);
        panelOrderHistory.add(lblOEnd);
        panelOrderHistory.add(cbxOMStart);
        panelOrderHistory.add(cbxODStart);
        panelOrderHistory.add(cbxOYStart);
        panelOrderHistory.add(cbxOMEnd);
        panelOrderHistory.add(cbxODEnd);
        panelOrderHistory.add(cbxOYEnd);
        panelOrderHistory.add(lblOMStart);
        panelOrderHistory.add(lblODStart);
        panelOrderHistory.add(lblOYStart);
        panelOrderHistory.add(lblOMEnd);
        panelOrderHistory.add(lblODEnd);
        panelOrderHistory.add(lblOYEnd);
        panelOrderHistory.add(btnOrderHistory);

        //---Creating components for employeeViewPanel---
        //JList to display different Lodging options
        lstLodging = new JList();
        lstLodging.setBackground(new Color(251, 233, 208));
        JScrollPane jspLodging = new JScrollPane(lstLodging);//Implementing the scrolling for the JList
        jspLodging.setBorder(BorderFactory.createLineBorder(border, 4));//Adding a border to the JScrollPane
        jspLodging.setBounds(80, 280, 350, 200);

        lstDetails = new JList();
        lstDetails.setBackground(new Color(251, 233, 208));
        JScrollPane jspDetails = new JScrollPane(lstDetails);//Implementing the scrolling for the JList
        jspDetails.setBorder(BorderFactory.createLineBorder(border, 4));//Adding a border to the JScrollPane
        jspDetails.setBounds(490, 280, 350, 200);

        //JTextArea to display the receipt
        txtReceipt = new JTextArea();
        txtReceipt.setBackground(new Color(255, 233, 208));
        txtReceipt.setBorder(BorderFactory.createLineBorder(border, 4));//Adding a border to the JTextArea
        txtReceipt.setFont(new Font("Arial", Font.BOLD, 13));
        txtReceipt.setForeground(Color.BLACK);
        txtReceipt.setEditable(false);

        //Labels
        Label lblLodgeType = new Label("Select Lodge Type:");
        Label lblNumNights = new Label("Select Dates");
        Label lblStart = new Label("Start date:");
        Label lblEnd = new Label("End date:");

        JLabel lblLodging = new JLabel("Lodging Details");
        lblLodging.setFont(new Font("Arial", Font.BOLD, 20));//Setting the font for the Lodgings label above the JTable

        lblOrderDetails = new JLabel("Order Details");
        lblOrderDetails.setFont(new Font("Arial", Font.BOLD, 20));
        lblOrderDetails.setVisible(true);

        JLabel lblReceipt = new JLabel("Receipt");
        lblReceipt.setFont(new Font("Arial", Font.BOLD, 20));
        lblReceipt.setVisible(false);

        //Label for error message
        lblError = new JLabel("Please select lodge type and dates of stay.");
        lblError.setFont(new Font("Arial", Font.BOLD, 14));
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);

        //Label to display error for customer order history reports
        lblCustOnly = new JLabel("Create an Account to access Order History");
        lblCustOnly.setFont(new Font("Arial", Font.BOLD, 18));
        lblCustOnly.setForeground(Color.RED);
        lblCustOnly.setVisible(false);

        //label to display error for date range
        lblErrorDate = new JLabel("Invalid date range. Start date must be before End date.");
        lblErrorDate.setFont(new Font("Arial", Font.BOLD, 14));
        lblErrorDate.setForeground(Color.RED);
        lblErrorDate.setVisible(false);

        lblSuccess = new JLabel("Checkout Successful!");
        lblSuccess.setVisible(false);
        lblSuccess.setFont(new Font("Arial", Font.BOLD, 20));

        //Label for cancel order message
        lblCancel = new JLabel("Order Cancelled!");
        lblCancel.setVisible(false);
        lblCancel.setFont(new Font("Arial", Font.BOLD, 20));
        lblCancel.setForeground(Color.RED);

        //Setting the connection status label
        lblStatusCust.setBounds(560, 870, 200, 20);

        //ComboBox for the user to select a Hotel or House
        String[] selectLodge = {"Hotel", "House"};//Index 0 and 1
        cbxLodge = new JComboBox(selectLodge);
        cbxLodge.setVisible(true);
        cbxLodge.setSelectedIndex(0);

        //Combo boxes for dates
        cbxMStart = new JComboBox<>();
        cbxDStart = new JComboBox<>();
        cbxYStart = new JComboBox<>();
        cbxMEnd = new JComboBox<>();
        cbxDEnd = new JComboBox<>();
        cbxYEnd = new JComboBox<>();

        //Buttons
        btnAddLodging = new Button("Add Lodging");
        btnConfirmOrder = new Button("Confirm Order");
        btnConfirmOrder.setVisible(false);
        btnCancel = new Button("Cancel Order");
        btnCancel.setVisible(false);
        btnNew = new Button("New Order");
        btnNew.setEnabled(false);
        Button btnBack = new Button("Back");
        btnRefresh = new Button("Refresh");

        //Adusting where each component should go in the panel
        lstLodging.setBounds(530, 50, 350, 350);
        txtReceipt.setBounds(490, 510, 350, 150);
        lblTitle.setBounds(420, 150, 400, 50);
        lblOrderDetails.setBounds(590, 470, 200, 50);
        lblReceipt.setBounds(620, 470, 200, 50);
        lblLodging.setBounds(580, 240, 150, 50);
        lblLodgeType.setBounds(110, 240, 150, 30);
        lblNumNights.setBounds(200, 490, 180, 30);
        lblStart.setBounds(70, 530, 100, 30);
        lblEnd.setBounds(70, 570, 100, 30);
        lblError.setBounds(470, 880, 330, 30);
        lblErrorDate.setBounds(430, 900, 400, 30);
        lblCustOnly.setBounds(385, 100, 500, 20);
        lblSuccess.setBounds(470, 40, 300, 30);
        lblCancel.setBounds(500, 40, 300, 30);
        cbxLodge.setBounds(270, 240, 100, 30);
        cbxMStart.setBounds(160, 530, 80, 30);
        cbxDStart.setBounds(245, 530, 80, 30);
        cbxYStart.setBounds(330, 530, 80, 30);
        cbxMEnd.setBounds(160, 570, 80, 30);
        cbxDEnd.setBounds(245, 570, 80, 30);
        cbxYEnd.setBounds(330, 570, 80, 30);
        btnAddLodging.setBounds(80, 630, 150, 30);
        btnNew.setBounds(270, 630, 150, 30);
        btnConfirmOrder.setBounds(510, 680, 150, 30);
        btnCancel.setBounds(675, 680, 150, 30);
        btnBack.setBounds(20, 820, 130, 30);
        btnRefresh.setBounds(20, 780, 130, 30);

        //Adding components to the panel
        mainPanel.add(lblLodging);
        mainPanel.add(lblOrderDetails);
        mainPanel.add(jspLodging);
        mainPanel.add(jspDetails);
        mainPanel.add(jspImages);
        mainPanel.add(txtReceipt);
        mainPanel.add(lblReceipt);
        mainPanel.add(lblLodgeType);
        mainPanel.add(lblNumNights);
        mainPanel.add(lblStart);
        mainPanel.add(lblEnd);
        mainPanel.add(lblError);
        mainPanel.add(lblErrorDate);
        mainPanel.add(lblSuccess);
        mainPanel.add(lblCancel);
        mainPanel.add(cbxLodge);
        mainPanel.add(cbxMStart);
        mainPanel.add(cbxDStart);
        mainPanel.add(cbxYStart);
        mainPanel.add(cbxMEnd);
        mainPanel.add(cbxDEnd);
        mainPanel.add(cbxYEnd);
        mainPanel.add(btnAddLodging);
        mainPanel.add(btnNew);
        mainPanel.add(btnConfirmOrder);
        mainPanel.add(btnCancel);
        mainPanel.add(btnBack);
        mainPanel.add(btnRefresh);
        mainPanel.add(lblTitle);
        mainPanel.add(panelOrderHistory);
        mainPanel.add(lblOrder);
        mainPanel.add(lblCustOnly);
        mainPanel.add(lblStatusCust);
        mainPanel.setVisible(false);
        mainFrame.add(layoutPanel);

        dateComboBoxes();//Calling the method to fill in the comboBoxes for dates
        btnBack.addActionListener(e -> {
            lblError.setVisible(false);
            lblErrorDate.setVisible(false);
            lblSuccess.setVisible(false);
            lblCustOnly.setVisible(false);
            lstLodging.setEnabled(true);
            txtReceipt.setText("");
            btnAddLodging.setEnabled(true);
            lblReceipt.setVisible(false);
            lblOrderDetails.setVisible(true);
            btnRefresh.setEnabled(true);
            cardLayout.show(layoutPanel, "Login");

            //Enabling the comboboxes
            cbxMStart.setEnabled(true);
            cbxDStart.setEnabled(true);
            cbxYStart.setEnabled(true);
            cbxMEnd.setEnabled(true);
            cbxDEnd.setEnabled(true);
            cbxYEnd.setEnabled(true);

            getOrdersFromDatabase();
            txtReceipt.setText("");
            cbxLodge.setSelectedIndex(0);
            //Setting the JList with a new DefaultListModel that is empty to clear the List of lodgings
            DefaultListModel<String> emptyModel = new DefaultListModel<>();
            lstLodging.setModel(emptyModel);
            viewLodges(0);
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
        });

        lstLodging.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() == false) {
                imagePanel.removeAll();
                imagePanel.revalidate();
                imagePanel.repaint();
                String selectedLodge = (String) lstLodging.getSelectedValue();
                lblSuccess.setVisible(false);
                lblCancel.setVisible(false);
                btnNew.setEnabled(false);
                int lodgingType = cbxLodge.getSelectedIndex();
                int lodgeIndex = lstLodging.getSelectedIndex();
                String selectedAddress = null;
                if (lodgeIndex != -1) {//Checking to see if a house is selected
                    lblError.setVisible(false);
                    lblErrorDate.setVisible(false);
                    //Getting the selected lodge address to display the correct information
                    if (lodgingType == 0) {
                        Hotel selectedHotel = EmployeeView.lodgingHotel.get(lodgeIndex); // Get the hotel from the list
                        selectedAddress = selectedHotel.getAddress();
                    } else if (lodgingType == 1) {
                        House selectedHouse = EmployeeView.lodgingHouse.get(lodgeIndex); // Get the house from the list
                        selectedAddress = selectedHouse.getAddress();
                    }
                }
                //Creating a new list model to clear the list when the user selects a new lodge
                DefaultListModel clear = new DefaultListModel();
                lstDetails.setModel(clear);
                addDetails(lodgingType, lodgeIndex);//calling the addDetails method to display hotel or house details
                //loading the images for the selected hotel
                loadLodgeImages(selectedLodge, selectedAddress);
            }
        });

        //Action Listener to add the Lodgings to the table 
        cbxLodge.addActionListener(e -> {
            int type = cbxLodge.getSelectedIndex();
            lblError.setVisible(false);
            lblErrorDate.setVisible(false);
            btnNew.setEnabled(false);
            lblSuccess.setVisible(false);
            lblCancel.setVisible(false);
            viewLodges(type);
        });

        cbxDStart.addActionListener(e -> {
            lblErrorDate.setVisible(false);
        });
        cbxDEnd.addActionListener(e -> {
            lblErrorDate.setVisible(false);
        });

        //Action Listener that changes the days when the start month is selected
        cbxMStart.addActionListener(e -> {
            int selectedMonth = cbxMStart.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxYStart.getSelectedItem());
            lblError.setVisible(false);
            lblErrorDate.setVisible(false);
            updateStartCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the start year is selected 
        cbxYStart.addActionListener(e -> {
            int selectedMonth = cbxMStart.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxYStart.getSelectedItem());
            updateStartCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the end year is selected
        cbxYEnd.addActionListener(e -> {
            int selectedMonth = cbxMEnd.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxYEnd.getSelectedItem());
            updateEndCbx(selectedMonth, year);
        });

        //Action Listener that changes the days when the end month is selected
        cbxMEnd.addActionListener(e -> {
            int selectedMonth = cbxMEnd.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxYEnd.getSelectedItem());
            lblError.setVisible(false);
            lblErrorDate.setVisible(false);
            updateEndCbx(selectedMonth, year);
        });

        //Action Listener for the add lodging button
        btnAddLodging.addActionListener(e -> {
            int lodgeNum = lstLodging.getSelectedIndex();
            int lodgeType = cbxLodge.getSelectedIndex();
            btnConfirmOrder.setVisible(true);
            btnCancel.setVisible(true);
            btnAddLodging.setEnabled(false);
            btnRefresh.setEnabled(false);
            try {
                //Checking if the comboBoxes are filled out
                if (cbxMStart.getSelectedIndex() == -1 || cbxDStart.getSelectedIndex() == -1 || cbxYStart.getSelectedIndex() == -1
                        || cbxMEnd.getSelectedIndex() == -1 || cbxDEnd.getSelectedIndex() == -1 || cbxYEnd.getSelectedIndex() == -1) {
                    lblErrorDate.setText("Please select Start and end dates.");
                    lblErrorDate.setVisible(true);//Printing a error label
                    btnConfirmOrder.setVisible(false);
                    btnCancel.setVisible(false);
                    btnAddLodging.setEnabled(true);
                    btnRefresh.setEnabled(true);
                    //Enabling the comboboxes 
                    cbxMStart.setEnabled(true);
                    cbxDStart.setEnabled(true);
                    cbxYStart.setEnabled(true);
                    cbxMEnd.setEnabled(true);
                    cbxDEnd.setEnabled(true);
                    cbxYEnd.setEnabled(true);
                    return;
                }
                // Checking if any of the required fields are empty
                if (lodgeNum == -1) {
                    lblError.setVisible(true);//Printing a error label
                    btnConfirmOrder.setVisible(false);
                    btnCancel.setVisible(false);
                    btnAddLodging.setEnabled(true);
                    btnRefresh.setEnabled(true);
                    return;//Exiting the method 
                }

                //Enabling the comboboxes
                cbxMStart.setEnabled(false);
                cbxDStart.setEnabled(false);
                cbxYStart.setEnabled(false);
                cbxMEnd.setEnabled(false);
                cbxDEnd.setEnabled(false);
                cbxYEnd.setEnabled(false);

                viewOrderDetails(lodgeNum, lodgeType);//Calling this method to display details in the textbox before the user confirms order
            } catch (Exception ex) {
                System.out.println("Error occured when attempting to display the customer order details. " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        //ActionListener that changes the correct days when a month is selected for the (reports panel)
        cbxOMStart.addActionListener(e -> {
            int selectedMonth = cbxOMStart.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxOYStart.getSelectedItem());
            updateStartCbx(selectedMonth, year);
        });

        cbxOMEnd.addActionListener(e -> {
            int selectedMonth = cbxOMEnd.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxOYEnd.getSelectedItem());
            updateEndCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the start year is selected 
        cbxOYStart.addActionListener(e -> {
            int selectedMonth = cbxOMStart.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxOYStart.getSelectedItem());
            updateStartCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the end year is selected 
        cbxOYEnd.addActionListener(e -> {
            int selectedMonth = cbxOMEnd.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxOYEnd.getSelectedItem());
            updateEndCbx(selectedMonth, year);
        });
        //Action Listener for the get reports button 
        btnOrderHistory.addActionListener(e -> {
            try {
                //Getting the start dates and end dates from the comboBoxes
                int startMonth = cbxOMStart.getSelectedIndex() + 1;
                int startDay = Integer.parseInt((String) cbxODStart.getSelectedItem());
                int startYear = Integer.parseInt((String) cbxOYStart.getSelectedItem());

                int endMonth = cbxOMEnd.getSelectedIndex() + 1;
                int endDay = Integer.parseInt((String) cbxODEnd.getSelectedItem());
                int endYear = Integer.parseInt((String) cbxOYEnd.getSelectedItem());

                //Creating string variable to hold the correct date
                String startDates = startYear + "-" + startMonth + "-" + startDay;
                String endDates = endYear + "-" + endMonth + "-" + endDay;

                // Create LocalDate objects for start and end dates
                LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
                LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

                // Calculate the number of days between the dates
                long diff = ChronoUnit.DAYS.between(startDate, endDate);
                //If else to check that the date range is valid
                if (diff <= 0) {
                    lblErrorDate.setVisible(true);
                    return;
                }
                //Calling the get Reprots method to load the customer reports
                getReports(startDates, endDates);
            } catch (NumberFormatException ex) {
                lblErrorDate.setVisible(true);
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("A error occured when getting the order history for the customer. " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        //Action Listener for the checkout button
        btnConfirmOrder.addActionListener(e -> {
            System.out.println("Customer Name: " + customerUserName);
            int lodgeNum = lstLodging.getSelectedIndex();
            int lodgeType = cbxLodge.getSelectedIndex();
            orderDetails(lodgeNum, lodgeType);//Calling the orderDetails method to add the lodging to the arraylist and database
            cbxLodge.setSelectedIndex(0);
            //When user checks out, enabling the sumbit and lstLodgings 
            btnAddLodging.setEnabled(false);
            lstLodging.setEnabled(false);
            btnConfirmOrder.setVisible(false);
            btnCancel.setVisible(false);
            lblOrderDetails.setVisible(false);
            lblReceipt.setVisible(true);
            lblSuccess.setVisible(true);
            btnNew.setEnabled(true);
            //Setting the JList with a new DefaultListModel that is empty to clear the List of lodgings
            DefaultListModel<String> emptyModel = new DefaultListModel<>();
            lstLodging.setModel(emptyModel);

            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();

        });
        //ActionListener for the cancel button
        btnCancel.addActionListener(e -> {
            //Creating an object of the GregorainCalendar class
            GregorianCalendar cal = new GregorianCalendar();

            //Getting the current date to set to the combo boxes back to the current date
            int currentMonth = cal.get(Calendar.MONTH);
            int currentDay = cal.get(Calendar.DAY_OF_MONTH);
            int currentYear = cal.get(Calendar.YEAR);

            //Enabling the comboboxes 
            cbxMStart.setEnabled(true);
            cbxDStart.setEnabled(true);
            cbxYStart.setEnabled(true);
            cbxMEnd.setEnabled(true);
            cbxDEnd.setEnabled(true);
            cbxYEnd.setEnabled(true);
            //Setting the current month as the selected month in the combo box
            cbxMStart.setSelectedIndex(currentMonth);
            cbxMEnd.setSelectedIndex(currentMonth);

            //Setting the current year as the selected year in the combo box
            cbxYStart.setSelectedItem(String.valueOf(currentYear));
            cbxYEnd.setSelectedItem(String.valueOf(currentYear));

            //Setting the current day as the selected day in the combo box
            cbxDStart.setSelectedItem(String.valueOf(currentDay));
            cbxDEnd.setSelectedItem(String.valueOf(currentDay));

            txtReceipt.setText("");
            //When user cancels the order, enabling the sumbit and lstLodgings 
            btnAddLodging.setEnabled(true);
            lstLodging.setEnabled(true);
            btnConfirmOrder.setVisible(false);
            btnCancel.setVisible(false);
            lblCancel.setVisible(true);
            lblOrderDetails.setVisible(true);
            btnRefresh.setEnabled(true);
            //Setting the JList with a new DefaultListModel that is empty to clear the List of lodgings
            DefaultListModel<String> emptyModel = new DefaultListModel<>();
            lstLodging.setModel(emptyModel);
            lblCancel.setVisible(true);

            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            viewLodges(0);
        });
        //Action Listener for a new order
        btnNew.addActionListener(e -> {
            btnNew.setEnabled(false);
            //Creating an object of the GregorainCalendar class
            GregorianCalendar cal = new GregorianCalendar();

            //Getting the current date to set to the combo boxes back to the current date
            int currentMonth = cal.get(Calendar.MONTH);
            int currentDay = cal.get(Calendar.DAY_OF_MONTH);
            int currentYear = cal.get(Calendar.YEAR);

            //Setting the current month as the selected month in the combo box
            cbxMStart.setSelectedIndex(currentMonth);
            cbxMEnd.setSelectedIndex(currentMonth);

            //Setting the current year as the selected year in the combo box
            cbxYStart.setSelectedItem(String.valueOf(currentYear));
            cbxYEnd.setSelectedItem(String.valueOf(currentYear));

            //Setting the current day as the selected day in the combo box
            cbxDStart.setSelectedItem(String.valueOf(currentDay));
            cbxDEnd.setSelectedItem(String.valueOf(currentDay));

            txtReceipt.setText("");
            //When user cancels the order, enabling the sumbit and lstLodgings 
            btnAddLodging.setEnabled(true);
            lstLodging.setEnabled(true);
            //lstLodging.setText("");
            btnConfirmOrder.setVisible(false);
            btnCancel.setVisible(false);
            lblCancel.setVisible(false);
            lblReceipt.setVisible(false);
            lblOrderDetails.setVisible(true);
            btnRefresh.setEnabled(true);
            imagePanel.removeAll();//Removing the images from the panel
            lblCustOnly.setVisible(false);
            //Enabling the comboboxes
            cbxMStart.setEnabled(true);
            cbxDStart.setEnabled(true);
            cbxYStart.setEnabled(true);
            cbxMEnd.setEnabled(true);
            cbxDEnd.setEnabled(true);
            cbxYEnd.setEnabled(true);
            viewLodges(0);
        });
        //Action Listener to refresh the 
        btnRefresh.addActionListener(e -> {
            getOrdersFromDatabase();
            txtReceipt.setText("");
            cbxLodge.setSelectedIndex(0);
            lblCustOnly.setVisible(false);
            //Setting the JList with a new DefaultListModel that is empty to clear the List of lodgings
            DefaultListModel<String> emptyModel = new DefaultListModel<>();
            lstLodging.setModel(emptyModel);
            viewLodges(0);
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            dateComboBoxes();
        });
        //Calling Methods to load lodges into the list of lodgings
        viewLodges(0);
        addDetails(0, 0);
        getOrdersFromDatabase();//Calling this method to add the orders to the CurrentOrders arraylist
    }

    //Method to get the reports for all customers with the selected dates 
    private void getReports(String startDates, String endDates) {
        //If else to check if the user is logged in as a customer or not
        if (customerUserName == null) {
            lblCustOnly.setVisible(true);
            lblErrorDate.setVisible(false);
        } else {
            //Using a Thread lambda to create a thread to execute the the query to get customers orders reports 
            Thread dbThread = new Thread(() -> {
                if (tryConnection()) {
                    //Query to get customers names and their total amount spent
                    String query = "SELECT c.name AS CustomerName, o.OrderID, o.lodgeName, o.startDate, o.endDate, o.numNights, SUM(o.totalPrice) AS totalSpent "
                            + "FROM Orders o JOIN Customers c ON o.customerID = c.customerID "
                            + "WHERE c.userName = ? AND o.startDate BETWEEN ? AND ? "
                            + "GROUP BY c.name, o.OrderID, o.lodgeName, o.startDate, o.endDate, o.numNights";

                    //StringBuilder for the HTML
                    StringBuilder html = new StringBuilder();
                    try {
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1, customerUserName);
                        ps.setString(2, startDates);
                        ps.setString(3, endDates);

                        //Creating the HTML report
                        html.append("""
                                <!DOCTYPE html>
                                 <html><head><style>
                                 html {background-color: #90AEAD;}
                                h2 {text-align: Center;}
                                table { width: 100%; border-collapse: collapse; }
                                 table, th, td { border: 1px solid black; }
                                 th, td { background-color: #FBE9D0; padding: 10px; text-align: left; }
                                 th { background-color: #244855; color: white; }
                                 </style></head><body>
                                <h2>Customer Order History</h2>
                                 <table><tr><th>Customer Name</th><th>Order ID</th><th>Lodge Name</th><th>Start Date</th><th>End Date</th><th>Number of Nights</th><th>Total Price</th></tr>""");

                        ResultSet rs = ps.executeQuery();
                        //using a while loop to add all of the customers order information to the table within the dates selected
                        while (rs.next()) {
                            String name = rs.getString("CustomerName");
                            int orderID = rs.getInt("OrderID");
                            String lodgeName = rs.getString("lodgeName");
                            String startDate = rs.getString("startDate");
                            String endDate = rs.getString("endDate");
                            int numNights = rs.getInt("numNights");
                            double totalSpent = rs.getDouble("totalSpent");

                            //Adding customers order details to the html file
                            html.append("<tr><td>").append(name)
                                    .append("</td><td>").append(orderID)
                                    .append("</td><td>").append(lodgeName)
                                    .append("</td><td>").append(startDate)
                                    .append("</td><td>").append(endDate)
                                    .append("</td><td>").append(numNights)
                                    .append("</td><td>").append(String.format("$%.2f", totalSpent))
                                    .append("</td></tr>");
                        }

                        //The closing HTML Tags
                        html.append("</table></body></html>");
                        //Storing the path to the reports folder in a string
                        String reportsFolderPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Reports";
                        //Creating the file to store the report
                        File reportsFolder = new File(reportsFolderPath);
                        //If the reports folder does not exist, it will create one using the .mkdir() method
                        if (!reportsFolder.exists()) {
                            reportsFolder.mkdir(); //Creating a Reports folder if it doesn't exist
                        }

                        //Saving the HTML to the file
                        File reportFile = new File(reportsFolderPath + "/CustomerReport.html");
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
                            writer.write(html.toString());
                        }

                        //Opening the saved HTML file in the browser
                        Desktop.getDesktop().browse(reportFile.toURI());

                    } catch (SQLException ex) {
                        System.out.println("SQL error when getting customer name and total spent for the customer reports." + ex.getMessage());
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        System.out.println("Error reading data from the database. " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });
            dbThread.start();
        }
    }

    //Method to load the lodge images into the imagePanel
    private void loadLodgeImages(String lodgeName, String address) {
        selectedLodge = lodgeName;
        //Removing all of the images from the image panel before adding them
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        //Initializing a lodge variable
        Lodging lodge = null;
        //Searching the lodgingHotel list
        for (int i = 0; i < EmployeeView.lodgingHotel.size(); i++) {
            Hotel hotel = EmployeeView.lodgingHotel.get(i);
            //if the lodgeName passed is found, it sets the lodge variable to that hotel name
            if (hotel.name.equals(lodgeName) && hotel.getAddress().equals(address)) {
                lodge = hotel;
                break;
            }
        }
        //Searching the lodgingHouse list if nothing is found in the lodgingHotel list
        if (lodge == null) {
            for (int i = 0; i < EmployeeView.lodgingHouse.size(); i++) {
                House house = EmployeeView.lodgingHouse.get(i);
                if (house.name.equals(lodgeName) && house.getAddress().equals(address)) {
                    lodge = house;
                    break;
                }
            }
        }
        //Checking to make sure that lodge in not null
        if (lodge == null) {
            return;
        }

        //If the lodgeArrayList is empty
        if (lodge.getImages().isEmpty()) {
            loadInProgress = true;
            int lodgeID = lodge.lodgeID;//getting the lodgeID from the lodge object
            final Lodging finalLodge = lodge;
            //A thread to load the images from the database and add them to the panel as they are retrieved
            Thread dbThread = new Thread(() -> {
                ArrayList<ImageIcon> images = getImagesFromDatabase(lodgeID);//Calling the getImageFromDatabase to add each image to the lodge, passing LodgeID
                //for loop to add each image to the lodge object
                for (int i = 0; i < images.size(); i++) {
                    ImageIcon icon = images.get(i);
                    finalLodge.addImage(icon);
                }

                //For loop to get all images from the database
                for (int i = 0; i < images.size(); i++) {
                    ImageIcon icon = images.get(i);
                    Image img = icon.getImage();
                    //Resizing the image to a thumbnail
                    Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    //Creating a new image icon from the scaled image 
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    //A JLabel to hold the thumbnail image
                    JLabel thumbnailImg = new JLabel(scaledIcon);
                    //Mouse listener for when an image is clicked
                    thumbnailImg.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            openWindow(lodgeName, icon);//Calling the openWindow method to display that image 
                        }
                    });
                    //Checking to see if the images being added is the selected lodge
                    if (lodgeName.equals(selectedLodge)) {
                        //Add the JLabel to the image panel
                        imagePanel.add(thumbnailImg);
                        //Revalidate and repaint to update the panel 
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                }
                //Adjust the height of the JScrollPane if needed using the Timer class
                new Timer(1000, e -> {
                    int jspHeight = 120;
                    if (jspImages.getHorizontalScrollBar().isVisible()) {//Checking to see if the scrollbar is visible, if it is adjust the size of the image panel
                        jspHeight += jspImages.getHorizontalScrollBar().getHeight();
                    }
                    jspImages.setBounds(200, 730, 940, jspHeight);
                    jspImages.revalidate();
                    jspImages.repaint();
                    ((Timer) e.getSource()).stop();
                }).start();
                loadInProgress = false;
                lstLodging.setEnabled(true);
            });
            dbThread.start();//Starting the thread to get the images 
        } else {
            addImagesToPanel(lodge.getImages(), lodgeName);
        }
    }

    //Method to add images to the panel if they have already been retrieved from the database
    private void addImagesToPanel(ArrayList<ImageIcon> images, String lodgeName) {
        System.out.println("Loading Images From the ArrayList");
        //Creating a thread to add images from the arraylist to the panel
        Thread imgThread = new Thread(() -> {
            for (int i = 0; i < images.size(); i++) {
                ImageIcon icon = images.get(i);
                Image img = icon.getImage();
                Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                //Creating a new image icon from the scaled image 
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                //A JLabel to hold the thumbnail image
                JLabel thumbnailImg = new JLabel(scaledIcon);
                //Mouse listener for when an image is clicked
                thumbnailImg.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        openWindow(lodgeName, icon);//Calling the openWindow method to display that image 
                    }
                });

                if (lodgeName.equals(selectedLodge)) {
                    //Add the JLabel to the image panel
                    imagePanel.add(thumbnailImg);
                    //Revalidate and repaint to update the panel 
                    imagePanel.revalidate();
                    imagePanel.repaint();
                }
            }
            //Adjust the height of the JScrollPane if needed using the Timer class
            new Timer(1000, e -> {
                int jspHeight = 120;
                if (jspImages.getHorizontalScrollBar().isVisible()) {//Checking to see if the scrollbar is visible, if it is adjust the size of the image panel
                    jspHeight += jspImages.getHorizontalScrollBar().getHeight();
                }
                jspImages.setBounds(200, 730, 940, jspHeight);
                jspImages.revalidate();
                jspImages.repaint();
                ((Timer) e.getSource()).stop();
            }).start();
            loadInProgress = false;
            lstLodging.setEnabled(true);
        });
        imgThread.start();//Starting the thread
    }

    //Method to get images from the database using lodgeID
    private ArrayList<ImageIcon> getImagesFromDatabase(int LodgeID) {
        System.out.println("Loading Images From the Database!");
        //Creating an arrayList to store images
        ArrayList<ImageIcon> images = new ArrayList<>();
        if (tryConnection()) {
            try {
                String query = "SELECT img FROM LodgeImages WHERE lodgeID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, LodgeID);
                ResultSet rs = ps.executeQuery();
                Blob imgBlob;
                //While loop to get all images for the lodge selected
                while (rs.next()) {
                    imgBlob = rs.getBlob("img");
                    if (imgBlob != null) {
                        //Converting the image to a byte array
                        byte[] b = imgBlob.getBytes(1, (int) imgBlob.length());
                        //Using the byte array to create an image
                        ImageIcon img = new ImageIcon(b);
                        images.add(img);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQL error when attempting to get images from the database. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return images;//Returning the arrayList with the images
    }

    //Method that opens the carousel window for images
    private void openWindow(String lodgeName, ImageIcon selectedImage) {
        //Creating a new JFrame for the carousel window
        JFrame carouselFrame = new JFrame();
        carouselFrame.setSize(1280, 720);
        carouselFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        carouselFrame.setLayout(new BorderLayout());
        //JPanel to display the images
        JPanel carouselPanel = new JPanel();
        JLabel imageLabel = new JLabel();
        carouselPanel.add(imageLabel);
        carouselFrame.add(carouselPanel, BorderLayout.CENTER);//Adding the imagePanel to the JFrame with BorderLayout
        //Buttons
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        //Initialize a Lodging object
        Lodging lodge = null;
        //Searching the lodgingHotel list
        for (Hotel hotel : EmployeeView.lodgingHotel) {
            //if the lodgeName passed is found, it sets the lodge variable to that hotel name
            if (hotel.name.equals(lodgeName)) {
                lodge = hotel;
                break;
            }
        }
        //Searching the lodgingHouse list if nothing is found in the lodgingHotel list
        if (lodge == null) {
            for (House house : EmployeeView.lodgingHouse) {
                if (house.name.equals(lodgeName)) {
                    lodge = house;
                    break;
                }
            }
        }
        //Checking to make sure that the lodge object is not empty
        if (lodge == null) {
            return;
        }
        //Adding all of the images to the arrayList from the database
        ArrayList<ImageIcon> images = lodge.getImages();
        int[] currentIndex = {images.indexOf(selectedImage)};

        //Action listeners for the next button
        prevButton.addActionListener(e -> {
            if (!images.isEmpty()) {
                // Move index backwards to go to the previous image in the arrayList
                currentIndex[0] = (currentIndex[0] - 1 + images.size()) % images.size();
                ImageIcon previousImage = images.get(currentIndex[0]);
                displayImageInCarousel(imageLabel, previousImage);
            }
        });
        //Action listener for the previous button
        nextButton.addActionListener(e -> {
            if (!images.isEmpty()) {
                // Move index forwards to go to the next image in the arrayList
                currentIndex[0] = (currentIndex[0] + 1) % images.size();
                ImageIcon nextImage = images.get(currentIndex[0]);
                displayImageInCarousel(imageLabel, nextImage);
            }
        });

        //Adding the buttons to the carouselFrame with borderLayout
        carouselFrame.add(prevButton, BorderLayout.WEST);
        carouselFrame.add(nextButton, BorderLayout.EAST);

        displayImageInCarousel(imageLabel, selectedImage);//Calling this method to display the carousel window with the selected image
        carouselFrame.setVisible(true);
    }

    //Method to display the images in the carousel
    private void displayImageInCarousel(JLabel imageLabel, ImageIcon icon) {
        //Creating an image object from the image icon
        Image img = icon.getImage();
        int newHeight = 720;
        int newWidth = (int) ((double) img.getWidth(null) * newHeight / img.getHeight(null));
        //Scaling the image to the width and height above
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));//Setting the label to the new image
    }

    //Method to add dates to the comboBoxes
    private void dateComboBoxes() {
        //Creating an object of the GregorainCalendar class
        GregorianCalendar cal = new GregorianCalendar();
        //Getting the current date to set to the combo boxes
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        // Populating the months comboBoxes
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (String month : months) {
            cbxMStart.addItem(month);
            cbxMEnd.addItem(month);
            cbxOMStart.addItem(month);
            cbxOMEnd.addItem(month);
        }
        // Populating the years 
        int endYear = 2027;
        for (int yr = currentYear; yr <= endYear; yr++) {
            cbxYStart.addItem(String.valueOf(yr));
            cbxYEnd.addItem(String.valueOf(yr));
            cbxOYStart.addItem(String.valueOf(yr));
            cbxOYEnd.addItem(String.valueOf(yr));
        }
        //Setting the current month as the selected month in the combo box
        cbxMStart.setSelectedIndex(currentMonth);
        cbxMEnd.setSelectedIndex(currentMonth);
        cbxOMStart.setSelectedIndex(currentMonth);
        cbxOMEnd.setSelectedIndex(currentMonth);

        //Setting the current year as the selected year in the combo box
        cbxYStart.setSelectedItem(String.valueOf(currentYear));
        cbxYEnd.setSelectedItem(String.valueOf(currentYear));
        cbxOYStart.setSelectedItem(String.valueOf(currentYear));
        cbxOYEnd.setSelectedItem(String.valueOf(currentYear));

        //Callind update method to populate the days based on current month, and year
        updateStartCbx(cal.get(Calendar.MONTH), currentYear);
        updateEndCbx(cal.get(Calendar.MONTH), currentYear);

        //Setting the current day as the selected day in the combo box
        cbxDStart.setSelectedItem(String.valueOf(currentDay));
        cbxDEnd.setSelectedItem(String.valueOf(currentDay));
        cbxODStart.setSelectedItem(String.valueOf(currentDay));
        cbxODEnd.setSelectedItem(String.valueOf(currentDay));
    }

    //Method to add days to the start day comboxBoxes
    private void updateStartCbx(int month, int year) {
        //String to hold the day that is selected by the user
        String selectedSDate = (String) cbxDStart.getSelectedItem();
        String selectedOSDate = (String) cbxODStart.getSelectedItem();
        //Clearing the comboBoxes of the items that were in it
        cbxDStart.removeAllItems();
        cbxODStart.removeAllItems();

        //Getting the days for the selected month and year
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Populating the days with the correct days for the month and year selected
        for (int day = 1; day <= dayOfMonth; day++) {
            cbxDStart.addItem(String.valueOf(day));
            cbxODStart.addItem(String.valueOf(day));
        }
        //if else to check if the selected day is valid for the month/year selected
        if (selectedSDate != null) {
            int selectedSDay = Integer.parseInt(selectedSDate);
            if (selectedSDay <= dayOfMonth) {
                cbxDStart.setSelectedItem(selectedSDate);
            } else {
                cbxDStart.setSelectedIndex(0);
            }
        }

        //if else to check if the selected day is valid for the month/year selected
        if (selectedOSDate != null) {
            int selectedOSDay = Integer.parseInt(selectedOSDate);
            if (selectedOSDay <= dayOfMonth) {
                cbxODStart.setSelectedItem(selectedOSDate);
            } else {
                cbxODStart.setSelectedIndex(0);//If the day selected is invalid, reset it to 0
            }
        }

    }

    //Method to add days to the end day comboxBoxes
    private void updateEndCbx(int month, int year) {
        //String to hold the day that is selected by the user
        String selectedEDate = (String) cbxDEnd.getSelectedItem();
        String selectedOEDate = (String) cbxODEnd.getSelectedItem();
        //Clearing the comboBoxes of the items that were in it
        cbxDEnd.removeAllItems();
        cbxODEnd.removeAllItems();

        //Getting the days for the selected month and year
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Populating the days with the correct days for the month and year selected
        for (int day = 1; day <= dayOfMonth; day++) {
            cbxDEnd.addItem(String.valueOf(day));
            cbxODEnd.addItem(String.valueOf(day));
        }
        //if else to check if the selected day is valid for the month/year selected
        if (selectedEDate != null) {
            int selectedEDay = Integer.parseInt(selectedEDate);
            if (selectedEDay <= dayOfMonth) {
                cbxDEnd.setSelectedItem(selectedEDate);
            } else {
                cbxDEnd.setSelectedIndex(0);
            }
        }

        //if else to check if the selected day is valid for the month/year selected
        if (selectedOEDate != null) {
            int selectedOEDay = Integer.parseInt(selectedOEDate);
            if (selectedOEDay <= dayOfMonth) {
                cbxODEnd.setSelectedItem(selectedOEDate);
            } else {
                cbxODEnd.setSelectedIndex(0);
            }
        }
    }

//    //Method to enter customer view
//    void enter() {
//        System.out.println("Entering Customer view");
//    }
//
//    //Method to update the customer view
//    void update() {
//        //Displaying menu options
//        System.out.println("1. View Lodging Options");
//        System.out.println("2. View order");
//        System.out.println("3. Return to main menu");
//
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();//User input to select menu option
//        switch (input) {
//            case "1":
//                viewLodges();
//                break;
//            case "2":
//                viewOrder();
//                break;
//            case "3":
//                current = loginState;
//                break;
//            default:
//                System.out.println("Error, Please try again!");
//                enter();
//                update();
//        }
//    }
    //Method to add details of the selected loging to the list
    private void addDetails(int lodgingType, int index) {
        //Creating a DefaultListModel to hold and manage the data that is displayed by the JList
        lodgingDetails = new DefaultListModel();
        //If-else and for loop to list all of the different lodging options with the price
        if (lodgingType == 0) {
            if (index >= 0 && index < EmployeeView.lodgingHotel.size()) {
                Hotel hotel = EmployeeView.lodgingHotel.get(index);
                //Adding the hotel details to the DefaultListModel
                lodgingDetails.addElement("Address: " + hotel.getAddress());
                lodgingDetails.addElement("Phone Number: " + hotel.getPhoneNumber());
                lodgingDetails.addElement("Max Occupants: " + hotel.getMaxOccupants());
                lodgingDetails.addElement("Base Price Per Night: " + String.format("$%.2f", hotel.getBasePricePerNight()));
                lodgingDetails.addElement("Room Service Price: " + String.format("$%.2f", hotel.getRoomServicePrice()));
                lodgingDetails.addElement("Parking fee: " + String.format("$%.2f", hotel.getParkingFee()));
                lodgingDetails.addElement("Vacancies: " + hotel.getVacancies());
                lodgingDetails.addElement("Has free Breakfast: " + hotel.isHasFreeBreakfast());
                lodgingDetails.addElement("Has Valet Parking: " + hotel.isValetParking());
                lodgingDetails.addElement("Has a Pool: " + hotel.isHasPool());
                lodgingDetails.addElement("Pets are allowed: " + hotel.isPetsAllowed());
                lodgingDetails.addElement("");
            }
            lstDetails.setModel(lodgingDetails);//Adding the DefaultListModel with hotel details to the JList
        } else if (lodgingType == 1) {
            if (index >= 0 && index < EmployeeView.lodgingHouse.size()) {
                House house = EmployeeView.lodgingHouse.get(index);
                //Adding the house details to the DefaultListModel
                lodgingDetails.addElement("Address: " + house.getAddress());
                lodgingDetails.addElement("Phone Number: " + house.getPhoneNumber());
                lodgingDetails.addElement("Max Occupants: " + house.getMaxOccupants());
                lodgingDetails.addElement("Base Price Per Night: " + String.format("$%.2f", house.getBasePricePerNight()));
                lodgingDetails.addElement("Cleaing Fee: " + String.format("$%.2f", house.getCleaningFee()));
                lodgingDetails.addElement("Number of Bedrooms: " + house.getNumberOfBedrooms());
                lodgingDetails.addElement("Has a Garage: " + house.isHasGarage());
                lodgingDetails.addElement("Has a Backyard: " + house.isHasBackyard());
                lodgingDetails.addElement("");
            }

            lstDetails.setModel(lodgingDetails);//Adding the DefaultListModel with house details to the JList
        }
    }

    //Method for viewing lodging options 
    public void viewLodges(int type) {
        //Creating a DefaultListModel to hold and manage the data that is displayed by the JList
        lodgingModel = new DefaultListModel();

        //If-else and for loop to list all of the different lodging options with the price
        if (type == 0) {
            for (int i = 0; i < EmployeeView.lodgingHotel.size(); i++) {
                //Adding the hotel options to the DefaultListModel
                lodgingModel.addElement(EmployeeView.lodgingHotel.get(i).getName());
            }
            lstLodging.setModel(lodgingModel);//Adding the DefaultListModel with hotel options to the JList
        } else if (type == 1) {
            for (int i = 0; i < EmployeeView.lodgingHouse.size(); i++) {
                //Adding the house options to the DefaultListModel
                lodgingModel.addElement(EmployeeView.lodgingHouse.get(i).getName());
            }
            lstLodging.setModel(lodgingModel);//Adding the DefaultListModel with house options to the JList
            lstLodging.revalidate();
            lstLodging.repaint();
        }
    }

    //Method to process the order before adding to database
    private void orderDetails(int lodgeID, int type) {
        //Checking to see if the user is a customer
        if (customerUserName == null) {
            lblCustOnly.setVisible(true);
        }
        //Getting the start date and end date from the comboBoxes
        int startMonth = cbxMStart.getSelectedIndex() + 1;
        int startDay = Integer.parseInt((String) cbxDStart.getSelectedItem());
        int startYear = Integer.parseInt((String) cbxYStart.getSelectedItem());

        int endMonth = cbxMEnd.getSelectedIndex() + 1;
        int endDay = Integer.parseInt((String) cbxDEnd.getSelectedItem());
        int endYear = Integer.parseInt((String) cbxYEnd.getSelectedItem());

        // Create LocalDate objects for start and end dates
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

        // Calculate the number of days between the dates
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        //If else to check that the date range is valid
        if (diff <= 0) {
            lblErrorDate.setVisible(true);
            btnAddLodging.setEnabled(true);
            lstLodging.setEnabled(true);
            btnConfirmOrder.setVisible(false);
            btnCancel.setVisible(false);
            return;
        }

        //Converting long to int for the number of nights
        int numNights = (int) diff;
        //Creating string variable to hold the correct date
        String startDates = startYear + "-" + startMonth + "-" + startDay;
        String endDates = endYear + "-" + endMonth + "-" + endDay;

        String formatSDate = startMonth + "/" + startDay + "/" + startYear;
        String formatEDate = endMonth + "/" + endDay + "/" + endYear;
        //Processing the order based on user selection 
        if (type == 0) {
            if (lodgeID >= 0 && lodgeID <= EmployeeView.lodgingHotel.size()) {
                Hotel selectedHotel = EmployeeView.lodgingHotel.get(lodgeID);
                double price = selectedHotel.getBasePricePerNight();
                order(selectedHotel.getName(), "Hotel", startDates, endDates, formatSDate, formatEDate, numNights, price);
            }
        } else if (type == 1) {
            if (lodgeID >= 0 && lodgeID <= EmployeeView.lodgingHouse.size()) {
                House selectedHouses = EmployeeView.lodgingHouse.get(lodgeID);
                double price = selectedHouses.getBasePricePerNight();
                order(selectedHouses.getName(), "House", startDates, endDates, formatSDate, formatEDate, numNights, price);
            }
        }
    }

    //Method to place the order
    private void order(String lodgeName, String type, String startDate, String endDate, String formatSDate, String formatEDate, int numNights, double price) {
        double totalPrice = price * numNights;
        int orderID = currentOrder.size() + 1;
        Order newOrder = new Order(lodgeName, orderID, totalPrice);
        addOrdersToDatabase(lodgeName, type, startDate, endDate, numNights, totalPrice);//Adding orders to the database
        currentOrder.add(newOrder);
        //Displaying Order to the reciept
        for (Order item : currentOrder) {
            txtReceipt.setText(item.toString() + "\nDates: " + formatSDate + "-" + formatEDate + "\nTotal Days of Stay: " + numNights);
        }

    }

    //Method to get the orders from the database and store them in the arraylist
    private void getOrdersFromDatabase() {
        currentOrder.clear();
        //A thread to get orders from the database
        Thread dbThread = new Thread(() -> {
            if (tryConnection()) {
                try {
                    String query = "SELECT lodgeName, orderID, totalPrice FROM Orders";
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String lodgeName = rs.getString("lodgeName");
                        int orderID = rs.getInt("OrderID");
                        double totalPrice = rs.getDouble("totalPrice");
                        //Creating a new order object for each row 
                        Order newOrder = new Order(lodgeName, orderID, totalPrice);
                        //Adding the new order item to the arrayList
                        currentOrder.add(newOrder);
                    }
                } catch (SQLException ex) {
                    System.out.println("SQL error when getting orders from the database." + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });
        dbThread.start();
    }

    //Method to add the orders to the database
    private void addOrdersToDatabase(String lodgeName, String lodgeType, String startDate, String endDate, int numNights, double totalPrice) {
        if (customerUserName != null) {
            //A thread to add orders to the database
            Thread dbThread = new Thread(() -> {
                if (tryConnection()) {
                    try {
                        //Variable to store customerID and lodgeID
                        int customerID;
                        int lodgeID;
                        //A query to get the customer id based on the customer who logs in
                        String queryCustomerID = "SELECT customerID FROM Customers WHERE userName = ?";
                        PreparedStatement psCustomerID = con.prepareStatement(queryCustomerID);
                        psCustomerID.setString(1, customerUserName);
                        ResultSet rs = psCustomerID.executeQuery();

                        if (rs.next()) {
                            customerID = rs.getInt("customerID");
                        } else {
                            System.out.println("Customer not found.");
                            return;
                        }
                        //A query to get lodgeID based on the lodgename
                        String queryLodgeID = "SELECT lodgeID FROM Lodges WHERE name = ?";
                        PreparedStatement psLodgeID = con.prepareStatement(queryLodgeID);
                        psLodgeID.setString(1, lodgeName);
                        ResultSet rsID = psLodgeID.executeQuery();

                        if (rsID.next()) {
                            lodgeID = rsID.getInt("lodgeID");
                        } else {
                            System.out.println("LodgeID not found");
                            return;
                        }

                        //A query to add orders to the database 
                        String query = "INSERT INTO Orders (customerID, lodgeID, lodgeName, lodgeType, startDate, endDate, numNights, totalPrice) VALUES (?,?,?,?,?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setInt(1, customerID);
                        ps.setInt(2, lodgeID);
                        ps.setString(3, lodgeName);
                        ps.setString(4, lodgeType);
                        ps.setString(5, startDate);
                        ps.setString(6, endDate);
                        ps.setInt(7, numNights);
                        ps.setDouble(8, totalPrice);
                        ps.executeUpdate();

                        //A query to update the total spending to the Customers table 
                        String queryUpdate = "UPDATE Customers SET totalSpending = totalSpending + ? WHERE customerID = ?";
                        PreparedStatement psUpdate = con.prepareStatement(queryUpdate);
                        psUpdate.setDouble(1, totalPrice);
                        psUpdate.setInt(2, customerID);
                        psUpdate.executeUpdate();

                    } catch (SQLException ex) {
                        System.out.println("SQL error when adding orders to the database" + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Error adding orders to the database");
                }
            });
            dbThread.start();
        } else {
            //System.out.println("No customer Logged in!");
            lblCustOnly.setVisible(true);
        }
    }

    //Method to display the order Details before the user confirms the order
    private void viewOrderDetails(int lodgeID, int type) {
        //Variables to store price of lodge and name
        double price = 0;
        String lodgeName = "";
        int orderID = currentOrder.size() + 1;
        btnAddLodging.setEnabled(false);//Disabling the submit button
        lstLodging.setEnabled(false);//Disabling the lodgings in the Jlist
        btnConfirmOrder.setVisible(true);
        btnCancel.setVisible(true);
        //Getting the start date and end date from the comboBoxes
        int startMonth = cbxMStart.getSelectedIndex() + 1;
        int startDay = Integer.parseInt((String) cbxDStart.getSelectedItem());
        int startYear = Integer.parseInt((String) cbxYStart.getSelectedItem());

        int endMonth = cbxMEnd.getSelectedIndex() + 1;
        int endDay = Integer.parseInt((String) cbxDEnd.getSelectedItem());
        int endYear = Integer.parseInt((String) cbxYEnd.getSelectedItem());

        // Create LocalDate objects for start and end dates
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

        // Calculate the number of days between the dates
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        //Converting long to int for the number of nights
        int numNights = (int) diff;
        //If else to check that the date range is valid
        if (diff <= 0) {
            lblErrorDate.setVisible(true);
            btnAddLodging.setEnabled(true);
            lstLodging.setEnabled(true);
            btnConfirmOrder.setVisible(false);
            btnCancel.setVisible(false);
            btnRefresh.setEnabled(true);
            //Enabling the comboboxes 
            cbxMStart.setEnabled(true);
            cbxDStart.setEnabled(true);
            cbxYStart.setEnabled(true);
            cbxMEnd.setEnabled(true);
            cbxDEnd.setEnabled(true);
            cbxYEnd.setEnabled(true);
            return;
        }

        //Creating string variable to hold the correct date
        String startDates = startMonth + "/" + startDay + "/" + startYear;
        String endDates = endMonth + "/" + endDay + "/" + endYear;

        //Processing the order based on user selection 
        if (type == 0) {
            if (lodgeID >= 0 && lodgeID <= EmployeeView.lodgingHotel.size()) {
                Hotel selectedHotel = EmployeeView.lodgingHotel.get(lodgeID);
                price = selectedHotel.getBasePricePerNight() * numNights;
                lodgeName = selectedHotel.getName();
            } else {
                return;
            }
        } else if (type == 1) {
            if (lodgeID >= 0 && lodgeID <= EmployeeView.lodgingHouse.size()) {
                House selectedHouse = EmployeeView.lodgingHouse.get(lodgeID);
                price = selectedHouse.getBasePricePerNight() * numNights;
                lodgeName = selectedHouse.getName();
            } else {
                return;
            }
        }

        txtReceipt.setText("Lodge: " + lodgeName + "\nTotal Price: $" + String.format("%.2f", price) + "\nDates: " + startDates + "-" + endDates + "\nTotal Days of Stay: " + numNights);

    }

    @Override
    //This abstract method does not need to be used in this class
    public void save() {

    }

    @Override
    //This abstract method does not need to be used in this class
    public void load() {

    }

}
