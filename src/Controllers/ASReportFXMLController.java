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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASReportFXMLController implements Initializable {

    @FXML Label reportTitleLabel;
    @FXML Button backButton;
    @FXML TextArea textArea;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML void segueToNewScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Views/ASReportMenuFXML.fxml"));
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}
