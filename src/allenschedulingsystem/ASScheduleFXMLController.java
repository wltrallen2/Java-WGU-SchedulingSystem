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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASScheduleFXMLController implements Initializable {
    
    private enum ViewOption {
        ALL("View All Appointments"),
        THIS_WEEKS("View This Week's"),
        NEXT_WEEKS("View Next Week's"),
        THIS_MONTHS("View This Month's"),
        NEXT_MONTHS("View Next Month's");
        
        private String description;
        
        ViewOption(String desc) {
            this.description = desc;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private final ViewOption[] VIEW_BY_OPTIONS = { ViewOption.ALL,
                                                    ViewOption.THIS_WEEKS,
                                                    ViewOption.NEXT_WEEKS,
                                                    ViewOption.THIS_MONTHS,
                                                    ViewOption.NEXT_MONTHS };
    
    @FXML private Button viewCustomerDBButton;
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;
    
    @FXML private ComboBox<ViewOption> viewByComboBox;
    
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
    
    /**
     * Loads the appointments into the AppointmentDatabase Singleton instance
     * and populates the appointment table with the data from the start, end,
     * customer, location, and description columns.
     */
    private void loadAndPopulateAppointmentTable() {
        populateViewByComboBox();

        populateDateTimeColumns();
        populateCustomerColumn();
        populateOtherStringColumns();
        
        setAndFilterItems(null);        
    }
    
    /**
     * Sets the CellValueFactorys and the CellFactorys for the date,
     * start, and end columns in the appointment table. The method defines
     * how the date, start time, and end times will be displayed.
     */
    private void populateDateTimeColumns() {
        dateColumn.setCellValueFactory(
            new PropertyValueFactory<>("start"));
        startColumn.setCellValueFactory(
            new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(
            new PropertyValueFactory<>("end"));

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
    }
    
    /**
     * Sets the CellValueFactorys and the CellFactorys for the customer column,
     * defining that the column will display the customer name.
     */
    private void populateCustomerColumn() {
        customerColumn.setCellValueFactory(
            new PropertyValueFactory<>("customer"));        

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
    }
    
    /**
     * Sets the CellValueFactorys and the CellFactorys for the location and
     * the description columns in the appointment table.
     */
    private void populateOtherStringColumns() {
        locationColumn.setCellValueFactory(
            new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(
            new PropertyValueFactory<>("description"));
    }
    
    /**
     * Sets the items for the appointment table by calling on the AppointmentDatabase
     * Singleton Instance for a HashMap of Appointment instances, sorts these
     * instances based on the start DateTime, and then binds the sorting comparator
     * properties of the list of appointments and the appointment table in case
     * the user decides to sort by customer name or appointment location.
     */
    private FilteredList<Appointment> getItemsForAppointmentTable() {
        HashMap<Integer, Appointment> appointments = AppointmentDatabase.getInstance().getAppointments();
        ObservableList<Appointment> items = FXCollections.observableArrayList(appointments.values());
        
        items.sort((Appointment a1, Appointment a2) -> a1.getStart().compareTo(a2.getStart()));
        FilteredList<Appointment> filteredItems = new FilteredList<>(items);
        
        return filteredItems;
        }
    
    /**
     * Sets the items for the View By ComboBox utilizing the five values of the
     * ViewOption enum (NOW, THIS_WEEKS, NEXT_WEEKS, THIS_MONTHS, NEXT_MONTHS).
     * Additionally, the method ses the cell factory and the button cell to display
     * the readable description values of the ViewOption enums.
     */
    private void populateViewByComboBox() {
        Callback<ListView<ViewOption>, ListCell<ViewOption>> cellFactory = (cell -> {
           return new ListCell<ViewOption>() {
               @Override
               protected void updateItem(ViewOption v, boolean empty) {
                   super.updateItem(v, empty);
                   
                   if(v == null || empty) {
                       setText("");
                   } else {
                       setText(v.getDescription());
                   }
               }
           };
        });
                    
        viewByComboBox.setCellFactory(cellFactory);
        viewByComboBox.setButtonCell(cellFactory.call(null));
        
        viewByComboBox.setItems(FXCollections.observableArrayList(VIEW_BY_OPTIONS));
        viewByComboBox.setValue(ViewOption.ALL);
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
     * been triggered. The method will segue to an instance of the
     * ASCustomerFXML scene when the user clicks the "View Customer Database" button,
     * or will segue to the ASAddAppointmentFXML when the user clicks the "Add
     * Appointment" or "View/Modify Appointment Details" button.
     * 
     * @param event the ActionEvent that triggers the segue; in this case, the
     * user clicking the "View Customer Database" button, or the user clicking
     * either the "Add Appointment" or "View/Modify Appointment Details" button.
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

    /**
     * Deletes the selected appointment from both the appointment table in the
     * database and from the HashMap of Appointment instances in the Appointment
     * Database Singleton instance.
     * 
     * @param event the ActionEvent that triggered the method, in this case, the
     * user clicking the delete button.
     */
    @FXML private void deleteAppointment(ActionEvent event) {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(appointment != null) {
            AppointmentDatabase.getInstance().removeApointment(appointment);
            loadAndPopulateAppointmentTable();
        }
    }
    
    /**
     * Filters the appointments in the table based on the value that the user
     * has selected in the View By Combo Box.
     * 
     * @param event the ActionEvent that triggers the method, in this case, the
     * user clicking and choosing a different value in the viewByComboBox
     */
    @FXML private void setAndFilterItems(ActionEvent event) {
        FilteredList<Appointment> filteredItems = getItemsForAppointmentTable();
                
        Calendar targetCal = Calendar.getInstance();
        int targetWeek = targetCal.get(Calendar.WEEK_OF_YEAR);
        int targetMonth = targetCal.get(Calendar.MONTH);
        int targetYear = targetCal.get(Calendar.YEAR);
        
        Calendar cal = Calendar.getInstance();
        ViewOption v = viewByComboBox.getValue();
        if (v.equals(ViewOption.THIS_WEEKS)) {
            filteredItems = filteredItems.filtered(a -> {
                cal.setTime(a.getStart());
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                int year = cal.get(Calendar.YEAR);
                return targetYear == year && targetWeek == week;
                });
        } else if (v.equals(ViewOption.NEXT_WEEKS)) {
            filteredItems = filteredItems.filtered(a -> {
                cal.setTime(a.getStart());
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                int year = cal.get(Calendar.YEAR);
                return (targetYear == year && targetWeek + 1 == week)
                    || (targetYear + 1 == year && week == 1);
            });
        } else if (v.equals(ViewOption.THIS_MONTHS)) {
            filteredItems = filteredItems.filtered(a -> {
                cal.setTime(a.getStart());
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                return targetYear == year && targetMonth == month;
            });
        } else if (v.equals(ViewOption.NEXT_MONTHS)) {
            filteredItems = filteredItems.filtered(a -> {
                cal.setTime(a.getStart());
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                return (targetYear == year && targetMonth + 1 == month)
                        || (targetYear + 1 == year && month == 1);
            });
        }
        
        SortedList<Appointment> sortedItems = new SortedList<>(filteredItems);
        sortedItems.comparatorProperty().bind(appointmentTable.comparatorProperty());
        
        appointmentTable.setItems(sortedItems);
    }
}
