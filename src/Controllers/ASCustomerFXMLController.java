/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Controllers;

import Model.Address;
import Model.AppointmentDatabase;
import Model.City;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
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
public class ASCustomerFXMLController implements Initializable {
    
    /***************************************************************************
     * PARAMETERS
     **************************************************************************/
    @FXML private Button returnButton;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button deleteCustomerButton;
    
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, Address> customerLocationColumn;
    @FXML private TableColumn<Customer, Address> customerPhoneColumn;
    
    /***************************************************************************
     * INITIALIZER
     **************************************************************************/
    /**
     * Initializes the controller class.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataAndPopulateTable();
    }    
    
    /***************************************************************************
     * EVENT HANDLERS
     **************************************************************************/

    /**
     * Deletes the customer that is currently selected in the customerTable
     * from both the AppointmentDatabase customers HashMap and from the customer
     * table in the database itself. Note that by calling the removeCustomer method
     * from the AppointmentDatabase Singleton instance, unused addresses, cities,
     * and countries will also be deleted from the database.
     * 
     * @param event the ActionEvent that triggers this method, in this case, the
     * user clicking on the Delete Selected Customer button.
     */
    @FXML private void deleteCustomer(ActionEvent event) {
        Customer customerToDelete = customerTable.getSelectionModel().getSelectedItem();
        if(customerToDelete != null) {
            AppointmentDatabase.getInstance().removeCustomer(customerToDelete);
            loadDataAndPopulateTable();
        }
    }
      
    /**
     * Segues to a new fxml scene based on the source of the ActionEvent that has
     * been triggered. The method will currently segue to an instance of the
     * ASScheduleFXML scene when the user clicks the "Return" button, and to an
     * instance of the ASAddCustomerFXML scene when the user clicks the "Add
     * Customer" or "Modify Selected Customer" button. When the user clicks the
     * "Modify Selected Customer" button, the customer parameter of the ASAddCustomerFXML
     * scene is set and the form fields are populated with that customer's information.
     * 
     * @param event the ActionEvent that triggers the segue; in this case, the
     * user clicking the "Return" or the "Add Customer" button.
     * @throws IOException 
     */
    @FXML private void segueToNewScene (ActionEvent event) throws IOException {
        String filename = "";
        
        Object source = event.getSource();
        if(source.equals(returnButton)) {
            filename = "/Views/ASScheduleFXML.fxml";
        } else if(source.equals(addCustomerButton) || source.equals(modifyCustomerButton)) {
            filename = "/Views/ASAddCustomerFXML.fxml";
        }
        
        Customer customerToModify = customerTable.getSelectionModel().getSelectedItem();
        if(!source.equals(modifyCustomerButton) || customerToModify != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            if(event.getSource().equals(modifyCustomerButton)
                    && loader.getController() instanceof ASAddCustomerFXMLController) {
                ASAddCustomerFXMLController controller = loader.getController();
                controller.setCustomer(customerToModify);
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /***************************************************************************
     * PRIVATE HELPER METHODS - INITIALIZATION
     **************************************************************************/

    /**
     * Loads the data for the customer table, sets the cellValueFactorys that
     * will populate the table, and sets the items for the table as the list
     * of customers in the AppointmentDatabase Singleton instance.
     */
    private void loadDataAndPopulateTable() {
        customerNameColumn.setCellValueFactory(
            new PropertyValueFactory<>("customerName"));
        customerLocationColumn.setCellValueFactory(
            new PropertyValueFactory<>("address"));
        customerPhoneColumn.setCellValueFactory(
            new PropertyValueFactory<>("address"));
        
        customerLocationColumn.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                protected void updateItem(Address item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        String city = item.getCity().getCityName();
                        String country = item.getCity().getCountry().getCountryName();
                        setText(city + ", " + country);
                    }
                }
            };
        });
        
        customerLocationColumn.setComparator((Address a1, Address a2) -> {
            City city1 = a1.getCity();
            City city2 = a2.getCity();
            String c1 = city1.getCityName() + city1.getCountry().getCountryName();
            String c2 = city2.getCityName() + city2.getCountry().getCountryName();
            
            return c1.compareTo(c2);
        });
        
        customerPhoneColumn.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                protected void updateItem(Address item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getPhone());
                    }
                }
            };
        });
        
        HashMap<Integer, Customer> customers = AppointmentDatabase.getInstance().getCustomers();
        ObservableList<Customer> obsItems = FXCollections.observableArrayList(customers.values());
        obsItems.sort((Customer c1, Customer c2) -> c1.getCustomerName().compareTo(c2.getCustomerName()));
        SortedList<Customer> items = new SortedList<>(obsItems);
        items.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(items);
        
    }

}
