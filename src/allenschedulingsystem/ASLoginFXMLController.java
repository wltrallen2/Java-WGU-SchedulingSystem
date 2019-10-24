/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allenschedulingsystem;

import Model.AppointmentDatabase;
import Model.DBQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author walterallen
 */
public class ASLoginFXMLController implements Initializable {
    
    @FXML private Label errorLabel;
    
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    
    @FXML private Button button;
    
    /**
     * nextButtonPressed checks the user name and password provided in the 
     * appropriate text fields against the MySQL connected database. If the
     * user name and password combination is found, the method changes the
     * scene to the Scheduling System's main FXML scene. Otherwise, it
     * displays an error message to the user alerting them that their log in
     * information was incorrect. Additionally, the method will call a helper
     * method that will check the user country and will translate the error
     * message to French if the user is in France.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML private void nextButtonPressed (ActionEvent event) throws IOException, SQLException {
        String username = userNameTextField.getText();
        String password = passwordField.getText();
        
        if (DBQuery.fetchUserId(username, password) != -1) {
            AppointmentDatabase.init(username);
            loadScheduleFXMLSceneWithUserName(event, username);
        } else {
            checkErrorLabelLanguage();
            errorLabel.visibleProperty().set(true);
            userNameTextField.setText("");
            passwordField.setText("");
        }
    }

    /**
     * Initializes the controller class. Sets the errorLabel to invisible,
     * hiding the error message from the user.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.visibleProperty().set(false);
    }    
    
    /**
     * Loads the ASScheduleFXML scene into the current stage.
     * 
     * @param event
     * @throws IOException 
     */
    private void loadScheduleFXMLSceneWithUserName(ActionEvent event, String username) 
            throws IOException, SQLException {
        Parent parent = FXMLLoader.load(getClass().getResource("ASScheduleFXML.fxml"));
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
        
    /**
     * Checks the current country for the user, and changes the error message
     * to French if the user is in France, or to English otherwise.
     */
    private void checkErrorLabelLanguage() {
        Locale currentLocale = Locale.getDefault();
        if(currentLocale.getCountry().equals("FR")) {
            errorLabel.setText("Le nom d'utilisateur et le mot de passe que " +
                                "vous avez entrés ne correspondent pas à nos " +
                                "enregistrements. Veuillez réessayer.");
        } else {
            errorLabel.setText("The user name and password you entered does " +
                    "not match our records. Please try again.");
        }
    }
}