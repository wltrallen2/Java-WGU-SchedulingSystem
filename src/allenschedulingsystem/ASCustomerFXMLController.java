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
public class ASCustomerFXMLController implements Initializable {
    
    @FXML private Button returnButton;
    @FXML private Button addCustomerButton;
    
        /**
     * Initializes the controller class.
     * 
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Segues to a new fxml scene based on the source of the ActionEvent that has
     * been triggered. The method will currently segue to an instance of the
     * ASScheduleFXML scene when the user clicks the "Return" button, and to an
     * instance of the ASAddCustomerFXML scene when the user clicks the "Add
     * Customer" button.
     * 
     * @param event the ActionEvent that triggers the segue; in this case, the
     * user clicking the "Return" or the "Add Customer" button.
     * @throws IOException 
     */
    @FXML private void segueToNewScene (ActionEvent event) throws IOException {
        String filename = "";
        
        Object source = event.getSource();
        if(source.equals(returnButton)) {
            filename = "ASScheduleFXML.fxml";
        } else if(source.equals(addCustomerButton)) {
            filename = "ASAddCustomerFXML.fxml";
        }
        
        Parent parent = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
