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
import Model.Country;
import Model.Customer;
import Utilities.MissingInformationException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class that allows the user to add a customer or modify a
 * customer's information.
 *
 * @author walterallen
 */
public class ASAddCustomerFXMLController implements Initializable {
    
    @FXML private Label sceneTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private TextField nameTextField;
    @FXML private TextField address1TextField;
    @FXML private TextField address2TextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneTextField;
    
    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> countryComboBox;
    
    @FXML private Customer customer;
    
    /**
     * Initializes the controller class, setting the selection items for the
     * city and country combo boxes.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxItemsForCities();
        setComboBoxItemsForCountries();
        //TODO: Set ActionListener on comboboxes to autocomplete as user types.
    }
    
    /**
     * Sets the customer parameter with a Customer instance representing the
     * information to be pre-loaded into the form. Additionally, this method
     * pre-loads the data into the form and changes the scene label from
     * "Add New Customer" to "Modify Customer Record".
     * 
     * @param customer a Customer instance
     */
    public void setCustomer(Customer customer) {
        sceneTitleLabel.setText("Modify Customer Record");
        
        this.customer = customer;
        nameTextField.setText(customer.getCustomerName());
        
        Address address = customer.getAddress();
        address1TextField.setText(address.getAddressLine1());
        address2TextField.setText(address.getAddressLine2());
        postalCodeTextField.setText(address.getPostalCode());
        phoneTextField.setText(address.getPhone());
        
        City city = address.getCity();
        cityComboBox.getSelectionModel().select(city.getCityName());
        
        Country country = city.getCountry();
        countryComboBox.getSelectionModel().select(country.getCountryName());
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
        try {
            if(event.getSource().equals(saveButton)) {
                saveCustomerInformation();
            }

            String filename = "/Views/ASCustomerFXML.fxml";
            Parent parent = FXMLLoader.load(getClass().getResource(filename));

            Scene scene = new Scene(parent);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch(MissingInformationException ex) {
            System.out.println(ex);
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        }
    }
 
    /** Retrieves the information in the form and saves it to a HashMap, mapping
     * String identifiers to the String values. The identifiers used are the static
     * variables: Customer.CUSTOMER_NAME, Address.ADDRESS_LINE1, Address.ADDRESS_LINE2,
     * Address.POSTAL_CODE, Address.PHONE, City.CITY_NAME, and Country.COUNTRY_NAME.
     * 
     * @return a HashMap mapping String identifiers to String values.
     */
    private HashMap<String, String> getFormInformation() {
        HashMap<String, String> info = new HashMap<>();
        
        info.put(Customer.CUSTOMER_NAME, nameTextField.getText());
        info.put(Address.ADDRESS_LINE1, address1TextField.getText());
        info.put(Address.ADDRESS_LINE2, address2TextField.getText());
        info.put(Address.POSTAL_CODE, postalCodeTextField.getText());
        info.put(Address.PHONE, phoneTextField.getText());
        info.put(City.CITY_NAME, cityComboBox.getSelectionModel().getSelectedItem());
        info.put(Country.COUNTRY_NAME, countryComboBox.getSelectionModel().getSelectedItem());
        
        return info;
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
    private void saveCustomerInformation() throws MissingInformationException {
        verifyFormIsComplete();
        
        HashMap<String, String> info = getFormInformation();
        
        Country country = AppointmentDatabase.getInstance()
                .getCountryFromDB(info.get(Country.COUNTRY_NAME));
        City city = AppointmentDatabase.getInstance()
                .getCityFromDB(info.get(City.CITY_NAME), country);
        Address address = AppointmentDatabase.getInstance()
                .getAddressFromDb(info.get(Address.ADDRESS_LINE1),info.get(Address.ADDRESS_LINE2),
                        city, info.get(Address.POSTAL_CODE), info.get(Address.PHONE));
        
        if(customer == null) {
            customer = AppointmentDatabase.getInstance()
                    .getCustomerFromDb(info.get(Customer.CUSTOMER_NAME), address, true);
        } else {
            HashMap<String, Object> data = new HashMap<>();
            data.put(Customer.CUSTOMER_ID, customer.getCustomerId());
            data.put(Customer.CUSTOMER_NAME, info.get(Customer.CUSTOMER_NAME));
            data.put(Customer.ACTIVE, true);
            data.put(Customer.ADDRESS_ID, address.getAddressId());
            data.put(Customer.CREATE_DATE, customer.getCreateDate());
            data.put(Customer.CREATED_BY, customer.getCreatedBy());
            data.put(Customer.LAST_UPDATE, new Timestamp(new Date().getTime()));
            data.put(Customer.LAST_UPDATE_BY, AppointmentDatabase.getInstance().getUserName());
            
            try {
                Customer newCustomer = Customer.createCustomerInstanceFromHashMap(data);
                AppointmentDatabase.getInstance().updateCustomerInformation(customer, newCustomer);
            } catch(Exception ex) {
                System.out.println("Exception in ASAddCustomerFXMLController in saveCustomerInformation method.");
                System.out.println(ex);
            }
        }
    }
    
    private void verifyFormIsComplete() throws MissingInformationException {
        String missingInfo = "";
        
        if(nameTextField.getText().equals("")) {
            missingInfo += "Please enter a valid customer name.\n";
        }
        
        if(address1TextField.getText().equals("")) {
            missingInfo += "Please enter a valid address.\n";
        }
        
        if(cityComboBox.getSelectionModel().getSelectedItem() == null
                || cityComboBox.getValue().equals("")) {
            missingInfo += "Please choose or enter a valid city.\n";
        }
        
        if(countryComboBox.getSelectionModel().getSelectedItem() == null
                || countryComboBox.getValue().equals("")) {
            missingInfo += "Please choose or enter a valid country.\n";
        }
        
        if(postalCodeTextField.getText().equals("")) {
            missingInfo += "Please enter a valid postal code.\n";
        }
        
        if(phoneTextField.getText().equals("")) {
            missingInfo += "Please enter a valid phone number.\n";
        }
        
        if(!missingInfo.equals("")) {
            throw new MissingInformationException(missingInfo);
        }
    }
            
    /**
     * Set the selection items for the countries combo box, using the unique set
     * of country names in the Country table.
     */
    private void setComboBoxItemsForCountries() {
        Collection<Country> countries = AppointmentDatabase.getInstance().getCountries().values();
        TreeSet<String> countryNames = new TreeSet<>();
        for(Country c : countries) {
            countryNames.add(c.getCountryName());
        }
        
        countryComboBox.setItems(FXCollections.observableArrayList(countryNames));
    }
    
    /**
     * Sets the selection items for the city combo box, setting the return value
     * for the cell factory to the name of the city.
     */
    private void setComboBoxItemsForCities() {
        Collection<City> cities = AppointmentDatabase.getInstance().getCities().values();
        TreeSet<String> cityNames = new TreeSet<>();
        for(City c : cities) {
            cityNames.add(c.getCityName());
        }
        
        cityComboBox.setItems(FXCollections.observableArrayList(cityNames));
    }
}


