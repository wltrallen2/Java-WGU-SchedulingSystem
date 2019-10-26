/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package allenschedulingsystem;

import Model.AppointmentDatabase;
import Model.City;
import Model.Country;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class that allows the user to add a customer or modify a
 * customer's information.
 *
 * @author walterallen
 */
public class ASAddCustomerFXMLController implements Initializable {
    
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private TextField nameTextField;
    @FXML private TextField address1TextField;
    @FXML private TextField address2TextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneTextField;
    
    @FXML private ComboBox cityComboBox;
    @FXML private ComboBox countryComboBox;
    
    /**
     * Initializes the controller class, setting the selection itesm for the
     * city and country combo boxes.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxItemsForCities();
        setComboBoxItemsForCountries();
    }
    
    /**
     * Segues to the Customer Database scene (ASCustomerFXML) when the user clicks
     * the "Save" or "Cancel" buttons.
     * 
     * @param event the ActionEvent that triggers the method; in this case, the
     * user clicking the "Save" or "Cancel" button.
     * @throws IOException 
     */    
    @FXML private void segueToNewScene (ActionEvent event) throws IOException {
        String filename = "ASCustomerFXML.fxml";
        
        Parent parent = FXMLLoader.load(getClass().getResource(filename));
        if(event.getSource().equals(saveButton)) {
            saveCustomerInformation();
        }
        
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Saves the customer information to the database, either overwriting the
     * customer if the user chose to modify an existing customer, or adding the
     * customer if the user chose to add a new customer. The implementation calls
     * the saveCustomer method in the AppointmentDatabase class instance, which
     * verifies the uniqueness of the customer, address, city, and/or country.
     * Any instances which are not unique are not overwritten unless the information
     * has been altered in some way.
     */
    private void saveCustomerInformation() {
        String name = nameTextField.getText();
        String address1 = address1TextField.getText();
        String address2 = address2TextField.getText();
        City city = (City)cityComboBox.getValue();
        Country country = (Country)countryComboBox.getValue();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        
        // TODO: Finish implementation by calling saveCustomer in AppointmentDatabase.
    }
        
    /**
     * Set the selection items for the countries combo box, setting the cell factory
     * return values to the name of the country.
     */
    private void setComboBoxItemsForCountries() {
        try {
            HashMap<Integer, Country> countries;
            countries = AppointmentDatabase.getInstance().getCountries();
            
            Callback<ListView<Country>, ListCell<Country>> cellFactory = (list -> {
                return new ListCell<Country>() {
                    @Override
                    protected void updateItem(Country item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item != null ? item.getCountryName() : "");
                    }
                };
            });
            
            countryComboBox.getItems().setAll(countries.values());
            countryComboBox.setButtonCell(cellFactory.call(null));
            countryComboBox.setCellFactory(cellFactory);
        } catch(SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Sets the selection items for the city combo box, setting the return value
     * for the cell factory to the name of the city.
     */
    private void setComboBoxItemsForCities() {
        try {
            HashMap<Integer, City> cities;
            cities = AppointmentDatabase.getInstance().getCities();
            
            Callback<ListView<City>, ListCell<City>> cellFactory = (list -> {
                return new ListCell<City>() {
                    @Override
                    protected void updateItem(City item, boolean empty) {
                        super.updateItem(item, empty);                        
                        setText(item != null ? item.getCityName() : "");
                    }
                };
            });
            
            cityComboBox.getItems().setAll(cities.values());
            cityComboBox.setButtonCell(cellFactory.call(null));
            cityComboBox.setCellFactory(cellFactory);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}


