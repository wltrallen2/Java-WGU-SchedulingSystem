/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Controllers;

import Model.Appointment;
import Model.AppointmentDatabase;
import Model.Customer;
import Utilities.MissingInformationException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    
    /***************************************************************************
     * PRIVATE INNER CLASS, EXCEPTION SUBCLASS
     **************************************************************************/

    /**
     * A subclass of Exception that represents that the user has entered
     * appointment information that is invalid.
     */
    private class InvalidAppointmentException extends Exception {
        /**
         * Constructor class
         * 
         * @param message a String representing the information to be returned
         * to the method that throws the InvalidAppointmentException
         */
        public InvalidAppointmentException(String message) {
            super(message);
        }
    }

    /***************************************************************************
     * CONSTANTS
     **************************************************************************/
    private final int START_HOUR_OF_BUSINESS_DAY = 8; // 8 a.m.
    private final int END_HOUR_OF_BUSINESS_DAY = 17; // 5 p.m.
    
    /***************************************************************************
     * PARAMETERS
     **************************************************************************/
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

    /***************************************************************************
     * INITIALIZER
     **************************************************************************/

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. 
     * @param rb - The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCustomerComboBoxItems();
        setTimeComboBoxItems();
    }
    
    /***************************************************************************
     * PUBLIC SETTER
     **************************************************************************/
    
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
        
        Timestamp start = getLocalTimestampFromGMTTimestamp(appointment.getStart());
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
        Timestamp end = getLocalTimestampFromGMTTimestamp(appointment.getEnd());
        int endHour = end.toLocalDateTime().getHour();
        String endPeriod = (endHour <= 12 ? "AM" : "PM");
        endHour = (endHour <= 12 ? endHour : endHour - 12);
        
        endHourComboBox.setValue(endHour);
        endMinuteComboBox.setValue(end.toLocalDateTime().getMinute());
        endPeriodComboBox.setValue(endPeriod);
        
        sceneTitleLabel.setText("View/Modify Appointment Details");
    }

    /***************************************************************************
     * EVENT HANDLERS
     **************************************************************************/
    
    /**
     * Segues back to the ASScheduleFXML scene, calling the saveAppointmentInfo
     * method if the user clicked the saveButton instead of the cancelButton.
     * 
     * @param event the ActionEvent that triggers the segue, in this case, the
     * user clicking either the save or cancel buttons.
     * @throws IOException 
     */
    @FXML private void segueToNewScene(ActionEvent event) throws IOException {
        try {
            if(event.getSource().equals(saveButton)) {
                saveAppointmentInfo();
            }

            String filename = "/Views/ASScheduleFXML.fxml";
            Parent parent = FXMLLoader.load(getClass().getResource(filename));

            Scene scene = new Scene(parent);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (InvalidAppointmentException ex) {
            System.out.println(ex);
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        } catch (MissingInformationException ex) {
            System.out.println(ex);
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        }
    }

    /***************************************************************************
     * PRIVATE HELPER METHODS - INITIALIZATION
     **************************************************************************/
    
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

    /***************************************************************************
     * PRIVATE HELPER METHODS - OTHER
     **************************************************************************/
    
    /**
     * Retrieves the user-entered information for the date, hour, minute, and period
     * for the end time and converts them into a Timestamp instance.
     * 
     * @return a Timestamp representing the end time for the appointment.
     */
    private Timestamp getEndTimestamp() {
        LocalDate date = datePicker.getValue();
        int endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        int endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();
        String endPeriod = endPeriodComboBox.getSelectionModel().getSelectedItem();
        
        return getTimestampCode(date, endHour, endMinute, endPeriod);
    }
                    
    /**
     * Adjusts a Timestamp in the user's local time zone to Greenwich Mean Time.
     * This assumes that the user has entered their appointment time using their 
     * own local time zone.
     * 
     * @param localTimeCode a Timestamp instance representing the data that the user entered
     * @return a Timestamp with the date and time adjusted to Greenwich Mean Time.
     */
    private Timestamp getGMTTimestampFromLocalTimestamp (Timestamp localTime) {
        ZonedDateTime zdtLocal = ZonedDateTime.of(localTime.toLocalDateTime(), ZoneId.systemDefault());
        ZonedDateTime zdt = zdtLocal.withZoneSameInstant(ZoneId.of("GMT"));
        return Timestamp.valueOf(zdt.toLocalDateTime());
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
    private HashMap<String, Object> getHashMapForAppointmentCreation()
            throws InvalidAppointmentException, MissingInformationException {
        
        try {
            verifyFormIsComplete();
            validateAppointment();
            
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

            data.put(Appointment.START_TIME, getGMTTimestampFromLocalTimestamp(getStartTimestamp()));
            data.put(Appointment.END_TIME, getGMTTimestampFromLocalTimestamp(getEndTimestamp()));

            return data;
        } catch (InvalidAppointmentException ex) {
            throw ex;
        }
    }

    /**
     * Adjusts a Timestamp from Greenwich Mean Time to the user's local time zone
     * as read from the system.
     * 
     * @param gmtTime a Timestamp instance representing the time in GMT
     * @return 
     */
    private Timestamp getLocalTimestampFromGMTTimestamp(Timestamp gmtTime) {
        ZonedDateTime zdt = ZonedDateTime.of(gmtTime.toLocalDateTime(), ZoneId.of("GMT"));
        ZonedDateTime zdtLocal = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(zdtLocal.toLocalDateTime());
    }
 
    /**
     * Retrieves the user-entered information from the date, hour, minute, and period
     * for the start time and converts them into a Timestamp instance.
     * 
     * @return a Timestamp representing the start time for the appointment.
     */
    private Timestamp getStartTimestamp() {
        LocalDate date = datePicker.getValue();
        int startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        int startMinute = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String startPeriod = startPeriodComboBox.getSelectionModel().getSelectedItem();

        return getTimestampCode(date, startHour, startMinute, startPeriod);
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
    private Timestamp getTimestampCode(LocalDate date, int hour, int minute, String period) {
        String timestampCode = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth()
                + " " + toTwelveHourTimeString(hour, minute, period);
        return Timestamp.valueOf(timestampCode);
    }

    /**
     * If the appointment variable is null, saves the new appointment to the database
     * and creates a new instance of an Appointment, adding it to the appointments
     * HashMap in the AppointmentDatabase Singleton instance; else, modifies the
     * existing appointment in both the database and the AppointmentDatabase Singleton
     * instance.
     */
    private void saveAppointmentInfo() 
            throws InvalidAppointmentException, MissingInformationException {
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
    private String toTwelveHourTimeString(int hour, int minute, String period) {
        if(hour == 12) { hour -= 12; }
        hour = period.equals("AM") ? hour : (hour + 12);
        String adjHour = (hour < 10 ? "0" + hour : "" + hour);
        
        String adjMinute = (minute < 10 ? "0" + minute : "" + minute);
        return adjHour + ":" + adjMinute + ":00";
    }
    
    /**
     * Validates the appointment based on the following conditions:<br>
     * <ul>
     * <li>Appointment must fall on a business day, not Saturday or Sunday.</li>
     * <li>Appointment must start and end within office hours (set as 8 a.m.
     * and 5 p.m. using class constants.</li>
     * <li>Appointment's end time must be set after the start time.</li>
     * <li>Appointment must not overlap with any other appointment.</li>
     * </ul>
     * If any of these conditions are violated, the method throws an InvalidAppointmentException
     * with a corresponding message informing the user which requirement has
     * been violated.
     * 
     * @throws Controllers.ASAddAppointmentFXMLController.InvalidAppointmentException 
     */
    private void validateAppointment() throws InvalidAppointmentException {
        String message = "";
        
        Timestamp startTimestamp = getStartTimestamp();
        Timestamp endTimestamp = getEndTimestamp();
        
        DayOfWeek apptDay = startTimestamp.toLocalDateTime().getDayOfWeek();
        int startHour = startTimestamp.toLocalDateTime().getHour();
        int endHour = endTimestamp.toLocalDateTime().getHour();
        
        // Appointment falls on a business day.
        if(apptDay.equals(DayOfWeek.SATURDAY) || apptDay.equals(DayOfWeek.SUNDAY)) {
            message += "The office is closed on Saturdays and Sundays. ";
        }
        
        // Appointment starts outside of office hours.
        if(startHour < START_HOUR_OF_BUSINESS_DAY) {
            message += "The office does not open until 8 a.m. ";
        }
        
        // Appointment ends outside of office hours.
        if((endHour == END_HOUR_OF_BUSINESS_DAY
                && endTimestamp.toLocalDateTime().getMinute() != 0)
                || endHour > END_HOUR_OF_BUSINESS_DAY) {
            message += "The office closes at 5 p.m. ";
        }
        
        // Start time falls after end time.
        if(startTimestamp.after(endTimestamp)) {
            message += "The end of the appointment is scheduled after the start time. ";
        }
        
        // Appointment overlaps with other appointment(s).
        Collection<Appointment> appointments = AppointmentDatabase.getInstance().getAppointments().values();
        FilteredList<Appointment> filteredAppointments 
                = new FilteredList(FXCollections.observableArrayList(appointments));
        filteredAppointments = filteredAppointments.filtered(a -> {                    
                    Timestamp thisStart = getLocalTimestampFromGMTTimestamp(a.getStart());
                    Timestamp thisEnd = getLocalTimestampFromGMTTimestamp(a.getEnd());
                    
                    if(startTimestamp.after(thisStart) && startTimestamp.before(thisEnd)
                            || endTimestamp.after(thisStart) && endTimestamp.before(thisEnd)
                            || startTimestamp.before(thisStart) && endTimestamp.after(thisEnd)) {
                        return true;
                    }
                                       
                    return false;
                });
        
        if(!filteredAppointments.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
            String apptList = "";
            for(Appointment a : filteredAppointments) {
                Timestamp startTime = getLocalTimestampFromGMTTimestamp(a.getStart());
                Timestamp endTime = getLocalTimestampFromGMTTimestamp(a.getEnd());
                apptList += a.getTitle() + ", " 
                        + startTime.toLocalDateTime().format(formatter) + "-"
                        + endTime.toLocalDateTime().format(formatter) + "\n";
            }
            message += "As scheduled, the appointment overlaps the following already"
                    + " scheduled appointments:\n\n";
            message += apptList + "\n";
        }
        
        if(!message.equals("")) {
            message += "Please schedule a valid appointment.";
            throw new InvalidAppointmentException(message);
        }
    }
    
    /**
     * Verifies that all required information for the Appointment instance has
     * been completed by the user. The required information includes the customer
     * name, the title, the location, the description, the date, and the start
     * and end times.
     * 
     * @throws MissingInformationException The exception includes information about
     * which required element is missing.
     */
    private void verifyFormIsComplete() throws MissingInformationException {
        String missingInformation = "";
        if(customerComboBox.getValue() == null) {
            missingInformation += "Please select a customer name.\n";
        }
        
        if(titleTextField.getText().equals("")) {
            missingInformation += "Please enter a title for the appointment.\n";
        }
        
        if(locationTextField.getText().equals("")) {
            missingInformation += "Please enter a valid location for the appointment.\n";
        }
        
        if(descriptionTextField.getText().equals("")) {
            missingInformation += "Please enter a valid description for the appointment.\n";
        }
        
        if(datePicker.getValue() == null) {
            missingInformation += "Please select a date for the appointment.\n";
        }
        
        if(startHourComboBox.getValue() == null
                || startMinuteComboBox.getValue() == null
                || startPeriodComboBox.getValue() == null) {
            missingInformation += "Please select a valid start time.\n";
        }
        
        if(endHourComboBox.getValue() == null
                || endMinuteComboBox.getValue() == null
                || endPeriodComboBox.getValue() == null) {
            missingInformation += "Please select a valid end time.";
        }
        
        if(!missingInformation.equals("")) {
            throw new MissingInformationException(missingInformation);
        }
    }
}
