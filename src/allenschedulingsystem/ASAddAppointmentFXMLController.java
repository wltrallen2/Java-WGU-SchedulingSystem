/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package allenschedulingsystem;

import Model.Appointment;
import Model.AppointmentDatabase;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASAddAppointmentFXMLController implements Initializable {
    
    @FXML private Label sceneTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private ComboBox<Customer> customerComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField contactTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField urlTextField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Integer> startHourComboBox;
    @FXML private ComboBox<Integer> startMinuteComboBox;
    @FXML private ComboBox<String> startPeriodComboBox;
    @FXML private ComboBox<Integer> endHourComboBox;
    @FXML private ComboBox<Integer> endMinuteComboBox;
    @FXML private ComboBox<String> endPeriodComboBox;
    
    private Appointment appointment;
    //TODO: NEXT ===>>> Continue with javadoc from this point.

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCustomerComboBoxItems();
        setTimeComboBoxItems();
    }
    
    /**
     * Sets the appointment parameter for this scene, populating the user fields
     * with the appropriate information from the Appointment instance and changing
     * the text in the title label of the scene to "View/Modify Appointment Details."
     * 
     * @param appointment an Appointment instance representing the appointment
     * to be viewed or modified
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        
        customerComboBox.setValue(appointment.getCustomer());
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        locationTextField.setText(appointment.getLocation());
        contactTextField.setText(appointment.getContact());
        typeTextField.setText(appointment.getType());
        urlTextField.setText(appointment.getURL());
        
        Timestamp start = appointment.getStart();
        LocalDateTime date = start.toLocalDateTime();
        datePicker.setValue(LocalDate.from(date));
        
        // Retrieve the time from the start variable, set AM or PM in the
        // period variable based on the start hour, and modify the start hour
        // to represent a 12-hour clock.
        int startHour = start.toLocalDateTime().getHour();
        String startPeriod = (startHour <= 12 ? "AM" : "PM" );
        startHour = (startHour <= 12 ? startHour : startHour - 12);
        
        startHourComboBox.setValue(startHour);
        startMinuteComboBox.setValue(start.toLocalDateTime().getMinute());
        startPeriodComboBox.setValue(startPeriod);
        
        // Repeat the hour and period process for the end time.
        Timestamp end = appointment.getEnd();
        int endHour = end.toLocalDateTime().getHour();
        String endPeriod = (endHour <= 12 ? "AM" : "PM");
        endHour = (endHour <= 12 ? endHour : endHour - 12);
        
        endHourComboBox.setValue(endHour);
        endMinuteComboBox.setValue(end.toLocalDateTime().getMinute());
        endPeriodComboBox.setValue(endPeriod);
        
        sceneTitleLabel.setText("View/Modify Appointment Details");
    }
    
    /**
     * Segues back to the ASScheduleFXML scene, calling the saveAppointmentInfo
     * method if the user clicked the saveButton instead of the cancelButton.
     * 
     * @param event the ActionEvent that triggers the segue, in this case, the
     * user clicking either the save or cancel buttons.
     * @throws IOException 
     */
    @FXML private void segueToNewScene(ActionEvent event) throws IOException {
        if(event.getSource().equals(saveButton)) {
            saveAppointmentInfo();
        }
        
        String filename = "ASScheduleFXML.fxml";
        Parent parent = FXMLLoader.load(getClass().getResource(filename));
    
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Populates the customer combo box with the names of the customers in the
     * customer table of the database. The method also sorts the customers
     * alphabetically by first name.
     */
    private void setCustomerComboBoxItems() {
        Collection<Customer> customers = AppointmentDatabase.getInstance().getCustomers().values();
        ObservableList<Customer> obsCustomers = FXCollections.observableArrayList(customers);
        obsCustomers.sort((Customer c1, Customer c2) -> c1.getCustomerName().compareTo(c2.getCustomerName()));
        customerComboBox.setItems(FXCollections.observableArrayList(obsCustomers));
        
        customerComboBox.setCellFactory(cell -> {
            return new ListCell<Customer>(){
                @Override
                public void updateItem(Customer customer, boolean empty) {
                    super.updateItem(customer, empty);
                    
                    if(customer == null || empty) {
                        setText("");
                    } else {
                        setText(customer.getCustomerName());
                    }
                }
            };
        });
    }
    
    /**
     * Sets the user choices for all of the time combo boxes. The user can choose
     * integers 1 through 12 for the hours and 0 through 55 in increments of 5
     * for the minutes.
     */
    private void setTimeComboBoxItems() {
        Integer[] hours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        
        Integer[] minutes = new Integer[12];
        for(int i = 0; i < 12; i++) {
            minutes[i] = i * 5;
        }
        
        String[] period = { "AM", "PM" };
        
        startHourComboBox.setItems(FXCollections.observableArrayList(hours));
        endHourComboBox.setItems(FXCollections.observableArrayList(hours));
        
        Callback<ListView<Integer>, ListCell<Integer>> cellFactory = cell -> {
           return new ListCell<Integer>() {
               @Override
               public void updateItem(Integer item, boolean empty) {
                   super.updateItem(item, empty);
                   
                   if(item == null || empty) {
                       setText("");
                   } else {
                       // Add the leading zero (0) to the minute value if it
                       // is less than 10.
                       setText(item < 10 ? "0" + item : "" + item);
                   }
               }
           };
        };
        
        startMinuteComboBox.setCellFactory(cellFactory);
        startMinuteComboBox.setButtonCell(cellFactory.call(null));
        
        endMinuteComboBox.setCellFactory(cellFactory);
        endMinuteComboBox.setButtonCell(cellFactory.call(null));

        
        startMinuteComboBox.setItems(FXCollections.observableArrayList(minutes));
        endMinuteComboBox.setItems(FXCollections.observableArrayList(minutes));
        
        startPeriodComboBox.setItems(FXCollections.observableArrayList(period));
        endPeriodComboBox.setItems(FXCollections.observableArrayList(period));
    }
    
    /**
     * If the appointment variable is null, saves the new appointment to the database
     * and creates a new instance of an Appointment, adding it to the appointments
     * HashMap in the AppointmentDatabase Singleton instance; else, modifies the
     * existing appointment in both the database and the AppointmentDatabase Singleton
     * instance.
     */
    private void saveAppointmentInfo() {
        HashMap<String, Object> data = getHashMapForAppointmentCreation();
        
        if(appointment == null) {
            AppointmentDatabase.getInstance().createNewAppointmentInDb(data);
        } else {
            Customer newCustomer = 
                    AppointmentDatabase.getInstance().getCustomerWithId
                    ((Integer)data.get(Appointment.CUSTOMER_ID));
            
            if(!appointment.getCustomer().equals(newCustomer)) {
                appointment.setCustomer(newCustomer); }
            if(!appointment.getTitle().equals(data.get(Appointment.TITLE))) {
                appointment.setTitle(((String)data.get(Appointment.TITLE))); }
            if(!appointment.getDescription().equals(data.get(Appointment.DESCRIPTION))) {
                appointment.setDescription(((String)data.get(Appointment.DESCRIPTION))); }
            if(!appointment.getLocation().equals(data.get(Appointment.LOCATION))) {
                appointment.setLocation(((String)data.get(Appointment.LOCATION))); }
            if(!appointment.getContact().equals(data.get(Appointment.CONTACT))) {
                appointment.setContact(((String)data.get(Appointment.CONTACT))); }
            if(!appointment.getType().equals(data.get(Appointment.TYPE))) {
                appointment.setType(((String)data.get(Appointment.TYPE))); }
            if(!appointment.getURL().equals(data.get(Appointment.URL))) {
                appointment.setURL(((String)data.get(Appointment.URL))); }
            if(!appointment.getStart().equals(data.get(Appointment.START_TIME))) {
                appointment.setStart(((Timestamp)data.get(Appointment.START_TIME))); }
            if(!appointment.getEnd().equals(data.get(Appointment.END_TIME))) {
                appointment.setEnd(((Timestamp)data.get(Appointment.END_TIME))); }
        }
    }
    
    /**
     * 
     * @param hour an int representing the hour
     * @param minute an int representing the minute
     * @param period a String representing the period (AM or PM)
     * @return a String representing the time in 12-hour format.
     */
    private String getAdjustedTimeString(int hour, int minute, String period) {
        hour = period.equals("AM") ? hour : (hour + 12);
        String adjHour = (hour < 10 ? "0" + hour : "" + hour);
        
        String adjMinute = (minute < 10 ? "0" + minute : "" + minute);
        return adjHour + ":" + adjMinute + ":00";
    }
    
    /**
     * Calculates and returns the timestamp code for the given date and time.
     * 
     * @param date a LocalDate instance representing the date to be encoded
     * @param hour an int representing the hour to be encoded
     * @param minute an int representing the minute to be encoded
     * @param period a String representing the period to be encoded
     * @return a String representing the timestamp to be stored in an RDBMS
     */
    private String getTimestampCode(LocalDate date, int hour, int minute, String period) {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth()
                + " " + getAdjustedTimeString(hour, minute, period);
    }
    
    /**
     * Creates and returns a HashMap that maps the user's entered data to a set of
     * key-value pairs that match the columns in the appointment table of the database.
     * The HashMap will consist of Strings mapped to Objects where the Strings are
     * the names of the columns in the appointment table and the Objects are the
     * values to be stored in those columns.
     * 
     * @return a HashMap of String-Object pairs representing the column-value
     * pairs for this row in the appointment table of the database.
     */
    private HashMap<String, Object> getHashMapForAppointmentCreation() {
        HashMap<String, Object> data = new HashMap<>();
        
        Customer customer = customerComboBox.getSelectionModel().getSelectedItem();
        data.put(Appointment.CUSTOMER_ID, customer.getCustomerId());
        
        String userName = AppointmentDatabase.getInstance().getUserName();
        data.put(Appointment.USER_ID, AppointmentDatabase.getInstance().getUserId());
        
        data.put(Appointment.TITLE, titleTextField.getText());
        data.put(Appointment.DESCRIPTION, descriptionTextField.getText());
        data.put(Appointment.LOCATION, locationTextField.getText());
        data.put(Appointment.CONTACT, contactTextField.getText());
        data.put(Appointment.TYPE, typeTextField.getText());
        data.put(Appointment.URL, urlTextField.getText());
        
        LocalDate date = datePicker.getValue();
        int startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        int startMinute = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String startPeriod = startPeriodComboBox.getSelectionModel().getSelectedItem();
        int endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        int endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();
        String endPeriod = endPeriodComboBox.getSelectionModel().getSelectedItem();
                
        String startTimeCode = getTimestampCode(date, startHour, startMinute, startPeriod);
        String endTimeCode = getTimestampCode(date, endHour, endMinute, endPeriod);
        
        data.put(Appointment.START_TIME, Timestamp.valueOf(startTimeCode));
        data.put(Appointment.END_TIME, Timestamp.valueOf(endTimeCode));

        return data;
    }
}
