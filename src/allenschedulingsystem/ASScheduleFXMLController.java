/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package allenschedulingsystem;

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
