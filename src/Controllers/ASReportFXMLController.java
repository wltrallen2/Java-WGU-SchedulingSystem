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
import Utilities.ASForm;
import Utilities.StringUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASReportFXMLController implements Initializable {

    /***************************************************************************
     * PARAMETERS
     **************************************************************************/
    @FXML Label reportTitleLabel;
    @FXML Button backButton;
    @FXML TextArea textArea;

    /***************************************************************************
     * INITIALIZER
     **************************************************************************/
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    /***************************************************************************
     * SETTER
     **************************************************************************/
    /**
     * Sets the title of the report scene and calls the method that runs
     * the specified report.
     * 
     * @param type an instance of the ASForm enum that represents the type of
     * report to run.
     */
    public void setFormType(ASForm type) {
        reportTitleLabel.setText(type.getTitle());
        
        switch (type) {
            case TYPE_SUMMARY : runTypeSummary(); break;
            case CONSULTANT_SCHEDULE : runConsultantSchedule(); break;
            case CUSTOMER_PHONES : runCustomerPhones(); break;
            default : runNoReportSelected();
        }
    }

    /***************************************************************************
     * EVENT HANDER
     **************************************************************************/
 
    /**
     * Segues back to the previous scene, in this case, the ASReportMenuFXMLController.
     * 
     * @param event the ActionEvent that triggered the segue, in this case, the user
     * hitting the "Back" button.
     * @throws IOException 
     */
    @FXML void segueToNewScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Views/ASReportMenuFXML.fxml"));
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /***************************************************************************
     * PRIVATE HELPER METHODS - REPORTS
     **************************************************************************/
    
    /**
     * Runs a report that lists the user's schedule. The report lists only future
     * appointments.
     */
    private void runConsultantSchedule() {
        ObservableList<Appointment> appointments = FXCollections
                .observableArrayList(AppointmentDatabase.getInstance().getAppointments().values());
        FilteredList<Appointment> filteredAppts = new FilteredList<>(appointments).filtered(a -> {
            return a.getUserId() == AppointmentDatabase.getInstance().getUserId()
                    && a.getStart().after(Timestamp.valueOf(LocalDateTime.now()));
        });
        
        SortedList<Appointment> sortedAppts = new SortedList<>(filteredAppts).sorted((a1, a2) -> {
            return a1.getStart().compareTo(a2.getStart());
        });
        
        String reportText = "FUTURE APPOINTMENTS FOR USER: " 
                + AppointmentDatabase.getInstance().getUserName() + "\n\n";
        for(Appointment a : sortedAppts) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
            ZonedDateTime start = ZonedDateTime.of(a.getStart().toLocalDateTime(), ZoneId.of("GMT"))
                    .withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(a.getEnd().toLocalDateTime(), ZoneId.of("GMT"))
                    .withZoneSameInstant(ZoneId.systemDefault());
            System.out.println(a.getStart() + ".........." + start);
            reportText += dateFormat.format(start) + " ("
                    + timeFormat.format(start) 
                    + "-" + timeFormat.format(end.toLocalDateTime()) + ")\n"
                    + a.getTitle() + "\n"
                    + "Customer: " + a.getCustomer().getCustomerName() + "\n"
                    + "Phone: " + a.getCustomer().getAddress().getPhone() + "\n"
                    + "Description: " + a.getDescription() + "\n"
                    + "Type: " + a.getType() + "     Location:" + a.getLocation() + "\n"
                    + "Contact: " + a.getContact() + "\n\n";
        }
        
        textArea.setText(reportText);
    } 
    
    /**
     * Runs a report that lists all customers and their phone numbers.
     */
    private void runCustomerPhones() {
        SortedList<Customer> customers = FXCollections
                .observableArrayList(AppointmentDatabase.getInstance().getCustomers().values())
                .sorted((c1, c2) -> {
                    return c1.getCustomerName().toUpperCase()
                            .compareTo(c2.getCustomerName().toUpperCase());
                });
        
        final int FIRST_COL_PAD = 49;
        final int SECOND_COL_PAD = 22;
        String reportText = StringUtil.rpad("Customer Name", FIRST_COL_PAD, ' ');
        reportText += StringUtil.lpad("Phone Number", SECOND_COL_PAD, ' ') + "\n";
        reportText += StringUtil.rpad("", FIRST_COL_PAD + SECOND_COL_PAD, '-') + "\n";
        
        for(Customer c : customers) {
            reportText += StringUtil.rpad(c.getCustomerName(), FIRST_COL_PAD, '.');
            reportText += StringUtil.lpad(c.getAddress().getPhone(), SECOND_COL_PAD, '.');
            reportText += "\n";
        }
        
        textArea.setText(reportText);
    }
    
    /**
     * Prints the text "No Report Selected" to the textArea.
     */
    private void runNoReportSelected() {
        textArea.setText("No report selected.");
    }
    
    /**
     * Runs a report that lists the types of meetings and how many of each type
     */
    private void runTypeSummary() {
        Collection<Appointment> appointments = AppointmentDatabase.getInstance().getAppointments().values();
        HashMap<String, Integer> typeMap = new HashMap<>();
        
        for(Appointment a : appointments) {
            String type = a.getType();
            int n;
            if(!typeMap.keySet().contains(type)) {
                n = 1;
            } else {
                n = typeMap.get(type) + 1;
            }
            
            typeMap.put(type, n);
        }
        
        ObservableList<String> types = FXCollections.observableArrayList(typeMap.keySet());
        SortedList<String> sortedItems = new SortedList<>(types);
        sortedItems = sortedItems.sorted((s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()));
        
        final int FIRST_COL_PAD = 49;
        final int SECOND_COL_PAD = 22;
        String reportText = StringUtil.rpad("Appointment Type", FIRST_COL_PAD, ' ') + "Number of Appointments\n";
        reportText += StringUtil.rpad("", FIRST_COL_PAD + SECOND_COL_PAD, '-') + "\n";
        for(String type : sortedItems) {
            reportText += StringUtil.rpad(type, FIRST_COL_PAD, '.');
            reportText += StringUtil.lpad(typeMap.get(type) + "", SECOND_COL_PAD, '.');
            reportText += "\n";
        }

        textArea.setText(reportText);
    }
}
