/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setComboBoxItemsForCountries();
        setComboBoxItemsForCities();
    }    
    
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

    private void saveCustomerInformation() {
        String name = nameTextField.getText();
        String address1 = address1TextField.getText();
        String address2 = address2TextField.getText();
        //City city = (City)cityComboBox.getSelectionModel().getSelectedItem();
        //Country country = (Country)countryComboBox.getSelectionModel().getSelectedItem();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        
        // TODO
    }
    
    private void setComboBoxItemsForCountries() {
                try {
            HashMap<Integer, Country> countries;
            countries = AppointmentDatabase.getInstance().getCountries();
            ObservableList<Country> countryList;
            countryList = FXCollections.observableArrayList(countries.values());
            countryList.sort(
                    (c1, c2) -> c1.getCountryName().compareTo(c2.getCountryName()));
                        
            Callback<ListView<Country>, ListCell<Country>> cellFactory = list -> {
                return new ListCell<Country>() {
                    @Override
                    protected void updateItem(Country item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : item.getCountryName());
                    }
                };
            };
            
            countryComboBox.setItems(countryList);
            countryComboBox.setCellFactory(cellFactory);
            //countryComboBox.setButtonCell(cellFactory.call(null));
            countryComboBox.setEditable(true);
            countryComboBox.setConverter(new CountryStringConverter());
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void setComboBoxItemsForCities() {
        try {
            HashMap<Integer, City> cities;
            cities = AppointmentDatabase.getInstance().getCities();
            
            Callback<ListView<City>, ListCell<City>> cellFactory = list -> {
                return new ListCell<City>() {
                    @Override
                    protected void updateItem(City item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : item.getCityName());
                    }
                };
            };
            
            cityComboBox.getItems().addAll(cities.values());
            cityComboBox.setCellFactory(cellFactory);
            cityComboBox.setEditable(true);
            //cityComboBox.setButtonCell(cellFactory.call(null));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    class CountryStringConverter extends StringConverter<Country> {

        @Override
        public String toString(Country object) {
            String name = "";
            if(object != null) { name = object.getCountryName(); }
            return name;
        }

        @Override
        public Country fromString(String string) {
            try {
                int rowNum = AppointmentDatabase.getInstance()
                        .rowExistsInDatabase(Country.TABLE_NAME, Country.COUNTRY_NAME, string);
                if(rowNum == -1) {
                    return Country.addCountryToDB(string);
                } else {
                    HashMap<Integer, Country> countries;
                    countries = AppointmentDatabase.getInstance().getCountries();
                    for(Country country : countries.values()) {
                        if(country.getCountryName().equals(string)) {
                            return country;
                        }
                    }
                }
            } catch(SQLException ex){
                System.out.println(ex);
            } catch (Exception ex) {
                System.out.println(ex);
            }

            return null;
        }
    }
}


