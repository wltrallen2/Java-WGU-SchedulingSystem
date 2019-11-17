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
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASScheduleFXMLController implements Initializable {
    
    @FXML private Button viewCustomerDBButton;
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;
    
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Timestamp> dateColumn;
    @FXML private TableColumn<Appointment, Timestamp> startColumn;
    @FXML private TableColumn<Appointment, Timestamp> endColumn;
    @FXML private TableColumn<Appointment, Customer> customerColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;

    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadAndPopulateAppointmentTable();
    }
    
    // TODO: NEXT ===>>> Accidentally wrote population as one method under populateCustomerColumn
    // Rewrite and decompose appropriately.
    
    // TODO: NEXT2 ===>>> Sorting of date/start/end columns
    
    // TODO: NEXT3 ===>>> Catch up on JavaDoc
    private void loadAndPopulateAppointmentTable() {
//        populateDateColumn();
//        populateStartColumn();
//        populateEndColumn();
        populateCustomerColumn();
//        populateLocationColumn();
//        populateDescriptionColumn();
    }
    
    private void populateCustomerColumn() {
        dateColumn.setCellValueFactory(
            new PropertyValueFactory<>("start"));
        startColumn.setCellValueFactory(
            new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(
            new PropertyValueFactory<>("end"));
        customerColumn.setCellValueFactory(
            new PropertyValueFactory<>("customer"));
        locationColumn.setCellValueFactory(
            new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(
            new PropertyValueFactory<>("description"));
        
        dateColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Timestamp>() {
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        setText(item.toLocalDateTime().format(formatter));
                    }
                }
            };
        });
        
        startColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Timestamp>() {
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                        setText(item.toLocalDateTime().format(formatter));
                    }
                }
            };
        });
        
        endColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Timestamp>() {
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                        setText(item.toLocalDateTime().format(formatter));
                    }
                }
            };
        });

        customerColumn.setCellFactory(column -> {
            return new TableCell<Appointment, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getCustomerName());
                    }
                }
            };
        });
                
        HashMap<Integer, Appointment> appointments = AppointmentDatabase.getInstance().getAppointments();
        ObservableList<Appointment> items = FXCollections.observableArrayList(appointments.values());
        appointmentTable.setItems(items);
    }
        
    /**
     * Exits the application when the exit button is pressed.
     * 
     * @param event the ActionEvent that triggers the method; in this case, the
     * user clicking the "Exit" button.
     */
    @FXML private void exitButtonPressed (ActionEvent event) {
        Platform.exit();
    }
    
    /**
     * Segues to a new fxml scene based on the source of the ActionEvent that has
     * been triggered. The method will currently segue to an instance of the
     * ASCustomerFXML scene when the user clicks the "View Customer Database" button.
     * 
     * @param event the ActionEvent that triggers the segue; in this case, the
     * user clicking the "View Customer Database" button.
     * @throws IOException 
     */
    @FXML private void segueToNewScene (ActionEvent event) throws IOException {
        // In the event that the user clicks the Modify button, but no appointment
        // has been selected in the table, then the method will return and no
        // segue will occur.
        if(event.getSource().equals(modifyButton)
                && appointmentTable.getSelectionModel().getSelectedItem() == null) {
            return;
        } else {
            String filename = "";
            if(event.getSource().equals(viewCustomerDBButton)) {
                filename = "ASCustomerFXML.fxml";
            } else {
                filename = "ASAddAppointmentFXML.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            if(event.getSource().equals(modifyButton)
                    && appointmentTable.getSelectionModel().getSelectedItem() != null) {
                    ASAddAppointmentFXMLController controller = loader.getController();
                    Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
                    controller.setAppointment(appointment);
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        
        return;
    }

    @FXML private void deleteAppointment(ActionEvent event) {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(appointment != null) {
            AppointmentDatabase.getInstance().removeApointment(appointment);
            loadAndPopulateAppointmentTable();
        }
    }
}
