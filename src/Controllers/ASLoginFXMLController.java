/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Controllers;

import Model.AppointmentDatabase;
import Utilities.DBQuery;
import Utilities.StringUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    
    private class InvalidUserException extends Exception {
        public InvalidUserException(String message) {
            super(message);
        }
    }
    
    @FXML private Label errorLabel;
    
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    
    @FXML private Button button;
    
    /**
     * Initializes the controller class. Sets the errorLabel to invisible,
     * hiding the error message from the user.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.visibleProperty().set(false);
    }    
        
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
        
        try {
            verifyUser(username, password);
            AppointmentDatabase.init(username);
            loadScheduleFXMLSceneWithUserName(event, username);
        } catch(InvalidUserException ex) {
            System.out.println(ex);
            
            checkErrorLabelLanguage();
            errorLabel.visibleProperty().set(true);
            userNameTextField.setText("");
            passwordField.setText("");
        }
    }
    
    private void verifyUser(String username, String password) throws InvalidUserException {
        if(DBQuery.fetchUserId(username, password) == -1) {
            logUser(username, false);
            throw new InvalidUserException("Invalid username/password combination.");
        }
        
        logUser(username, true);
    }
    
    private void logUser(String username, boolean success) {
        String logLine = StringUtil.rpad(username, 30, '-');
        logLine += StringUtil.lpad(Timestamp.valueOf(LocalDateTime.now()).toString(), 25, '-');
        logLine += (success ? "  SUCCESS" : "  FAIL");
        
        File file = new File("log.txt");
        
        try {
            boolean newFile = false;
            if(!file.exists()) {
                file.createNewFile();
                newFile = true;
            }
            
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            if(newFile) {
                out.append("USER-TIMESTAMP LOG");
                out.newLine();
                
                out.append(StringUtil.rpad("", 55, '-'));
                out.newLine();
                out.newLine();
                
                out.append(StringUtil.rpad("USERNAME", 30, ' '));
                out.append(StringUtil.lpad("TIMESTAMP", 25, ' '));
                out.newLine();
                out.newLine();
            }
            
            out.append(logLine);
            out.newLine();
            
            out.close();
        } catch(IOException ex) {
            System.out.println("IOException in ASLoginFXMLController.logUser(String, boolean).");
            System.out.println(ex);
        }
    }

    /**
     * Loads the ASScheduleFXML scene into the current stage.
     * 
     * @param event
     * @throws IOException 
     */
    private void loadScheduleFXMLSceneWithUserName(ActionEvent event, String username) 
            throws IOException, SQLException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Views/ASScheduleFXML.fxml"));
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