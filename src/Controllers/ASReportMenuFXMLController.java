/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Controllers;

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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASReportMenuFXMLController implements Initializable {
    
    @FXML ComboBox reportsComboBox;
    @FXML Button viewButton;
    @FXML Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML void segueToNewScene(ActionEvent event) throws IOException {
        String filename = "/Views/ASReportFXML.fxml";
        if(event.getSource().equals(backButton)) {
            filename = "/Views/ASScheduleFXML.fxml";
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        
        if(event.getSource().equals(viewButton)) {
            ASReportMenuFXMLController controller = loader.getController();
            // TODO: Set report.
        }
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
