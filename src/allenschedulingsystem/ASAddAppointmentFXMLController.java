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
import javafx.collections.transformation.SortedList;
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
    //TODO: Continue with javadoc from this point.

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCustomerComboBoxItems();
        setTimeComboBoxItems();
    }
    
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
        
        int startHour = start.toLocalDateTime().getHour();
        String startPeriod = (startHour <= 12 ? "AM" : "PM" );
        startHour = (startHour <= 12 ? startHour : startHour - 12);
        
        startHourComboBox.setValue(startHour);
        startMinuteComboBox.setValue(start.toLocalDateTime().getMinute());
        startPeriodComboBox.setValue(startPeriod);
        
        Timestamp end = appointment.getEnd();
        int endHour = end.toLocalDateTime().getHour();
        String endPeriod = (endHour <= 12 ? "AM" : "PM");
        endHour = (endHour <= 12 ? endHour : endHour - 12);
        
        endHourComboBox.setValue(endHour);
        endMinuteComboBox.setValue(end.toLocalDateTime().getMinute());
        endPeriodComboBox.setValue(endPeriod);
        
        sceneTitleLabel.setText("View/Modify Appointment Details");
    }
    
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
    
    private String getAdjustedTimeString(int hour, int minute, String period) {
        hour = period.equals("AM") ? hour : (hour + 12);
        String adjHour = (hour < 10 ? "0" + hour : "" + hour);
        
        String adjMinute = (minute < 10 ? "0" + minute : "" + minute);
        return adjHour + ":" + adjMinute + ":00";
    }
    
    private String getTimestampCode(LocalDate date, int hour, int minute, String period) {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth()
                + " " + getAdjustedTimeString(hour, minute, period);
    }
    
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
