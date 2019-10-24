/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allenschedulingsystem;

import Model.AppointmentDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASScheduleFXMLController implements Initializable {
    
    @FXML private Button viewCustomerDBButton;

    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        
    @FXML private void exitButtonPressed (ActionEvent event) {
        Platform.exit();
    }
    
    @FXML private void segueToNewScene (ActionEvent event) throws IOException {
        String filename = "";
        if(event.getSource().equals(viewCustomerDBButton)) {
            filename = "ASCustomerFXML.fxml";
        }
        
        Parent parent = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
}
