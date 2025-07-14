package kylejohnsonM6;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.*;//Import for swing application
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class EmployeeView extends State {
    private boolean fillArrays = false;//Boolean for if the arrayLists have been filled
    private boolean loadInProgress = false;//Boolean for if the lodgeImages are being loaded
    private boolean cancelLoad = false;//Boolean to cancel loading
    private boolean newImagesAdded = false;//Boolean to track if images were added from the database
    private String selectedLodge = "";//String to store the selected lodge for checking before displaying 
    private int currentThread = 0;//Variable for keeping track of the threads

    //ArrayLists 
    static ArrayList<Hotel> lodgingHotel = new ArrayList();
    static ArrayList<House> lodgingHouse = new ArrayList();
    //boolean isManager;
    DefaultListModel lodgingModelHotel;
    DefaultListModel lodgingModelHouse;
    //Components for employeeView Panel
    static JPanel imagePanel;
    JList lstHotels;
    JList lstHouses;
    JLabel htSuccess;
    JLabel hsSuccess;
    JLabel htAdded;
    JLabel hsAdded;
    JLabel htAddErr;
    JLabel hsAddErr;
    JLabel lblHtNumEx;
    JLabel lblHsNumEx;
    JLabel lblErrorDate;
    TextField txtHTName;
    TextField txtHTAddress;
    TextField txtHTPhone;
    TextField txtHTOccu;
    TextField txtHTRmPrice;
    TextField txtHTRmSvPrice;
    TextField txtHTParking;
    TextField txtHTVacancies;
    TextField txtHSName;
    TextField txtHSAddress;
    TextField txtHSPhone;
    TextField txtHSOccu;
    TextField txtHSPrice;
    TextField txtHSCleanFee;
    TextField txtHSBedrooms;
    JRadioButton rBtnBreakT;
    JRadioButton rBtnBreakF;
    JRadioButton rBtnValetT;
    JRadioButton rBtnValetF;
    JRadioButton rBtnPoolT;
    JRadioButton rBtnPoolF;
    JRadioButton rBtnPetsT;
    JRadioButton rBtnPetsF;
    JRadioButton rBtnGarageT;
    JRadioButton rBtnGarageF;
    JRadioButton rBtnYardT;
    JRadioButton rBtnYardF;
    JScrollPane jspImages;
    JTextArea txtHSDescr;
    JComboBox cbxM1;
    JComboBox cbxD1;
    JComboBox cbxY1;
    JComboBox cbxM2;
    JComboBox cbxD2;
    JComboBox cbxY2;
    JTextArea txtHTDescr;
    Button btnAddHotelImg;
    Button btnAddHouseImg;
    Button btnUpdateHotel;
    Button btnUpdateHouse;
    Button btnCancelHouse;
    Button btnCancelHotel;
    Button btnRemoveHT;
    Button btnRemoveHS;
    Button btnAddHotel;
    Button btnAddHouse;
    Button btnRefresh;
    Button btnGetReports;
    ButtonGroup btnGHotelBreakfast;
    ButtonGroup btnGValet;
    ButtonGroup btnGPool;
    ButtonGroup btnGPets;
    ButtonGroup btnGarage;
    ButtonGroup btnYard;

    //For the progress bar when uploading images
    JPanel progPanel;
    JProgressBar progBar;

    public EmployeeView() {
        mainPanel.setBackground(new Color(144, 174, 173));//Changing the background color
        mainPanel.setBorder(BorderFactory.createTitledBorder("Elite Travel Employees"));
        mainPanel.setSize(1200, 1000);
        mainPanel.setLayout(null);
        //Creating a Color object to set the color of the borders
        Color border = new Color(36, 72, 85);

        //Progress bar and Panel to display the progress bar
        progPanel = new JPanel();
        progBar = new JProgressBar();
        progBar.setStringPainted(true);
        progBar.setForeground(new Color(144, 174, 173));
        progBar.setBackground(new Color(251, 233, 208));

        progPanel.setBorder(BorderFactory.createLineBorder(border, 4));
        progPanel.setBounds(500, 900, 200, 20);
        progPanel.setLayout(new BorderLayout());
        progPanel.add(progBar, BorderLayout.CENTER);
        progPanel.setVisible(false);

        //Panel to hold the add Hotels
        JPanel addHotelPanel = new JPanel();
        addHotelPanel.setBackground(new Color(251, 233, 208));//Setting the background for Add Hotel Panel
        addHotelPanel.setBorder(BorderFactory.createLineBorder(border, 4));
        addHotelPanel.setBounds(400, 80, 750, 250);
        addHotelPanel.setLayout(null);

        //Panel to hold the add Houses
        JPanel addHousePanel = new JPanel();
        addHousePanel.setBackground(new Color(251, 233, 208));//Setting the background for Add House Panel
        addHousePanel.setBorder(BorderFactory.createLineBorder(border, 4));
        addHousePanel.setBounds(400, 380, 750, 250);
        addHousePanel.setLayout(null);

        //Buttons to add images for the hotels and houses
        btnAddHotelImg = new Button("Add Image");
        btnAddHouseImg = new Button("Add Image");
        btnAddHotelImg.setEnabled(false);
        btnAddHouseImg.setEnabled(false);

        //Creating components for employeeViewPanel
        lstHotels = new JList();
        lstHotels.setBounds(40, 80, 325, 250);
        lstHotels.setBackground(new Color(251, 233, 208));

        //Panel to hold lodging images
        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(251, 233, 208));
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));//Setting the imagePanel with a flowLayout

        //Scrollpane for the images 
        jspImages = new JScrollPane(imagePanel);
        jspImages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspImages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jspImages.setBorder(BorderFactory.createLineBorder(border, 4));
        jspImages.setBounds(3, 750, 1180, 120);

        JScrollPane jspHotels = new JScrollPane(lstHotels);//Implementing the scrolling for the JList
        jspHotels.setBorder(BorderFactory.createLineBorder(border, 4));
        jspHotels.setBounds(40, 80, 325, 250);

        lstHouses = new JList();
        lstHouses.setBounds(40, 380, 325, 250);
        lstHouses.setBackground(new Color(251, 233, 208));

        JScrollPane jspHouses = new JScrollPane(lstHouses);//Implementing the scrolling for the JList
        jspHouses.setBorder(BorderFactory.createLineBorder(border, 4));
        jspHouses.setBounds(40, 380, 325, 250);

        //Labels for Employees panel
        Label lblHouses = new Label("Houses");
        lblHouses.setBounds(170, 360, 100, 20);

        Label lblHotels = new Label("Hotels");
        lblHotels.setBounds(170, 60, 100, 20);

        lblHtNumEx = new JLabel("Number fields filled out incorrectly!");
        lblHtNumEx.setForeground(Color.RED);
        lblHtNumEx.setVisible(false);
        lblHtNumEx.setBounds(538, 80, 300, 30);

        lblHsNumEx = new JLabel("Number fields filled out incorrectly!");
        lblHsNumEx.setForeground(Color.RED);
        lblHsNumEx.setVisible(false);
        lblHsNumEx.setBounds(538, 40, 300, 30);

        lblErrorDate = new JLabel("Invalid date range. Start date must be before End date.");
        lblErrorDate.setFont(new Font("Arial", Font.BOLD, 14));
        lblErrorDate.setForeground(Color.RED);
        lblErrorDate.setVisible(false);
        lblErrorDate.setBounds(565, 720, 400, 30);

        //-----Components for the reports-------
        btnGetReports = new Button("Get Report");
        btnGetReports.setBounds(998, 678, 100, 30);

        Label lblReports = new Label("Reports");
        lblReports.setBounds(740, 640, 100, 20);

        Label lblStart = new Label("Start Dates:");
        lblStart.setBounds(430, 670, 100, 20);

        Label lblEnd = new Label("End Dates:");
        lblEnd.setBounds(430, 695, 100, 20);

        //Start Dates
        Label lblStartM = new Label("Month:");
        lblStartM.setBounds(540, 670, 100, 20);

        Label lblStartD = new Label("Day:");
        lblStartD.setBounds(700, 670, 100, 20);

        Label lblStartY = new Label("Year:");
        lblStartY.setBounds(850, 670, 100, 20);

        cbxM1 = new JComboBox();
        cbxM1.setBounds(600, 670, 80, 20);

        cbxD1 = new JComboBox();
        cbxD1.setBounds(750, 670, 80, 20);

        cbxY1 = new JComboBox();
        cbxY1.setBounds(900, 670, 80, 20);

        //End Dates
        Label lblEndM = new Label("Month:");
        lblEndM.setBounds(540, 695, 100, 20);

        Label lblEndD = new Label("Day:");
        lblEndD.setBounds(700, 695, 100, 20);

        Label lblEndY = new Label("Year:");
        lblEndY.setBounds(850, 695, 100, 20);

        cbxM2 = new JComboBox();
        cbxM2.setBounds(600, 695, 80, 20);

        cbxD2 = new JComboBox();
        cbxD2.setBounds(750, 695, 80, 20);

        cbxY2 = new JComboBox();
        cbxY2.setBounds(900, 695, 80, 20);

        //Labels to tell the user the lodging was updated successfully, added, or removed
        htSuccess = new JLabel("Successfully Updated!");
        htSuccess.setFont(new Font("Arial", Font.BOLD, 14));
        htSuccess.setForeground(Color.BLACK);
        htSuccess.setBounds(680, 50, 200, 30);
        htSuccess.setVisible(false);

        htAdded = new JLabel("Successfully Added!");
        htAdded.setFont(new Font("Arial", Font.BOLD, 14));
        htAdded.setForeground(Color.BLACK);
        htAdded.setBounds(680, 50, 200, 30);
        htAdded.setVisible(false);

        hsSuccess = new JLabel("Successfully Updated!");
        hsSuccess.setFont(new Font("Arial", Font.BOLD, 14));
        hsSuccess.setForeground(Color.BLACK);
        hsSuccess.setBounds(680, 350, 200, 30);
        hsSuccess.setVisible(false);

        hsAdded = new JLabel("Successfully Added!");
        hsAdded.setFont(new Font("Arial", Font.BOLD, 14));
        hsAdded.setForeground(Color.BLACK);
        hsAdded.setBounds(680, 350, 200, 30);
        hsAdded.setVisible(false);

        //Buttons for the panels
        Button btnLogout = new Button("Logout");
        btnLogout.setBounds(40, 670, 100, 30);
        //Buttons to edit the lodgings
        btnCancelHouse = new Button("Cancel");
        btnCancelHouse.setEnabled(false);

        btnCancelHotel = new Button("Cancel");
        btnCancelHotel.setBounds(210, 335, 75, 20);
        btnCancelHotel.setEnabled(false);

        //Buttons to remove Hotel and House
        btnRemoveHT = new Button("Remove Hotel");
        btnRemoveHT.setVisible(true);

        btnRemoveHS = new Button("Remove House");
        btnRemoveHS.setVisible(true);

        //Buttons for AddHouse and AddHotel panels
        btnAddHotel = new Button("Add Hotel");
        btnAddHotel.setVisible(true);
        btnAddHouse = new Button("Add House");
        btnAddHouse.setVisible(true);

        btnUpdateHouse = new Button("Update");
        btnUpdateHouse.setEnabled(false);
        btnUpdateHotel = new Button("Update");
        btnUpdateHotel.setEnabled(false);

        //Button for the refresh
        btnRefresh = new Button("Refresh");
        btnRefresh.setEnabled(true);
        btnRefresh.setBounds(240, 670, 100, 30);

        //---Components for add Hotel Panel---
        //Text fields for adding Hotel
        txtHTName = new TextField();
        txtHTAddress = new TextField();
        txtHTPhone = new TextField();
        txtHTOccu = new TextField();
        txtHTRmPrice = new TextField();
        txtHTRmSvPrice = new TextField();
        txtHTParking = new TextField();
        txtHTVacancies = new TextField();
        txtHTDescr = new JTextArea();//Text area for description
        txtHTDescr.setLineWrap(true);
        txtHTDescr.setBorder(BorderFactory.createLineBorder(border, 4));
        txtHTDescr.setBackground(new Color(251, 233, 208));

        //JRadioButtons for adding Hotel Free Breakfast
        btnGHotelBreakfast = new ButtonGroup();
        rBtnBreakT = new RadioButton("True");
        rBtnBreakF = new RadioButton("False");
        //Adding the radiobuttons to the ButtonGroupBreakfast
        btnGHotelBreakfast.add(rBtnBreakT);
        btnGHotelBreakfast.add(rBtnBreakF);

        //RadioButtons for adding Hotel Valet Parking
        btnGValet = new ButtonGroup();
        rBtnValetT = new RadioButton("True");
        rBtnValetF = new RadioButton("False");
        //Adding the radiobuttons to the ButtonGroupValet
        btnGValet.add(rBtnValetT);
        btnGValet.add(rBtnValetF);

        //RadioButtons for adding Hotel Pool
        btnGPool = new ButtonGroup();
        rBtnPoolT = new RadioButton("True");
        rBtnPoolF = new RadioButton("False");
        //Adding the radiobuttons to the button group pool
        btnGPool.add(rBtnPoolT);
        btnGPool.add(rBtnPoolF);

        //RadioButtons for adding Hotel Pool
        btnGPets = new ButtonGroup();
        rBtnPetsT = new RadioButton("True");
        rBtnPetsF = new RadioButton("False");
        //Adding the radiobuttons to the button group pool
        btnGPets.add(rBtnPetsT);
        btnGPets.add(rBtnPetsF);

        //Labels for adding a Hotel
        Label lblHTName = new Label("Hotel Name:");
        Label lblHTAddress = new Label("Address:");
        Label lblHTPhone = new Label("Phone Number:");
        Label lblHTOccu = new Label("Max Occupants:");
        Label lblHTPrice = new Label("Base Price per Night:");
        Label lblHTRmSvPrice = new Label("Room Service Price:");
        Label lblHTparking = new Label("Parking Fee:");
        Label lblHTVacancies = new Label("Vacancies:");
        Label lblHTBreakfast = new Label("Free Breakfast:");
        Label lblHTVParking = new Label("Valet Parking:");
        Label lblHTPool = new Label("Has a pool:");
        Label lblHTPets = new Label("Pets allowed:");
        Label lblHTDesc = new Label("Description:");
        htAddErr = new JLabel("Please fill out all fields before selecting Add Hotel!");
        htAddErr.setForeground(Color.RED);
        htAddErr.setVisible(false);
        htAddErr.setFont(new Font("Arial", Font.PLAIN, 15));

        //Adjusting where each component should go in the addHotelPanel
        //Hotel Name
        lblHTName.setBounds(10, 10, 150, 20);
        txtHTName.setBounds(170, 10, 150, 20);
        //Hotel Address
        lblHTAddress.setBounds(10, 40, 150, 20);
        txtHTAddress.setBounds(170, 40, 150, 20);
        //Hotel Phone Number
        lblHTPhone.setBounds(10, 70, 150, 20);
        txtHTPhone.setBounds(170, 70, 150, 20);
        //Hotel Max Occupants
        lblHTOccu.setBounds(10, 100, 150, 20);
        txtHTOccu.setBounds(170, 100, 150, 20);
        //Hotel BasePricePerNight
        lblHTPrice.setBounds(10, 130, 150, 20);
        txtHTRmPrice.setBounds(170, 130, 150, 20);
        //Room service Price
        lblHTRmSvPrice.setBounds(10, 160, 150, 20);
        txtHTRmSvPrice.setBounds(170, 160, 150, 20);
        //Parkingfee
        lblHTparking.setBounds(10, 190, 150, 20);
        txtHTParking.setBounds(170, 190, 150, 20);
        //Vacancies
        lblHTVacancies.setBounds(10, 220, 150, 20);
        txtHTVacancies.setBounds(170, 220, 150, 20);
        //Has Free Breakfast
        lblHTBreakfast.setBounds(340, 10, 150, 20);
        rBtnBreakT.setBounds(460, 10, 70, 20);
        rBtnBreakF.setBounds(540, 10, 70, 20);
        //Valet Parking
        lblHTVParking.setBounds(340, 40, 150, 20);
        rBtnValetT.setBounds(460, 40, 70, 20);
        rBtnValetF.setBounds(540, 40, 70, 20);
        //Has a pool
        lblHTPool.setBounds(340, 70, 150, 20);
        rBtnPoolT.setBounds(460, 70, 70, 20);
        rBtnPoolF.setBounds(540, 70, 70, 20);
        //Pets are allowed
        lblHTPets.setBounds(340, 100, 150, 20);
        rBtnPetsT.setBounds(460, 100, 70, 20);
        rBtnPetsF.setBounds(540, 100, 70, 20);
        //Description
        lblHTDesc.setBounds(340, 130, 150, 20);
        txtHTDescr.setBounds(340, 160, 200, 75);
        //Buttons to add hotel and Image
        btnAddHotelImg.setBounds(620, 10, 120, 30);
        btnAddHotel.setBounds(585, 210, 150, 30);
        btnRemoveHT.setBounds(585, 210, 150, 30);
        btnUpdateHotel.setBounds(585, 170, 150, 30);
        btnCancelHotel.setBounds(585, 130, 150, 30);
        htAddErr.setBounds(630, 50, 350, 30);

        //Adding Components to the addHotelPanel
        addHotelPanel.add(lblHTName);
        addHotelPanel.add(txtHTName);

        addHotelPanel.add(lblHTAddress);
        addHotelPanel.add(txtHTAddress);

        addHotelPanel.add(lblHTPhone);
        addHotelPanel.add(txtHTPhone);

        addHotelPanel.add(lblHTOccu);
        addHotelPanel.add(txtHTOccu);

        addHotelPanel.add(lblHTPrice);
        addHotelPanel.add(txtHTRmPrice);

        addHotelPanel.add(lblHTRmSvPrice);
        addHotelPanel.add(txtHTRmSvPrice);

        addHotelPanel.add(lblHTparking);
        addHotelPanel.add(txtHTParking);

        addHotelPanel.add(lblHTVacancies);
        addHotelPanel.add(txtHTVacancies);

        addHotelPanel.add(lblHTBreakfast);
        addHotelPanel.add(rBtnBreakT);
        addHotelPanel.add(rBtnBreakF);

        addHotelPanel.add(lblHTVParking);
        addHotelPanel.add(rBtnValetT);
        addHotelPanel.add(rBtnValetF);

        addHotelPanel.add(lblHTPool);
        addHotelPanel.add(rBtnPoolT);
        addHotelPanel.add(rBtnPoolF);

        addHotelPanel.add(lblHTPets);
        addHotelPanel.add(rBtnPetsT);
        addHotelPanel.add(rBtnPetsF);

        addHotelPanel.add(lblHTDesc);
        addHotelPanel.add(txtHTDescr);

        addHotelPanel.add(btnAddHotel);
        addHotelPanel.add(btnAddHotelImg);
        addHotelPanel.add(btnUpdateHotel);
        addHotelPanel.add(btnCancelHotel);
        addHotelPanel.add(btnRemoveHT);
        addHotelPanel.add(lblHtNumEx);

        //---Components for addHouse Panel---
        //TextFields for adding a House
        txtHSName = new TextField();
        txtHSAddress = new TextField();
        txtHSPhone = new TextField();
        txtHSOccu = new TextField();
        txtHSPrice = new TextField();
        txtHSCleanFee = new TextField();
        txtHSBedrooms = new TextField();
        txtHSDescr = new JTextArea();
        txtHSDescr.setLineWrap(true);
        txtHSDescr.setBorder(BorderFactory.createLineBorder(border, 4));
        txtHSDescr.setBackground(new Color(251, 233, 208));

        //RadioButtons for adding House Garage
        btnGarage = new ButtonGroup();
        rBtnGarageT = new RadioButton("True");
        rBtnGarageF = new RadioButton("False");
        //Adding the radiobuttons to the ButtonGroupValet
        btnGarage.add(rBtnGarageT);
        btnGarage.add(rBtnGarageF);

        //RadioButtons for adding House Backyard
        btnYard = new ButtonGroup();
        rBtnYardT = new RadioButton("True");
        rBtnYardF = new RadioButton("False");
        //Adding the radiobuttons to the ButtonGroupValet
        btnYard.add(rBtnYardT);
        btnYard.add(rBtnYardF);

        //Labels for adding a House
        Label lblHSName = new Label("House Name:");
        Label lblHSAddress = new Label("Address:");
        Label lblHSPhone = new Label("Phone Number:");
        Label lblHSOccu = new Label("Max Occupants:");
        Label lblHSPrice = new Label("Base Price per Night:");
        Label lblHSCleanFee = new Label("Cleaning Fee:");
        Label lblHSBedrooms = new Label("# Bedrooms:");
        Label lblHSGarage = new Label("Has a Garage:");//True or false - radiobuttons
        Label lblHSBackyard = new Label("Has a Backyard:");//True or false - radiobuttons
        Label lblHSDesc = new Label("Description:");
        hsAddErr = new JLabel("Please fill out all fields before selecting Add House!");
        hsAddErr.setForeground(Color.RED);
        hsAddErr.setVisible(false);
        hsAddErr.setFont(new Font("Arial", Font.PLAIN, 15));
        //Adjusting where each component should go in the addHousePanel
        //House Name
        lblHSName.setBounds(10, 10, 150, 20);
        txtHSName.setBounds(170, 10, 150, 20);
        //House Address
        lblHSAddress.setBounds(10, 40, 150, 20);
        txtHSAddress.setBounds(170, 40, 150, 20);
        //House Phone Number
        lblHSPhone.setBounds(10, 70, 150, 20);
        txtHSPhone.setBounds(170, 70, 150, 20);
        //House Max Occupants
        lblHSOccu.setBounds(10, 100, 150, 20);
        txtHSOccu.setBounds(170, 100, 150, 20);
        //House BasePricePerNight
        lblHSPrice.setBounds(10, 130, 150, 20);
        txtHSPrice.setBounds(170, 130, 150, 20);
        //House cleaning fee
        lblHSCleanFee.setBounds(10, 160, 150, 20);
        txtHSCleanFee.setBounds(170, 160, 150, 20);
        //House Bedrooms
        lblHSBedrooms.setBounds(10, 190, 150, 20);
        txtHSBedrooms.setBounds(170, 190, 150, 20);

        //Setting the connection status label
        lblStatusEmp.setBounds(560, 870, 200, 20);
        //connectLabels[3] = lblStatusEmp;

        //Has a Garage
        lblHSGarage.setBounds(340, 10, 150, 20);
        rBtnGarageT.setBounds(460, 10, 70, 20);
        rBtnGarageF.setBounds(540, 10, 70, 20);
        //Has a backyard
        lblHSBackyard.setBounds(340, 40, 150, 20);
        rBtnYardT.setBounds(460, 40, 70, 20);
        rBtnYardF.setBounds(540, 40, 70, 20);
        //Description
        lblHSDesc.setBounds(340, 70, 150, 20);
        txtHSDescr.setBounds(340, 100, 200, 75);
        //Buttons
        btnAddHouse.setBounds(585, 210, 150, 30);
        btnRemoveHS.setBounds(585, 210, 150, 30);
        btnAddHouseImg.setBounds(620, 10, 120, 30);
        btnUpdateHouse.setBounds(585, 170, 150, 30);
        btnCancelHouse.setBounds(585, 130, 150, 30);
        hsAddErr.setBounds(630, 350, 350, 30);

        // Adding components to addHousePanel
        //House Name
        addHousePanel.add(lblHSName);
        addHousePanel.add(txtHSName);
        //House Address
        addHousePanel.add(lblHSAddress);
        addHousePanel.add(txtHSAddress);
        //House Phone #
        addHousePanel.add(lblHSPhone);
        addHousePanel.add(txtHSPhone);
        //House Max occupants
        addHousePanel.add(lblHSOccu);
        addHousePanel.add(txtHSOccu);
        //House price per night
        addHousePanel.add(lblHSPrice);
        addHousePanel.add(txtHSPrice);
        //House Cleaning Fee
        addHousePanel.add(lblHSCleanFee);
        addHousePanel.add(txtHSCleanFee);
        //House # of Bedrooms
        addHousePanel.add(lblHSBedrooms);
        addHousePanel.add(txtHSBedrooms);
        //House has Garage
        addHousePanel.add(lblHSGarage);
        addHousePanel.add(rBtnGarageT);
        addHousePanel.add(rBtnGarageF);
        //House has Backyard
        addHousePanel.add(lblHSBackyard);
        addHousePanel.add(rBtnYardT);
        addHousePanel.add(rBtnYardF);
        //House Description
        addHousePanel.add(txtHSDescr);
        addHousePanel.add(lblHSDesc);
        //Buttons to add House and images for house
        addHousePanel.add(btnAddHouse);
        addHousePanel.add(btnAddHouseImg);
        addHousePanel.add(btnUpdateHouse);
        addHousePanel.add(btnCancelHouse);
        addHousePanel.add(btnRemoveHS);
        addHousePanel.add(lblHsNumEx);

        mainPanel.add(htSuccess);
        mainPanel.add(hsSuccess);
        mainPanel.add(htAdded);
        mainPanel.add(hsAdded);
        mainPanel.add(lblHouses);
        mainPanel.add(lblHotels);
        mainPanel.add(lblReports);
        mainPanel.add(btnLogout);

        mainPanel.add(jspHotels);
        mainPanel.add(jspHouses);
        mainPanel.add(addHotelPanel);
        mainPanel.add(addHousePanel);
        mainPanel.add(btnRefresh);
        mainPanel.add(jspImages);
        mainPanel.add(cbxM1);
        mainPanel.add(cbxD1);
        mainPanel.add(cbxY1);
        mainPanel.add(lblStartM);
        mainPanel.add(lblStartD);
        mainPanel.add(lblStartY);
        mainPanel.add(cbxM2);
        mainPanel.add(cbxD2);
        mainPanel.add(cbxY2);
        mainPanel.add(lblEndM);
        mainPanel.add(lblEndD);
        mainPanel.add(lblEndY);
        mainPanel.add(lblStart);
        mainPanel.add(lblEnd);
        mainPanel.add(hsAddErr);
        mainPanel.add(htAddErr);
        mainPanel.add(btnGetReports);
        mainPanel.add(lblErrorDate);
        mainPanel.add(progPanel);
        mainPanel.add(lblStatusEmp);
        mainPanel.setVisible(false);

        //Adding the EmployeeView panel to the layoutPanel
        layoutPanel.add(mainPanel, "EmployeeView");
        mainFrame.add(layoutPanel);

        btnLogout.addActionListener(e -> {
            cardLayout.show(layoutPanel, "Login");
            //Clearing the textfields, radiobuttons and text area for Hotel Panel
            txtHTName.setText("");
            txtHTAddress.setText("");
            txtHTPhone.setText("");
            txtHTDescr.setText("");
            txtHTOccu.setText("");
            txtHTRmPrice.setText("");
            txtHTVacancies.setText("");
            txtHTParking.setText("");
            txtHTRmSvPrice.setText("");
            btnGHotelBreakfast.clearSelection();
            btnGValet.clearSelection();
            btnGPool.clearSelection();
            btnGPets.clearSelection();
            //Clearing the textfields, radiobuttons and text area for House Panel
            txtHSName.setText("");
            txtHSAddress.setText("");
            txtHSPhone.setText("");
            txtHSDescr.setText("");
            txtHSOccu.setText("");
            txtHSPrice.setText("");
            txtHSCleanFee.setText("");
            txtHSBedrooms.setText("");
            btnGarage.clearSelection();
            btnYard.clearSelection();
            btnAddHouseImg.setEnabled(true);
            btnAddHotelImg.setEnabled(true);
            htSuccess.setVisible(false);
            hsSuccess.setVisible(false);
            lblErrorDate.setVisible(false);
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            btnRemoveHT.setVisible(false);
            btnRemoveHS.setVisible(false);
            btnAddHotel.setVisible(true);
            btnAddHouse.setVisible(true);
            btnCancelHotel.setEnabled(false);
            btnCancelHouse.setEnabled(false);
            btnUpdateHouse.setEnabled(false);
            btnUpdateHotel.setEnabled(false);
            btnAddHouseImg.setEnabled(false);
            btnAddHotelImg.setEnabled(false);
            lstHotels.clearSelection();
            lstHouses.clearSelection();
            htAddErr.setVisible(false);
            hsAddErr.setVisible(false);
        });

        btnRefresh.addActionListener(e -> {
            if (loadInProgress) {
                System.out.println("Cancelling the previous lodge load...");
                cancelLoad = true;
                loadInProgress = false; // Signal the ongoing process to stop
            }
            //Clearing the textfields, radiobuttons and text area for Hotel Panel
            txtHTName.setText("");
            txtHTAddress.setText("");
            txtHTPhone.setText("");
            txtHTDescr.setText("");
            txtHTOccu.setText("");
            txtHTRmPrice.setText("");
            txtHTVacancies.setText("");
            txtHTParking.setText("");
            txtHTRmSvPrice.setText("");
            btnGHotelBreakfast.clearSelection();
            btnGValet.clearSelection();
            btnGPool.clearSelection();
            btnGPets.clearSelection();
            //Clearing the textfields, radiobuttons and text area for House Panel
            txtHSName.setText("");
            txtHSAddress.setText("");
            txtHSPhone.setText("");
            txtHSDescr.setText("");
            txtHSOccu.setText("");
            txtHSPrice.setText("");
            txtHSCleanFee.setText("");
            txtHSBedrooms.setText("");
            btnGarage.clearSelection();
            btnYard.clearSelection();
            btnAddHouseImg.setEnabled(false);
            btnAddHotelImg.setEnabled(false);
            htSuccess.setVisible(false);
            hsSuccess.setVisible(false);
            lblErrorDate.setVisible(false);
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            btnRemoveHT.setVisible(false);
            btnRemoveHS.setVisible(false);
            btnAddHotel.setVisible(true);
            btnAddHouse.setVisible(true);
            btnCancelHotel.setEnabled(false);
            btnCancelHouse.setEnabled(false);
            btnUpdateHouse.setEnabled(false);
            btnUpdateHotel.setEnabled(false);
            lstHotels.clearSelection();
            lstHouses.clearSelection();
            btnAddHouseImg.setEnabled(false);
            btnAddHotelImg.setEnabled(false);
            htSuccess.setVisible(false);
            hsSuccess.setVisible(false);
            lblErrorDate.setVisible(false);
            htAddErr.setVisible(false);
            hsAddErr.setVisible(false);
        });
        //Using a list listener to check if the user selects a lodging from Hotel or House, enabling edit and remove buttons
        lstHotels.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //Clearing the image panel 
                imagePanel.removeAll();
                imagePanel.revalidate();
                imagePanel.repaint();
                int htIndex = lstHotels.getSelectedIndex();
                String selectedLodge = (String) lstHotels.getSelectedValue();
                String type = "Hotel";
                if (htIndex != -1) {//Checking to see if a hotel is selected
                    Hotel selectedHotel = lodgingHotel.get(htIndex);
                    String selectedAddress = selectedHotel.getAddress();//Getting the address of the selected Hotel
                    btnAddHotel.setVisible(false);
                    btnRemoveHT.setVisible(true);
                    btnRemoveHT.setEnabled(true);
                    //btnRemoveHT.setEnabled(false);
                    htSuccess.setVisible(false);
                    htAdded.setVisible(false);
                    hsAdded.setVisible(false);
                    hsSuccess.setVisible(false);
                    btnAddHouseImg.setEnabled(false);
                    btnCancelHotel.setEnabled(true);
                    btnUpdateHotel.setEnabled(true);
                    btnAddHotelImg.setEnabled(true);
                    btnRefresh.setEnabled(true);
                    lstHouses.clearSelection();
                    //Clearing the textfield/RadipButtons/TextArea for House Panel
                    txtHSName.setText("");
                    txtHSAddress.setText("");
                    txtHSPhone.setText("");
                    txtHSDescr.setText("");
                    txtHSOccu.setText("");
                    txtHSPrice.setText("");
                    txtHSCleanFee.setText("");
                    txtHSBedrooms.setText("");
                    btnGarage.clearSelection();
                    btnYard.clearSelection();
                    btnUpdateHouse.setEnabled(false);
                    btnRemoveHS.setVisible(false);
                    btnRemoveHS.setEnabled(false);
                    btnAddHouse.setVisible(true);
                    htAddErr.setVisible(false);
                    int htSelectedIndex = lstHotels.getSelectedIndex();
                    int choice = 1;
                    loadLodge(htSelectedIndex, choice);//Calling the loadLodge method and passing in the index and choice of hotel to display the hotel information
                    loadLodgeImages(selectedLodge, selectedAddress, type);//Calling the loadLodgeImages method to display the images to the panel
                } else {
                    btnCancelHotel.setEnabled(false);
                    btnAddHotel.setEnabled(true);
                    btnRemoveHT.setEnabled(false);
                }
            }
        });

        lstHouses.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //Clearing the image panel 
                imagePanel.removeAll();
                imagePanel.revalidate();
                imagePanel.repaint();
                int hsIndex = lstHouses.getSelectedIndex();
                String selectedLodge = (String) lstHouses.getSelectedValue();
                String type = "House";
                if (hsIndex != -1) {//Checking to see if a house is selected
                    House selectedHouse = lodgingHouse.get(hsIndex);
                    String selectedAddress = selectedHouse.getAddress();//Getting the address of the selected House
                    btnAddHouse.setVisible(false);
                    btnRemoveHS.setVisible(true);
                    btnRemoveHS.setEnabled(true);
                    //btnRemoveHS.setEnabled(false);
                    hsSuccess.setVisible(false);
                    htSuccess.setVisible(false);
                    htAdded.setVisible(false);
                    hsAdded.setVisible(false);
                    btnAddHouseImg.setEnabled(true);
                    btnCancelHouse.setEnabled(true);
                    btnUpdateHouse.setEnabled(true);
                    btnAddHotelImg.setEnabled(false);
                    btnRefresh.setEnabled(true);
                    lstHotels.clearSelection();
                    //Clearing the textfield/RadioButtons/TextArea for Hotel Panel
                    txtHTName.setText("");
                    txtHTAddress.setText("");
                    txtHTPhone.setText("");
                    txtHTDescr.setText("");
                    txtHTOccu.setText("");
                    txtHTRmPrice.setText("");
                    txtHTVacancies.setText("");
                    txtHTParking.setText("");
                    txtHTRmSvPrice.setText("");
                    btnGHotelBreakfast.clearSelection();
                    btnGValet.clearSelection();
                    btnGPool.clearSelection();
                    btnGPets.clearSelection();
                    btnUpdateHotel.setEnabled(false);
                    btnRemoveHT.setVisible(false);
                    btnRemoveHT.setEnabled(false);
                    btnAddHotel.setVisible(true);
                    hsAddErr.setVisible(false);
                    int hsSelectedIndex = lstHouses.getSelectedIndex();
                    int choice = 2;
                    loadLodge(hsSelectedIndex, choice);//Calling the loadLodge method and passing in the index and choice of house to display the house information
                    loadLodgeImages(selectedLodge, selectedAddress, type);//Calling the loadLodgeImages method to display the images to the panel
                } else {
                    btnCancelHouse.setEnabled(false);
                    btnAddHouse.setEnabled(true);
                    btnRemoveHS.setEnabled(false);
                }
            }
        });

        //ActionListener to add images for hotels
        btnAddHotelImg.addActionListener(e -> {
            int htIndex = lstHotels.getSelectedIndex();
            String name = lodgingHotel.get(htIndex).getName();
            String address = lodgingHotel.get(htIndex).getAddress();
            String type = "Hotel";
            btnCancelHotel.setEnabled(false);
            btnUpdateHotel.setEnabled(false);
            btnRemoveHT.setEnabled(false);
            btnAddHotelImg.setEnabled(false);
            addLodgeImages(name, type, address);//Calling the Method to add lodge Images
        });

        //ActionListener to add images for houses
        btnAddHouseImg.addActionListener(e -> {
            int hsIndex = lstHouses.getSelectedIndex();
            String name = lodgingHouse.get(hsIndex).getName();
            String address = lodgingHouse.get(hsIndex).getAddress();
            String type = "House";
            btnAddHouseImg.setEnabled(false);
            btnCancelHouse.setEnabled(false);
            btnRemoveHS.setEnabled(false);
            btnUpdateHouse.setEnabled(false);
            addLodgeImages(name, type, address);//Calling the Method to add lodge Images
        });

        //ActionListener for canceling editing
        btnCancelHotel.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();

            // Disable buttons immediately on cancel
            btnUpdateHotel.setEnabled(false);
            btnCancelHotel.setEnabled(false);
            btnRemoveHT.setEnabled(false);
            btnAddHotel.setVisible(true);
            btnRemoveHT.setVisible(false);
            btnAddHotelImg.setEnabled(false);
            lstHotels.setEnabled(true);
            lstHotels.clearSelection();
            //Clearing the textfields, radiobuttons and text area
            txtHTName.setText("");
            txtHTAddress.setText("");
            txtHTPhone.setText("");
            txtHTDescr.setText("");
            txtHTOccu.setText("");
            txtHTRmPrice.setText("");
            txtHTVacancies.setText("");
            txtHTParking.setText("");
            txtHTRmSvPrice.setText("");
            btnGHotelBreakfast.clearSelection();
            btnGValet.clearSelection();
            btnGPool.clearSelection();
            btnGPets.clearSelection();
        });
        //ActionListener for canceling editing
        btnCancelHouse.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            //Removing the images from the imagePanel
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();

            btnUpdateHouse.setEnabled(false);
            btnCancelHouse.setEnabled(false);
            btnRemoveHS.setEnabled(false);
            btnAddHouseImg.setEnabled(false);
            btnAddHouse.setVisible(true);
            btnRemoveHS.setVisible(false);
            lstHouses.setEnabled(true);
            lstHouses.clearSelection();
            //Clearing the textfields, radiobuttons and text area
            txtHSName.setText("");
            txtHSAddress.setText("");
            txtHSPhone.setText("");
            txtHSDescr.setText("");
            txtHSOccu.setText("");
            txtHSPrice.setText("");
            txtHSCleanFee.setText("");
            txtHSBedrooms.setText("");
            btnGarage.clearSelection();
            btnYard.clearSelection();
        });
        //ActionListener for updating a hotels information
        btnUpdateHotel.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            int htSelectedIndex = lstHotels.getSelectedIndex();
            int choice = 1;
            editLodge(htSelectedIndex, choice);
            //Clearing the textfields, radiobuttons and text area
            txtHTName.setText("");
            txtHTAddress.setText("");
            txtHTPhone.setText("");
            txtHTDescr.setText("");
            txtHTOccu.setText("");
            txtHTRmPrice.setText("");
            txtHTVacancies.setText("");
            txtHTParking.setText("");
            txtHTRmSvPrice.setText("");
            btnGHotelBreakfast.clearSelection();
            btnGValet.clearSelection();
            btnGPool.clearSelection();
            btnGPets.clearSelection();
            lstHotels.setEnabled(true);
            lstHotels.clearSelection();//clearing the selection

            btnCancelHotel.setEnabled(false);
            btnRemoveHT.setEnabled(false);
            btnUpdateHotel.setEnabled(false);
            btnAddHotelImg.setEnabled(false);
            btnAddHotel.setVisible(true);
            btnAddHotel.setEnabled(true);
            imagePanel.removeAll();
            imagePanel.repaint();
            imagePanel.revalidate();
        });
        //ActionListener for updating a houses information
        btnUpdateHouse.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            int hsSelectedIndex = lstHouses.getSelectedIndex();
            int choice = 2;
            editLodge(hsSelectedIndex, choice);
            //Clearing the textfields, radiobuttons and text area
            txtHSName.setText("");
            txtHSAddress.setText("");
            txtHSPhone.setText("");
            txtHSDescr.setText("");
            txtHSOccu.setText("");
            txtHSPrice.setText("");
            txtHSCleanFee.setText("");
            txtHSBedrooms.setText("");
            btnGarage.clearSelection();
            btnYard.clearSelection();
            lstHouses.setEnabled(true);
            lstHouses.clearSelection();//Clearing the selection
            btnCancelHouse.setEnabled(false);
            btnRemoveHS.setEnabled(false);
            btnUpdateHouse.setEnabled(false);
            btnAddHouseImg.setEnabled(false);
            btnAddHouse.setVisible(true);
            btnAddHouse.setEnabled(true);
            imagePanel.removeAll();
            imagePanel.repaint();
            imagePanel.revalidate();
        });

        btnRemoveHT.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            int htSelectedIndex = lstHotels.getSelectedIndex();
            int choice = 1;
            //Clearing the textfield/RadipButtons/TextArea for Hotel Panel
            txtHTName.setText("");
            txtHTAddress.setText("");
            txtHTPhone.setText("");
            txtHTDescr.setText("");
            txtHTOccu.setText("");
            txtHTRmPrice.setText("");
            txtHTVacancies.setText("");
            txtHTParking.setText("");
            txtHTRmSvPrice.setText("");
            btnGHotelBreakfast.clearSelection();
            btnGValet.clearSelection();
            btnGPool.clearSelection();
            btnGPets.clearSelection();

            //Disabling/enabling buttons
            btnUpdateHotel.setEnabled(false);
            btnRemoveHT.setVisible(false);
            btnRemoveHT.setEnabled(false);
            btnAddHotel.setVisible(true);
            htAddErr.setVisible(false);
            btnAddHotelImg.setEnabled(false);

            removeLodge(htSelectedIndex, choice);//Calling the removeLodge method and passing in the index and choice of hotel to remove
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            lstHotels.setEnabled(true);
            lstHouses.setEnabled(true);
            btnRefresh.setEnabled(true);
            loadInProgress = false;
        });

        btnRemoveHS.addActionListener(e -> {
            if (loadInProgress) {
                cancelLoad = true;
                loadInProgress = false;
            }
            int hsSelectedIndex = lstHouses.getSelectedIndex();
            int choice = 2;
            //Clearing the textfields, radiobuttons and text area
            txtHSName.setText("");
            txtHSAddress.setText("");
            txtHSPhone.setText("");
            txtHSDescr.setText("");
            txtHSOccu.setText("");
            txtHSPrice.setText("");
            txtHSCleanFee.setText("");
            txtHSBedrooms.setText("");
            btnGarage.clearSelection();
            btnYard.clearSelection();

            //Disabling/enabling buttons
            btnUpdateHouse.setEnabled(false);
            btnRemoveHS.setVisible(false);
            btnRemoveHS.setEnabled(false);
            btnAddHouse.setVisible(true);
            hsAddErr.setVisible(false);
            btnAddHouseImg.setEnabled(false);

            removeLodge(hsSelectedIndex, choice);//Calling the removeLodge method and passing in the index and choice of house to remove
            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            lstHotels.setEnabled(true);
            lstHouses.setEnabled(true);
            btnRefresh.setEnabled(true);
            loadInProgress = false;
        });

        //ActionListener to add a hotel
        btnAddHotel.addActionListener(e -> {
            int choice = 1;
            //If statement to check that user enters all fields for the Adding Hotels
            if ("".equals(txtHTName.getText()) || "".equals(txtHTAddress.getText()) || "".equals(txtHTPhone.getText()) || "".equals(txtHTOccu.getText())
                    || "".equals(txtHTRmPrice.getText()) || "".equals(txtHTParking.getText()) || "".equals(txtHTRmSvPrice.getText()) || "".equals(txtHTVacancies.getText())
                    || "".equals(txtHTDescr.getText())) {
                htAddErr.setText("Please fill out all fields before selecting Add Hotel!");
                htAddErr.setBounds(630, 50, 350, 30);
                htAddErr.setVisible(true);
                htSuccess.setVisible(false);
                return;
            }
            //Checking to see if the user inputs numeric values in the correct textboxes
            if (!isNumeric(txtHTOccu.getText()) || !isNumeric(txtHTRmPrice.getText()) || !isNumeric(txtHTRmSvPrice.getText()) || !isNumeric(txtHTParking.getText())) {
                htAddErr.setText("Please ensure that occupancy, room price, room service price, and parking are numbers!");
                htAddErr.setBounds(500, 50, 650, 30);
                htAddErr.setVisible(true);
                htSuccess.setVisible(false);
                return;
            }

            //Variables to store the selection of the true or false values that the user selects
            boolean htBreakfast = rBtnBreakT.isSelected() || rBtnBreakF.isSelected();
            boolean valet = rBtnValetT.isSelected() || rBtnValetF.isSelected();
            boolean pool = rBtnPoolT.isSelected() || rBtnPoolF.isSelected();
            boolean pets = rBtnPetsT.isSelected() || rBtnPetsF.isSelected();

            if (!htBreakfast || !valet || !pool || !pets) {
                htAddErr.setVisible(true);
                htSuccess.setVisible(false);
            }
            lblHtNumEx.setVisible(false);
            addLodge(choice);//Calling the addLodge method and passing in the choice of hotel to be added

        });

        //ActionListener to add a house
        btnAddHouse.addActionListener(e -> {
            int choice = 2;
            //If statement to check that user enters all fields for the Adding Houses
            if ("".equals(txtHSName.getText()) || "".equals(txtHSAddress.getText()) || "".equals(txtHSPhone.getText()) || "".equals(txtHSOccu.getText())
                    || "".equals(txtHSPrice.getText()) || "".equals(txtHSCleanFee.getText()) || "".equals(txtHSBedrooms.getText()) || "".equals(txtHSDescr.getText())) {
                hsAddErr.setText("Please fill out all fields before selecting Add House!");
                hsAddErr.setBounds(630, 350, 350, 30);
                hsAddErr.setVisible(true);
                hsSuccess.setVisible(false);
                return;
            }
            //Checking to see if the user inputs numeric values in the correct textboxes
            if (!isNumeric(txtHSOccu.getText()) || !isNumeric(txtHSPrice.getText()) || !isNumeric(txtHSCleanFee.getText()) || !isNumeric(txtHSBedrooms.getText())) {
                hsAddErr.setText("Please ensure that occupancy, price, cleaning fee, and bedrooms are numbers!");
                hsAddErr.setBounds(500, 350, 650, 30);
                hsAddErr.setVisible(true);
                hsSuccess.setVisible(false);
                return;
            }

            //Variables to store the selection of the true or false values that the user selects
            boolean garage = rBtnGarageT.isSelected() || rBtnGarageF.isSelected();
            boolean yard = rBtnYardT.isSelected() || rBtnYardF.isSelected();

            if (!garage || !yard) {
                hsAddErr.setVisible(true);
                hsSuccess.setVisible(false);
                return;
            }
            lblHsNumEx.setVisible(false);
            addLodge(choice);//Calling the addLodge method and passing in the choice of house to be added

        });

        if (!fillArrays) {
            populateArrayListLodges();//Calling this method to populate the arrayLists with lodgings
            fillArrays = true;
        }

        dateComboBoxes();//Calling this method to fill in the dates in the comboBoxes

        //ActionListener that changes the correct days when a month is selected
        cbxM1.addActionListener(e -> {
            int selectedMonth = cbxM1.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxY1.getSelectedItem());
            updateStartDayCbx(selectedMonth, year);
        });

        cbxM2.addActionListener(e -> {
            int selectedMonth = cbxM2.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxY2.getSelectedItem());
            updateEndDayCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the start year is selected 
        cbxY1.addActionListener(e -> {
            int selectedMonth = cbxM1.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxY1.getSelectedItem());
            updateStartDayCbx(selectedMonth, year);
        });
        //Action Listener that changes the days when the end year is selected 
        cbxY2.addActionListener(e -> {
            int selectedMonth = cbxM2.getSelectedIndex() - 1;
            int year = Integer.parseInt((String) cbxY2.getSelectedItem());
            updateEndDayCbx(selectedMonth, year);
        });
        //Action Listener for the get reports button 
        btnGetReports.addActionListener(e -> {
            try {
                //Creating an object of the GregorainCalendar class
                GregorianCalendar cal = new GregorianCalendar();

                //Getting the current date to set to the combo boxes
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);
                int currentYear = cal.get(Calendar.YEAR);

                //Getting the start dates and end dates from the comboBoxes
                int startMonth = cbxM1.getSelectedIndex() + 1;
                int startDay = Integer.parseInt((String) cbxD1.getSelectedItem());
                int startYear = Integer.parseInt((String) cbxY1.getSelectedItem());

                int endMonth = cbxM2.getSelectedIndex() + 1;
                int endDay = Integer.parseInt((String) cbxD2.getSelectedItem());
                int endYear = Integer.parseInt((String) cbxY2.getSelectedItem());

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
                } else {
                    try {
                        //Calling the get reports
                        getReports(startDates, endDates);
                        lblErrorDate.setVisible(false);
                        //Setting the current month as the selected month in the combo box
                        cbxM1.setSelectedIndex(currentMonth);
                        cbxM2.setSelectedIndex(currentMonth);

                        //Setting the current year as the selected year in the combo box
                        cbxY1.setSelectedItem(String.valueOf(currentYear));
                        cbxY2.setSelectedItem(String.valueOf(currentYear));

                        //Setting the current day as the selected day in the combo box
                        cbxD1.setSelectedItem(String.valueOf(currentDay));
                        cbxD2.setSelectedItem(String.valueOf(currentDay));

                    } catch (NumberFormatException ex) {
                        lblErrorDate.setText("Ensure all dates are filled out Correctly");
                        ex.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();

            }
        });

    }

    //Method to get the reports for all customers with the selected dates 
    private void getReports(String startDates, String endDates) throws IOException {
        if (tryConnection()) {
            //Query to get customers names and their total amount spent
            String query = "SELECT c.name AS CustomerName, SUM(o.totalPrice) AS totalSpent "
                    + "FROM Orders o JOIN Customers c ON o.customerID = c.customerID "
                    + " WHERE o.startDate BETWEEN ? AND ? "
                    + "GROUP BY c.name;";
            //StringBuilder for the HTML
            StringBuilder html = new StringBuilder();
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, startDates);
                ps.setString(2, endDates);

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
                           <h2>Customer Spending Report</h2>
                            <table><tr><th>Customer Name</th><th>Total Spent</th></tr>""");

                ResultSet rs = ps.executeQuery();
                //using a while loop to add all of the customers to the table within the dates selected
                while (rs.next()) {
                    String customerName = rs.getString("CustomerName");
                    double totalSpent = rs.getDouble("totalSpent");
                    //Adding the customerName and total spent to the HTML
                    html.append("<tr><td>").append(customerName)
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

                //Saving the HTML report to the file
                File reportFile = new File(reportsFolderPath + "/EmployeeReport.html");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
                    writer.write(html.toString());
                }

                //Opening the saved HTML file in the browser
                Desktop.getDesktop().browse(reportFile.toURI());

            } catch (SQLException ex) {
                System.out.println("Error with SQL statements when getting Employee Reports" + ex.getMessage());
                ex.printStackTrace();
            }
        }
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
            cbxM1.addItem(month);
            cbxM2.addItem(month);
        }

        // Populating the years 
        int endYear = 2027;
        for (int yr = currentYear; yr <= endYear; yr++) {
            cbxY1.addItem(String.valueOf(yr));
            cbxY2.addItem(String.valueOf(yr));
        }

        //Setting the current month as the selected month in the combo box
        cbxM1.setSelectedIndex(currentMonth);
        cbxM2.setSelectedIndex(currentMonth);

        //Setting the current year as the selected year in the combo box
        cbxY1.setSelectedItem(String.valueOf(currentYear));
        cbxY2.setSelectedItem(String.valueOf(currentYear));

        // Initializing the day comboBoxes based on the current date
        updateStartDayCbx(cal.get(Calendar.MONTH), currentYear);
        updateEndDayCbx(cal.get(Calendar.MONTH), currentYear);

        //Setting the current day as the selected day in the combo box
        cbxD1.setSelectedItem(String.valueOf(currentDay));
        cbxD2.setSelectedItem(String.valueOf(currentDay));
    }

    //Method to check if the user inputs numeric values when adding a Hotel or House
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Method to add days to the start day comboxBoxes
    private void updateStartDayCbx(int month, int year) {
        //String to hold the day that is selected by the user
        String selectedSDate = (String) cbxD1.getSelectedItem();
        //Clearing the comboBoxes of the items that were in it
        cbxD1.removeAllItems();

        //Getting the days for the selected month and year
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Populating the days with the correct days for the month and year selected
        for (int day = 1; day <= dayOfMonth; day++) {
            cbxD1.addItem(String.valueOf(day));
        }
        //if else to check if the selected day is valid for the month/year selected
        if (selectedSDate != null) {
            int selectedSDay = Integer.parseInt(selectedSDate);
            if (selectedSDay <= dayOfMonth) {
                cbxD1.setSelectedItem(selectedSDate);
            } else {
                cbxD1.setSelectedIndex(0);//If the day selected is invalid, reset it to 0
            }
        }
    }

    //Method to add days to the end day comboxBoxes
    private void updateEndDayCbx(int month, int year) {
        //String to hold the day that is selected by the user
        String selectedEDate = (String) cbxD2.getSelectedItem();
        //Clearing the comboBoxes of the items that were in it
        cbxD2.removeAllItems();

        //Getting the days for the selected month and year
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Populating the days with the correct days for the month and year selected
        for (int day = 1; day <= dayOfMonth; day++) {
            cbxD2.addItem(String.valueOf(day));
        }
        //if else to check if the selected day is valid for the month/year selected
        if (selectedEDate != null) {
            int selectedEDay = Integer.parseInt(selectedEDate);
            if (selectedEDay <= dayOfMonth) {
                cbxD2.setSelectedItem(selectedEDate);
            } else {
                cbxD2.setSelectedIndex(0);
            }
        }
    }

    //Method to add the lodge images to the database
    private void addLodgeImages(String lodgeName, String type, String address) {
        int lodgeID = getLodgeID(lodgeName, address);//Calling a method to get the lodgeID for the selected Lodging
        //FileChooser to select which files to add to the database
        JFileChooser fc = new JFileChooser();
        //Allowing selection of mulitple files at once
        fc.setMultiSelectionEnabled(true);
        //Adding a filter to make finding images easier
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
        //Setting the file filter
        fc.setFileFilter(filter);

        int result = fc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fc.getSelectedFiles();
            int totalFiles = selectedFiles.length;
            progBar.setMinimum(0);
            progBar.setMaximum(totalFiles);
            progBar.setValue(0);
            progBar.setString("Starting upload...");

            //Setting progressPanel to visible
            progPanel.setVisible(true);
            progPanel.revalidate();
            progPanel.repaint();

            //Creating a thread to run the query to add lodge Images to the database
            Thread dbThread = new Thread(() -> {
                if (tryConnection()) {
                    try {
                        String query = "INSERT into LodgeImages (lodgeID, imageName, img) VALUES (?,?,?)";
                        PreparedStatement ps = con.prepareStatement(query);

                        for (int i = 0; i < totalFiles; i++) {
                            File file = selectedFiles[i];
                            //Converting the file to a byte array
                            byte[] b = Files.readAllBytes(file.toPath());
                            ps.setInt(1, lodgeID);
                            ps.setString(2, file.getName());
                            ps.setBinaryStream(3, new ByteArrayInputStream(b, 0, b.length));
                            ps.executeUpdate();

                            //Updating the progress bar
                            progBar.setValue(i + 1);
                            progBar.setString("Uploading " + (i + 1) + "/" + totalFiles + " images");

                        }

                    } catch (SQLException ex) {
                        System.out.println("Error with SQL statement" + ex.getMessage());
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        System.out.println("Error adding data to the database" + ex.getMessage());
                        ex.printStackTrace();
                    }
                    newImagesAdded = true;
                    System.out.println("Images added to the database Successfully!");
                    //Calling the loadLodgeImages method
                    loadLodgeImages(lodgeName, address, type);

                    //Hiding the progress bar after images are added to the database
                    progPanel.setVisible(false);
                }
            });
            dbThread.start();
        }
    }

    //Method to get the lodgeID based on the name of the lodge chosen
    private int getLodgeID(String name, String address) {
        int[] lodgeID = new int[1];//Using an array to hold the lodgeID
        lodgeID[0] = 0;
        if (tryConnection()) {
            Thread dbThread = new Thread(() -> {
                try {
                    String query = "SELECT LodgeID FROM Lodges where name = ? AND address = ? AND isActive = true";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, name);
                    ps.setString(2, address);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        lodgeID[0] = rs.getInt("lodgeID");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error with SQL statement" + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            dbThread.start();
            //Try catch for any errors while waiting for the thread to finish when getting lodgeID
            try {
                dbThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return lodgeID[0];
    }

    //Method to load the lodge images into the imagePanel
    private void loadLodgeImages(String lodgeName, String address, String type) {
        //Clearing the image Panel
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        //Setting the name of the selected lodge, for adding images 
        selectedLodge = lodgeName;
        //Setting to flags to false 
        cancelLoad = false;
        loadInProgress = false;
        //Initializing a lodge variable
        Lodging lodge = null;
        //Searching the lodgingHotel list
        if (type.equals("Hotel")) {
            for (int i = 0; i < lodgingHotel.size(); i++) {
                Hotel hotel = lodgingHotel.get(i);
                //if the lodgeName passed is found, it sets the lodge variable to that hotel name
                if (hotel.getName().equals(lodgeName) && hotel.getAddress().equals(address)) {
                    lodge = hotel;
                    break;
                }
            }
        } else if (type.equals("House")) {
            //Searching the lodgingHouse list if nothing is found in the lodgingHotel list
            for (int i = 0; i < lodgingHouse.size(); i++) {
                House house = lodgingHouse.get(i);
                if (house.getName().equals(lodgeName) && house.getAddress().equals(address)) {
                    lodge = house;
                    break;
                }
            }
        }

        //Checking to make sure that lodge in not null
        if (lodge == null) {
            System.out.println("Lodge not found: " + lodgeName);
            return;
        }
        //If the lodgeImageArrayList is empty
        if (lodge.getImages().isEmpty() || newImagesAdded == true) {
            loadInProgress = true;

            int lodgeID = lodge.lodgeID;//getting the lodgeID from the lodge object
            final Lodging finalLodge = lodge;
            //A thread to load the images from the database and add them to the panel as they are retrieved
            Thread dbThread = new Thread(() -> {
                //loadInProgress = true;
                ArrayList<ImageIcon> images = getImagesFromDatabase(lodgeID);//Calling the getImageFromDatabase to add each image to the lodge object, passing LodgeID
                
                //Add each image to the lodge object
                for (int i = 0; i < images.size(); i++) {
                    finalLodge.addImage(images.get(i));//Adding image to the lodge object
                }

                //For each image, update the image panel
                for (int i = 0; i < images.size(); i++) {
                    ImageIcon icon = images.get(i);
                    Image img = icon.getImage();
                    Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    JLabel thumbnailImg = new JLabel(scaledIcon);

                    //Mouse listener for when an image is clicked
                    thumbnailImg.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            openWindow(lodgeName, icon);//Calling the openWindow method to display the image
                        }
                    });
                    //Checking to see if the images being added is the selected lodge, and if cancel load is false: Adds Images to panel
                    if (lodgeName.equals(selectedLodge) && !cancelLoad) {
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
                    jspImages.setBounds(3, 750, 1180, jspHeight);
                    jspImages.revalidate();
                    jspImages.repaint();
                    ((Timer) e.getSource()).stop();
                }).start();

                loadInProgress = false;
                newImagesAdded = false;
                //Checking to see if cancel load is false, if it is- Re-enabled the buttons
                if (!cancelLoad) {
                    if (type.equals("Hotel")) {
                        btnCancelHotel.setEnabled(true);
                        btnUpdateHotel.setEnabled(true);
                        btnAddHotelImg.setEnabled(true);
                        btnRemoveHT.setEnabled(true);
                    } else {
                        btnAddHouseImg.setEnabled(true);
                        btnCancelHouse.setEnabled(true);
                        btnUpdateHouse.setEnabled(true);
                        btnRemoveHS.setEnabled(true);
                    }
                }

            });
            dbThread.start();//Starting the thread to get the images 

        } else {
            addImagesToPanel(lodge.getImages(), lodgeName, type);//Calling this method to add images from the arrayList, when they have already been loaded from the database
        }
    }
    private boolean loadingForSelectedLodge = false;
    //Method to add images to the panel if they have already been retrieved from the database
    private void addImagesToPanel(ArrayList<ImageIcon> images, String lodgeName, String type) {
        System.out.println("Loading Images from the ArrayList");
        //Removing all of the images from the image panel before adding them
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        loadingForSelectedLodge = true;
        //Initializing a lodge
        cancelLoad = false;
        //Variable to keep track of the threads
        int threadNumber = ++currentThread;
        //Creating a thread to add images from the arraylist to the panel
        Thread imgThread = new Thread(() -> {
            loadInProgress = true;
            //If the load is canceled, stop processing
            for (int i = 0; i < images.size(); i++) {
                // If the lodge has changed, stop processing images for the previous lodge
                if (!loadingForSelectedLodge) {
                    return;  // If the lodge has changed, exit the loop
                }
                //If the load is canceled, stop processing
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

                //Checking to see if the images being added are from the correct thread and if cancel load is false: Adds images to the panel
                if (threadNumber == currentThread && !cancelLoad) {
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
                jspImages.setBounds(3, 750, 1180, jspHeight);
                jspImages.revalidate();
                jspImages.repaint();
                ((Timer) e.getSource()).stop();
            }).start();

            loadInProgress = false;
            //Checking to see if cancel load is false, if it is- Re-enabled the buttons
            if (!cancelLoad) {
                if (type.equals("Hotel")) {
                    btnCancelHotel.setEnabled(true);
                    btnUpdateHotel.setEnabled(true);
                    btnAddHotelImg.setEnabled(true);
                    btnRemoveHT.setEnabled(true);
                } else {
                    btnAddHouseImg.setEnabled(true);
                    btnCancelHouse.setEnabled(true);
                    btnUpdateHouse.setEnabled(true);
                    btnRemoveHS.setEnabled(true);
                }
            }

        });
        imgThread.start();//Starting the thread
    }
    //Method to get images from the database 
    private ArrayList<ImageIcon> getImagesFromDatabase(int lodgeID) {
        System.out.println("Loading Images From the Database");
        //Creating an arrayList to store images from the database
        ArrayList<ImageIcon> images = new ArrayList<>();
        if (tryConnection()) {
            try {
                String query = "SELECT img FROM LodgeImages WHERE lodgeID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, lodgeID);
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

        //Action listeners for the previous button
        prevButton.addActionListener(e -> {
            if (!images.isEmpty()) {
                // Move index backwards to go to the previous image in the arrayList
                currentIndex[0] = (currentIndex[0] - 1 + images.size()) % images.size();
                ImageIcon previousImage = images.get(currentIndex[0]);
                displayImageInCarousel(imageLabel, previousImage);
            }
        });
        //Action listener for the next button
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

    //Method to add a lodge(Hotel or House) to the ArrayList
    private void addLodge(int choice) {
        CustomerView customerLoad = new CustomerView();//Creating an instance of CustomerView class to update the list of lodges
        //Using a Thread lambda to create a thread to execute the queries for the lodge information 
        Thread dbThread = new Thread(() -> {
            if (tryConnection()) {
                try {
                    String nameCheck, addressCheck;
                    if (choice == 1) {
                        nameCheck = txtHTName.getText();
                        addressCheck = txtHTAddress.getText();
                    } else if (choice == 2) {
                        nameCheck = txtHSName.getText();
                        addressCheck = txtHSAddress.getText();
                    } else {
                        return; // Invalid choice
                    }
                    //Checking to see if the lodge already exists
                    String checkQuery = "SELECT lodgeID FROM Lodges WHERE name = ? AND address = ?";
                    PreparedStatement psCheck = con.prepareStatement(checkQuery);
                    psCheck.setString(1, nameCheck);
                    psCheck.setString(2, addressCheck);
                    ResultSet rsCheck = psCheck.executeQuery();

                    //If the lodge exists it displays to the user
                    if (rsCheck.next()) {
                        if (choice == 1) {
                            htAddErr.setText("This Hotel already exists.");
                            htAddErr.setBounds(670, 50, 350, 30);
                            htAddErr.setVisible(true);
                            txtHTName.setText("");
                            txtHTAddress.setText("");
                            txtHTPhone.setText("");
                            txtHTDescr.setText("");
                            txtHTOccu.setText("");
                            txtHTRmPrice.setText("");
                            txtHTVacancies.setText("");
                            txtHTParking.setText("");
                            txtHTRmSvPrice.setText("");
                            btnGHotelBreakfast.clearSelection();
                            btnGValet.clearSelection();
                            btnGPool.clearSelection();
                            btnGPets.clearSelection();
                        } else if (choice == 2) {
                            htAddErr.setText("This House already exists.");
                            hsAddErr.setBounds(670, 350, 350, 30);
                            hsAddErr.setVisible(true);
                            txtHSName.setText("");
                            txtHSAddress.setText("");
                            txtHSPhone.setText("");
                            txtHSDescr.setText("");
                            txtHSOccu.setText("");
                            txtHSPrice.setText("");
                            txtHSCleanFee.setText("");
                            txtHSBedrooms.setText("");
                            btnGarage.clearSelection();
                            btnYard.clearSelection();
                        }
                        return;
                    }

                    if (choice == 1) {
                        //Setting variables Hotel lodge information
                        String name = txtHTName.getText();
                        String address = txtHTAddress.getText();
                        String phoneNumber = txtHTPhone.getText();
                        String description = txtHTDescr.getText();
                        int maxOccupants = Integer.parseInt(txtHTOccu.getText());
                        double basePricePerNight = Double.parseDouble(txtHTRmPrice.getText());

                        String lodgeQuery = "INSERT INTO Lodges (name, address, phoneNumber, description, maxOccupants, basePricePerNight) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement psLodges = con.prepareStatement(lodgeQuery, Statement.RETURN_GENERATED_KEYS);
                        //Adding the hotel information to Lodges
                        psLodges.setString(1, name);
                        psLodges.setString(2, address);
                        psLodges.setString(3, phoneNumber);
                        psLodges.setString(4, description);
                        psLodges.setInt(5, maxOccupants);
                        psLodges.setDouble(6, basePricePerNight);
                        psLodges.executeUpdate();

                        //Calling the getGeneratedKeys on the preparedStatement to get the lodgeID that was created
                        ResultSet getKey = psLodges.getGeneratedKeys();
                        //Variable to hold the lodgeID
                        int lodgeID = 0;
                        //If the getKey is true, it gets the newly generated key and sets lodgeID to that key
                        if (getKey.next()) {
                            lodgeID = getKey.getInt(1);
                        }

                        //Setting variables with hotel information
                        boolean hasFreeBreakfast = rBtnBreakT.isSelected();
                        int vacancies = Integer.parseInt(txtHTVacancies.getText());
                        boolean valetParking = rBtnValetT.isSelected();
                        double parkingFee = Double.parseDouble(txtHTParking.getText());
                        boolean hasPool = rBtnPoolT.isSelected();
                        boolean petsAllowed = rBtnPetsT.isSelected();
                        double roomServicePrice = Double.parseDouble(txtHTRmSvPrice.getText());

                        //Query to instert hotel information in the Hotels table
                        String hotelQuery = "INSERT INTO Hotels (lodgeID, hasFreeBreakfast, vacancies, hasValetParking, parkingFee, hasPool, petsAllowed, roomServicePrice)"
                                + " VALUES(?,?,?,?,?,?,?,?)";
                        PreparedStatement psHotel = con.prepareStatement(hotelQuery);
                        //Adding the hotel information to Lodges
                        psHotel.setInt(1, lodgeID);
                        psHotel.setBoolean(2, hasFreeBreakfast);
                        psHotel.setInt(3, vacancies);
                        psHotel.setBoolean(4, valetParking);
                        psHotel.setDouble(5, parkingFee);
                        psHotel.setBoolean(6, hasPool);
                        psHotel.setBoolean(7, petsAllowed);
                        psHotel.setDouble(8, roomServicePrice);
                        psHotel.executeUpdate();

                        //Clearing the textfields, radiobuttons and text area
                        txtHTName.setText("");
                        txtHTAddress.setText("");
                        txtHTPhone.setText("");
                        txtHTDescr.setText("");
                        txtHTOccu.setText("");
                        txtHTRmPrice.setText("");
                        txtHTVacancies.setText("");
                        txtHTParking.setText("");
                        txtHTRmSvPrice.setText("");
                        btnGHotelBreakfast.clearSelection();
                        btnGValet.clearSelection();
                        btnGPool.clearSelection();
                        btnGPets.clearSelection();
                        htAddErr.setVisible(false);
                        btnCancelHotel.setEnabled(false);
                        btnRemoveHT.setEnabled(false);
                        btnUpdateHotel.setEnabled(false);
                        btnAddHotel.setEnabled(true);
                        htAdded.setVisible(true);
                        htAddErr.setVisible(false);
                        hsAddErr.setVisible(false);
                        populateArrayListLodges();//Calling this method to repopulate the arraylist from the database with the current lodges
                        listLodging();//Displaying the update list of hotels
                        customerLoad.viewLodges(0);
                    } else if (choice == 2) {
                        //Setting variables with house lodge Information
                        String name = txtHSName.getText();
                        String address = txtHSAddress.getText();
                        String phoneNumber = txtHSPhone.getText();
                        String description = txtHSDescr.getText();
                        int maxOccupants = Integer.parseInt(txtHSOccu.getText());
                        double basePricePerNight = Double.parseDouble(txtHSPrice.getText());

                        String lodgeQuery = "INSERT INTO Lodges (name, address, phoneNumber, description, maxOccupants, basePricePerNight) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement psLodges = con.prepareStatement(lodgeQuery, Statement.RETURN_GENERATED_KEYS);
                        //Adding the hotel information to Lodges
                        psLodges.setString(1, name);
                        psLodges.setString(2, address);
                        psLodges.setString(3, phoneNumber);
                        psLodges.setString(4, description);
                        psLodges.setInt(5, maxOccupants);
                        psLodges.setDouble(6, basePricePerNight);
                        psLodges.executeUpdate();

                        //Calling the getGeneratedKeys on the preparedStatement to get the lodgeID that was created
                        ResultSet getKey = psLodges.getGeneratedKeys();
                        //Variable to hold the lodgeID
                        int lodgeID = 0;
                        //If the getKey is true, it gets the newly generated key and sets lodgeID to that key
                        if (getKey.next()) {
                            lodgeID = getKey.getInt(1);
                        }
                        //Setting variables with house information 
                        int numberOfBedrooms = Integer.parseInt(txtHSBedrooms.getText());
                        boolean hasGarage = rBtnGarageT.isSelected();
                        double cleaningFee = Double.parseDouble(txtHSCleanFee.getText());
                        boolean hasBackyard = rBtnYardT.isSelected();

                        //Query to insert house information in the Houses table
                        String houseQuery = "INSERT INTO Houses (lodgeID, numberOfBedrooms, hasGarage, cleaningFee, hasBackyard) VALUES(?,?,?,?,?)";
                        PreparedStatement psHouse = con.prepareStatement(houseQuery);
                        //Adding the hotel information to Lodges
                        psHouse.setInt(1, lodgeID);
                        psHouse.setInt(2, numberOfBedrooms);
                        psHouse.setBoolean(3, hasGarage);
                        psHouse.setDouble(4, cleaningFee);
                        psHouse.setBoolean(5, hasBackyard);
                        psHouse.executeUpdate();
                        listLodging();//Displaying the update list of hotels
                        //Clearing the textfields, radiobuttons and text area
                        txtHSName.setText("");
                        txtHSAddress.setText("");
                        txtHSPhone.setText("");
                        txtHSDescr.setText("");
                        txtHSOccu.setText("");
                        txtHSPrice.setText("");
                        txtHSCleanFee.setText("");
                        txtHSBedrooms.setText("");
                        btnGarage.clearSelection();
                        btnYard.clearSelection();
                        hsAddErr.setVisible(false);
                        btnCancelHouse.setEnabled(false);
                        btnRemoveHS.setEnabled(false);
                        btnUpdateHouse.setEnabled(false);
                        btnAddHouse.setEnabled(true);
                        hsAdded.setVisible(true);
                        htAddErr.setVisible(false);
                        hsAddErr.setVisible(false);
                        populateArrayListLodges();//Calling this method to repopulate the arraylist from the database with the current lodges
                        listLodging();//Displaying the update list of houses
                        customerLoad.viewLodges(0);
                    }

                } catch (SQLException ex) {
                    System.out.println("Error with SQL statements when adding a lodge to the database. " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                save();//Saving the lodging to the file
            }
        });
        dbThread.start();
    }

//Method to populate the arrayList from the database
    private void populateArrayListLodges() {
        //Cearing the hotel and house arraylist
        lodgingHotel.clear();
        lodgingHouse.clear();
        //If connection is successful returns true, and loads data from the database, if false if loads data from the file
        if (tryConnection()) {
            //Using a Thread lambda to create a thread to execute the queries to populate the arrayList with lodges  
            Thread dbThread = new Thread(() -> {
                try {
                    //A query to get the hotels
                    String hotelQuery = "SELECT l.lodgeID, l.name, l.address, l.phoneNumber, l.description, l.maxOccupants, l.basePricePerNight, "
                            + "h.hasFreeBreakfast, h.vacancies, h.hasValetParking, h.parkingFee, h.hasPool, h.petsAllowed, h.roomServicePrice "
                            + "FROM Lodges l JOIN Hotels h ON l.lodgeID = h.lodgeID "
                            + "WHERE isActive = TRUE";
                    PreparedStatement psHotel = con.prepareStatement(hotelQuery);
                    ResultSet rsHotel = psHotel.executeQuery();

                    while (rsHotel.next()) {
                        int lodgeID = rsHotel.getInt("lodgeID");
                        String name = rsHotel.getString("name");
                        String address = rsHotel.getString("address");
                        String phoneNumber = rsHotel.getString("phoneNumber");
                        String description = rsHotel.getString("description");
                        int maxOccupants = rsHotel.getInt("maxOccupants");
                        double basePricePerNight = rsHotel.getDouble("basePricePerNight");
                        boolean hasFreeBreakfast = rsHotel.getBoolean("hasFreeBreakfast");
                        int vacancies = rsHotel.getInt("vacancies");
                        boolean hasValetParking = rsHotel.getBoolean("hasValetParking");
                        double parkingFee = rsHotel.getDouble("parkingFee");
                        boolean hasPool = rsHotel.getBoolean("hasPool");
                        boolean petsAllowed = rsHotel.getBoolean("petsAllowed");
                        double roomServicePrice = rsHotel.getDouble("roomServicePrice");
                        //Creating a new hotel object
                        Hotel hotel = new Hotel(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight, hasFreeBreakfast,
                                vacancies, hasValetParking, parkingFee, hasPool, petsAllowed, roomServicePrice);
                        //Adding the hotel to the arrayList
                        lodgingHotel.add(hotel);
                    }
                    //A query to get the houses
                    String houseQuery = "SELECT l.lodgeID, l.name, l.address, l.phoneNumber, l.description, l.maxOccupants, l.basePricePerNight, "
                            + "h.numberOfBedrooms, h.hasGarage, h.cleaningFee, h.hasBackyard "
                            + "FROM Lodges l JOIN Houses h ON l.lodgeID = h.lodgeID "
                            + "WHERE isActive = TRUE";
                    PreparedStatement psHouse = con.prepareStatement(houseQuery);
                    ResultSet rsHouse = psHouse.executeQuery();

                    while (rsHouse.next()) {
                        int lodgeID = rsHouse.getInt("lodgeID");
                        String name = rsHouse.getString("name");
                        String address = rsHouse.getString("address");
                        String phoneNumber = rsHouse.getString("phoneNumber");
                        String description = rsHouse.getString("description");
                        int maxOccupants = rsHouse.getInt("maxOccupants");
                        double basePricePerNight = rsHouse.getDouble("basePricePerNight");
                        int numberOfBedrooms = rsHouse.getInt("numberOfBedrooms");
                        boolean hasGarage = rsHouse.getBoolean("hasGarage");
                        double cleaningFee = rsHouse.getDouble("cleaningFee");
                        boolean hasBackyard = rsHouse.getBoolean("hasBackyard");
                        //Creating a new house object
                        House house = new House(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight, numberOfBedrooms,
                                hasGarage, cleaningFee, hasBackyard);
                        //Adding the houses to the arrayList
                        lodgingHouse.add(house);
                    }

                    //listLodging();//Calling the list lodging method to add the hotels to the JList
                } catch (SQLException ex) {
                    System.out.println("Error with SQL statements when populating the ArrayLists with the lodges. " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            //Starting the thread 
            dbThread.start();

            //Try catch for any errors while waiting for the thread to finish
            try {
                dbThread.join();
                listLodging(); //Calling list lodging method to add the hotels to the JList
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            load();
        }
    }

    //Method to load the lodgings in the textboxes from the arrayList
    private void loadLodge(int index, int choice) {
        //Checking to see if a lodge is loading
//        if (loadInProgress) {
//            lstHotels.clearSelection();
//            lstHouses.clearSelection();
//            return; //Exits if the loading is in progress
//        }
        if (choice == 1) { // If choice = Hotel
            if (index >= 0 && index < lodgingHotel.size()) {
                Hotel hotel = lodgingHotel.get(index); // Creating a hotel object from the lodgingHotel ArrayList
                // Displaying hotel details in the panel
                displayHotel(hotel);
            }
        } else if (choice == 2) {//If choice = House
            if (index >= 0 && index < lodgingHouse.size()) {
                House house = lodgingHouse.get(index);

                //Displaying house details in the panel
                displayHouses(house);
            }

        }
    }

    //Method to edit the lodges 
    private void editLodge(int index, int choice) {
        if (choice == 1) { // If choice = Hotel
            if (index >= 0 && index < lodgingHotel.size()) {
                Hotel hotel = lodgingHotel.get(index); // Creating a hotel object from the lodgingHotel ArrayList
                //Updating the hotel with the values inside the textfields and radiobuttons
                updateHotel(hotel);
                htSuccess.setVisible(true);
                //save();
                listLodging();
            }
        } else if (choice == 2) {//If choice = House
            if (index >= 0 && index < lodgingHouse.size()) {
                House house = lodgingHouse.get(index);
                //Updating the house with the values inside the textfields and radiobuttons
                updateHouse(house);
                hsSuccess.setVisible(true);
                //save();
                listLodging();
            }
        }
    }

    //Method to display the hotel of the chosen Index in the textfields and radiobuttons
    private void displayHotel(Hotel hotel) {
        txtHTName.setText(hotel.getName());
        txtHTAddress.setText(hotel.getAddress());
        txtHTPhone.setText(hotel.getPhoneNumber());
        txtHTDescr.setText(hotel.getDescription());
        txtHTOccu.setText(String.valueOf(hotel.getMaxOccupants()));
        txtHTRmPrice.setText(String.format("$%.2f", hotel.getBasePricePerNight()));
        rBtnBreakT.setSelected(hotel.isHasFreeBreakfast());
        rBtnBreakF.setSelected(!hotel.isHasFreeBreakfast());
        txtHTVacancies.setText(String.valueOf(hotel.getVacancies()));
        rBtnValetT.setSelected(hotel.isValetParking());
        rBtnValetF.setSelected(!hotel.isValetParking());
        txtHTParking.setText(String.format("$%.2f", hotel.getParkingFee()));
        rBtnPoolT.setSelected(hotel.isHasPool());
        rBtnPoolF.setSelected(!hotel.isHasPool());
        rBtnPetsT.setSelected(hotel.isPetsAllowed());
        rBtnPetsF.setSelected(!hotel.isPetsAllowed());
        txtHTRmSvPrice.setText(String.format("$%.2f", hotel.getRoomServicePrice()));
    }

    //Method to update the hotels 
    private void updateHotel(Hotel hotel) {
        if (tryConnection()) {
            //Using a Thread lambda to create a thread to execute the queries to update lodge info for Hotels
            Thread dbThread = new Thread(() -> {
                try {
                    //Updating the hotel object with the new values
                    hotel.setName(txtHTName.getText());
                    hotel.setAddress(txtHTAddress.getText());
                    hotel.setPhoneNumber(txtHTPhone.getText());
                    hotel.setDescription(txtHTDescr.getText());
                    hotel.setMaxOccupants(Integer.parseInt(txtHTOccu.getText()));
                    hotel.setBasePricePerNight(Double.parseDouble(txtHTRmPrice.getText().replace("$", "").trim()));
                    hotel.setHasFreeBreakfast(rBtnBreakT.isSelected());
                    hotel.setVacancies(Integer.parseInt(txtHTVacancies.getText()));
                    hotel.setValetParking(rBtnValetT.isSelected());
                    hotel.setParkingFee(Double.parseDouble(txtHTParking.getText().replace("$", "").trim()));
                    hotel.setHasPool(rBtnPoolT.isSelected());
                    hotel.setPetsAllowed(rBtnPetsT.isSelected());
                    hotel.setRoomServicePrice(Double.parseDouble(txtHTRmSvPrice.getText().replace("$", "").trim()));
                    //String query to update the lodge and hotel tables 
                    String query = "UPDATE Lodges l JOIN Hotels h ON l.lodgeID = h.lodgeID "
                            + "SET l.name = ?, l.address = ?, l.phoneNumber = ?, l.description = ?, l.maxOccupants = ?, "
                            + "l.basePricePerNight = ?, h.hasFreeBreakfast = ?, h.vacancies = ?, "
                            + "h.hasValetParking = ?, h.parkingFee = ?, h.hasPool = ?, h.petsAllowed = ?, h.roomServicePrice = ? WHERE h.lodgeID = ?";

                    //Prepared statement for the update
                    PreparedStatement psUpdate = con.prepareStatement(query);

                    psUpdate.setString(1, hotel.getName());
                    psUpdate.setString(2, hotel.getAddress());
                    psUpdate.setString(3, hotel.getPhoneNumber());
                    psUpdate.setString(4, hotel.getDescription());
                    psUpdate.setInt(5, hotel.getMaxOccupants());
                    psUpdate.setDouble(6, hotel.getBasePricePerNight());
                    psUpdate.setBoolean(7, hotel.isHasFreeBreakfast());
                    psUpdate.setInt(8, hotel.getVacancies());
                    psUpdate.setBoolean(9, hotel.isValetParking());
                    psUpdate.setDouble(10, hotel.getParkingFee());
                    psUpdate.setBoolean(11, hotel.isHasPool());
                    psUpdate.setBoolean(12, hotel.isPetsAllowed());
                    psUpdate.setDouble(13, hotel.getRoomServicePrice());
                    psUpdate.setString(14, Integer.toString(hotel.getLodgeID()));
                    //Executing the update
                    int rowsAffected = psUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Hotel updated successfully.");
                    } else {
                        System.out.println("Error updating hotel");
                    }

                } catch (SQLException ex) {
                    System.out.println("Update error when updating the Hotel. " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            dbThread.start();
        } else {
            //Updating the hotel object with the new values
            hotel.setName(txtHTName.getText());
            hotel.setAddress(txtHTAddress.getText());
            hotel.setPhoneNumber(txtHTPhone.getText());
            hotel.setDescription(txtHTDescr.getText());
            hotel.setMaxOccupants(Integer.parseInt(txtHTOccu.getText()));
            hotel.setBasePricePerNight(Double.parseDouble(txtHTRmPrice.getText().replace("$", "").trim()));
            hotel.setHasFreeBreakfast(rBtnBreakT.isSelected());
            hotel.setVacancies(Integer.parseInt(txtHTVacancies.getText()));
            hotel.setValetParking(rBtnValetT.isSelected());
            hotel.setParkingFee(Double.parseDouble(txtHTParking.getText().replace("$", "").trim()));
            hotel.setHasPool(rBtnPoolT.isSelected());
            hotel.setPetsAllowed(rBtnPetsT.isSelected());
            hotel.setRoomServicePrice(Double.parseDouble(txtHTRmSvPrice.getText().replace("$", "").trim()));
            save();
            listLodging();
        }
    }
//Method to display the house of the chosen Index in the textfields and radiobuttons
    private void displayHouses(House house) {
        txtHSName.setText(house.getName());
        txtHSAddress.setText(house.getAddress());
        txtHSPhone.setText(house.getPhoneNumber());
        txtHSDescr.setText(house.getDescription());
        txtHSOccu.setText(String.valueOf(house.getMaxOccupants()));
        txtHSPrice.setText(String.format("$%.2f", house.getBasePricePerNight()));
        txtHSCleanFee.setText(String.format("$%.2f", house.getCleaningFee()));
        txtHSBedrooms.setText(String.valueOf(house.getNumberOfBedrooms()));
        rBtnGarageT.setSelected(house.isHasGarage());
        rBtnGarageF.setSelected(!house.isHasGarage());
        rBtnYardT.setSelected(house.isHasBackyard());
        rBtnYardF.setSelected(!house.isHasBackyard());
    }
//Method to update the houses in the database or in the file
    private void updateHouse(House house) {
        if (tryConnection()) {
            //Using a Thread lambda to create a thread to execute the queries to update lodge info for houses 
            Thread dbThread = new Thread(() -> {
                try {
                    //Updating the house object with the new values 
                    house.setName(txtHSName.getText());
                    house.setAddress(txtHSAddress.getText());
                    house.setPhoneNumber(txtHSPhone.getText());
                    house.setDescription(txtHSDescr.getText());
                    house.setMaxOccupants(Integer.parseInt(txtHSOccu.getText()));
                    house.setBasePricePerNight(Double.parseDouble(txtHSPrice.getText().replace("$", "").trim()));
                    house.setCleaningFee(Double.parseDouble(txtHSCleanFee.getText().replace("$", "").trim()));
                    house.setNumberOfBedrooms(Integer.parseInt(txtHSBedrooms.getText()));
                    house.setHasGarage(rBtnGarageT.isSelected());
                    house.setHasBackyard(rBtnYardT.isSelected());

                    //String query to update the lodge and house tables
                    String query = "UPDATE Houses h JOIN Lodges l ON h.lodgeID = l.lodgeID SET "
                            + "l.name = ?, l.address = ?, l.phoneNumber = ?, l.description = ?, "
                            + "l.maxOccupants = ?, l.basePricePerNight = ?, h.numberOfBedrooms = ?, "
                            + "h.hasGarage = ?, h.cleaningFee = ?, h.hasBackyard = ? WHERE h.lodgeID = ?";

                    //Prepared statement for the update 
                    PreparedStatement psUpdate = con.prepareStatement(query);
                    psUpdate.setString(1, house.getName());
                    psUpdate.setString(2, house.getAddress());
                    psUpdate.setString(3, house.getPhoneNumber());
                    psUpdate.setString(4, house.getDescription());
                    psUpdate.setInt(5, house.getMaxOccupants());
                    psUpdate.setDouble(6, house.getBasePricePerNight());
                    psUpdate.setInt(7, house.getNumberOfBedrooms());
                    psUpdate.setBoolean(8, house.isHasGarage());
                    psUpdate.setDouble(9, house.getCleaningFee());
                    psUpdate.setBoolean(10, house.isHasBackyard());
                    psUpdate.setString(11, Integer.toString(house.getLodgeID()));

                    int rowsAffected = psUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("House updated successfully.");
                    } else {
                        System.out.println("Error updating House.");
                    }
                    listLodging();
                } catch (SQLException ex) {
                    System.out.println("Update error when updating the House. " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            dbThread.start();
        } else {
            //Updating the house object with the new values 
            house.setName(txtHSName.getText());
            house.setAddress(txtHSAddress.getText());
            house.setPhoneNumber(txtHSPhone.getText());
            house.setDescription(txtHSDescr.getText());
            house.setMaxOccupants(Integer.parseInt(txtHSOccu.getText()));
            house.setBasePricePerNight(Double.parseDouble(txtHSPrice.getText()));
            house.setCleaningFee(Double.parseDouble(txtHSCleanFee.getText()));
            house.setNumberOfBedrooms(Integer.parseInt(txtHSBedrooms.getText()));
            house.setHasGarage(rBtnGarageT.isSelected());
            house.setHasBackyard(rBtnYardT.isSelected());
            save();
            listLodging();
        }
    }

//Method to remove a lodge from the database along with the images related to it
    private void removeLodge(int index, int choice) {
        CustomerView customerLoad = new CustomerView();//Creating an instance of CustomerView class to update the list of lodges
        if (tryConnection()) {//Trying to open the connection, if true it removes the lodges from the database 
            if (choice == 1) {//If choice = Hotel 
                if (index >= 0 && index < lodgingHotel.size()) {
                    //Creating an object of the hotel the user selects
                    Hotel hotel = lodgingHotel.get(index);
                    String lodgeID = Integer.toString(hotel.getLodgeID());
                    //Using a Thread lambda to create a thread to execute the queries to delete lodges
                    Thread dbThread = new Thread(() -> {
                        try {
                            //String query to delete images from the LodgeImages table
                            String deleteImgQuery = "DELETE FROM LodgeImages WHERE LodgeID = ?";
                            PreparedStatement psImg = con.prepareStatement(deleteImgQuery);
                            psImg.setString(1, lodgeID);
                            psImg.executeUpdate();

                            //String querys to deactivate hotels from tables in database
                            String deactivateLodgeQuery = "UPDATE Lodges SET isActive = FALSE WHERE lodgeID = ?";
                            PreparedStatement psDeactivateLodge = con.prepareStatement(deactivateLodgeQuery);
                            psDeactivateLodge.setString(1, lodgeID);

                            int rowsAffected = psDeactivateLodge.executeUpdate();

                            if (rowsAffected > 0) {
                                htSuccess.setVisible(true);
                            } else {
                                System.out.println("Error deleting hotel.");
                            }
                            populateArrayListLodges();
                            listLodging();
                            customerLoad.viewLodges(0);//Updating the list of lodges
                        } catch (SQLException ex) {
                            System.out.println("Error deleting hotel. " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    });
                    dbThread.start();
                }
            } else if (choice == 2) {
                if (index >= 0 && index < lodgingHouse.size()) {
                    House house = lodgingHouse.get(index);
                    String lodgeID = Integer.toString(house.getLodgeID());
                    //Using a Thread lambda to create a thread to execute the queries for the lodge information 
                    Thread dbThread = new Thread(() -> {
                        try {
                            //String query to delete images from the LodgeImages table
                            String deleteImgQuery = "DELETE FROM LodgeImages WHERE LodgeID = ?";
                            PreparedStatement psImg = con.prepareStatement(deleteImgQuery);
                            psImg.setString(1, lodgeID);
                            psImg.executeUpdate();

                            //String query to deactivate houses from tables in the database
                            String deactivateLodgeQuery = "UPDATE Lodges SET isActive = FALSE WHERE lodgeID = ?";

                            PreparedStatement psDeactivateLodge = con.prepareStatement(deactivateLodgeQuery);

                            psDeactivateLodge.setString(1, lodgeID);

                            int rowsAffected = psDeactivateLodge.executeUpdate();//Executing the update 
                            //psDeleteLodge.executeUpdate();

                            if (rowsAffected > 0) {
                                hsSuccess.setVisible(true);
                            } else {
                                System.out.println("Error deleting House.");
                            }
                            populateArrayListLodges();
                            listLodging();
                            customerLoad.viewLodges(0);//Updating the list of lodges 
                        } catch (SQLException ex) {
                            System.out.println("Error deleting house" + ex.getMessage());
                            ex.printStackTrace();
                        }
                    });
                    dbThread.start();
                }
            }
        } else {
            //If the connection fails, it deletes the lodge from the textfiles
            if (choice == 1) {//If choice = Hotel 
                if (index >= 0 && index < lodgingHotel.size()) {
                    lodgingHotel.remove(index);
                    System.out.println("Hotel removed successfully!");
                    save();
                    listLodging();
                    btnCancelHotel.setEnabled(false);
                    btnRemoveHT.setEnabled(false);
                    htSuccess.setVisible(true);
                }
            } else if (choice == 2) {//If choice = House
                System.out.println("Enter the index of the House to remove:");
                if (index >= 0 && index < lodgingHouse.size()) {
                    lodgingHouse.remove(index);
                    System.out.println("House removed successfully!");
                    save();
                    listLodging();
                    //Clearing components
                    btnCancelHouse.setEnabled(false);
                    btnRemoveHS.setEnabled(false);
                    hsSuccess.setVisible(true);
                } else {
                }
            }
        }
    }

    //Method to list all of the lodging options in the JLists
    private void listLodging() {
        //Creating a DefaultListModel to hold and manage the data that is displayed by the JList
        lodgingModelHotel = new DefaultListModel();
        lodgingModelHouse = new DefaultListModel();
        lodgingModelHotel.clear();
        lodgingModelHouse.clear();

        //For loops to display lodgings 
        for (int i = 0; i < lodgingHotel.size(); i++) {
            //Adding the hotel options to the DefaultListModel
            lodgingModelHotel.addElement(EmployeeView.lodgingHotel.get(i).getName());
        }

        for (int i = 0; i < lodgingHouse.size(); i++) {
            //Adding the house options to the DefaultListModel
            lodgingModelHouse.addElement(EmployeeView.lodgingHouse.get(i).getName());
        }
        lstHotels.setModel(lodgingModelHotel);//Adding the DefaultListModel with hotel options to the JList
        lstHouses.setModel(lodgingModelHouse);//Adding the DefaultListModel with house options to the JList
    }

    @Override
//Method that saves lodging houses or hotels
    void save() {
        try {
            BufferedWriter bwHotel;
            try (BufferedWriter bwHouse = new BufferedWriter(new FileWriter("lodgingHouse.txt"))) {
                bwHotel = new BufferedWriter(new FileWriter("lodgingHotel.txt"));
                for (int i = 0; i < lodgingHouse.size(); i++) {
                    //Writing the string representation of the house lodges directly to the file
                    House house = lodgingHouse.get(i);
                    bwHouse.write(house.getName() + "|" + house.getAddress() + "|" + house.getPhoneNumber() + "|" + house.getDescription() + "|" + house.getMaxOccupants() + "|"
                            + house.getBasePricePerNight() + "|" + house.getNumberOfBedrooms() + "|" + house.isHasGarage() + "|" + house.getCleaningFee() + "|" + house.isHasBackyard());//Calling the toFileString to convert data to a string
                    bwHouse.newLine();
                }
                for (int i = 0; i < lodgingHotel.size(); i++) {
                    //Writing the string representation of the hotel lodges directly to the file
                    Hotel hotel = lodgingHotel.get(i);
                    bwHotel.write(hotel.getName() + "|" + hotel.getAddress() + "|" + hotel.getPhoneNumber() + "|" + hotel.getDescription() + "|" + hotel.getMaxOccupants() + "|"
                            + hotel.getBasePricePerNight() + "|" + hotel.isHasFreeBreakfast() + "|" + hotel.getVacancies() + "|"
                            + hotel.isValetParking() + "|" + hotel.getParkingFee() + "|" + hotel.isHasPool() + "|" + hotel.isPetsAllowed() + "|" + hotel.getRoomServicePrice());//Calling the toFileString to convert data to a string
                    bwHotel.newLine();
                }
            }
            bwHotel.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    //Method that loads lodging hotels and houses
    void load() {
        lodgingHouse.clear();
        lodgingHotel.clear();

        try {
            BufferedReader brHouse = new BufferedReader(new FileReader("lodgingHouse.txt"));
            BufferedReader brHotel = new BufferedReader(new FileReader("lodgingHotel.txt"));
            String line;
            while ((line = brHouse.readLine()) != null) {
                //Splitting the input line by | to access individual fields
                String[] hDetails = line.split("\\|");
                int lodgeID = Integer.parseInt(hDetails[0]);
                String name = hDetails[1];
                String address = hDetails[2];
                String phoneNumber = hDetails[3];
                String description = hDetails[4];
                int maxOccupants = Integer.parseInt(hDetails[5]);
                double basePricePerNight = Double.parseDouble(hDetails[6]);
                int numberOfBedrooms = Integer.parseInt(hDetails[7]);
                boolean hasGarage = Boolean.parseBoolean(hDetails[8]);
                double cleaningFee = Double.parseDouble(hDetails[9]);
                boolean hasBackyard = Boolean.parseBoolean(hDetails[10]);
                House house = new House(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight, numberOfBedrooms, hasGarage, cleaningFee, hasBackyard);
                lodgingHouse.add(house);

            }
            while ((line = brHotel.readLine()) != null) {
                //Splitting the input line by | to access individual fields
                String[] hDetails = line.split("\\|");
                int lodgeID = Integer.parseInt(hDetails[0]);
                String name = hDetails[1];
                String address = hDetails[2];
                String phoneNumber = hDetails[3];
                String description = hDetails[4];
                int maxOccupants = Integer.parseInt(hDetails[5]);
                double basePricePerNight = Double.parseDouble(hDetails[6]);
                boolean hasFreeBreakfast = Boolean.parseBoolean(hDetails[7]);
                int vacancies = Integer.parseInt(hDetails[8]);
                boolean valetParking = Boolean.parseBoolean(hDetails[9]);
                double parkingFee = Double.parseDouble(hDetails[10]);
                boolean hasPool = Boolean.parseBoolean(hDetails[11]);
                boolean petsAllowed = Boolean.parseBoolean(hDetails[12]);
                double roomServicePrice = Double.parseDouble(hDetails[13]);
                Hotel hotel = new Hotel(lodgeID, name, address, phoneNumber, description, maxOccupants, basePricePerNight, hasFreeBreakfast, vacancies, valetParking, parkingFee, hasPool,
                        petsAllowed, roomServicePrice);
                lodgingHotel.add(hotel);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
