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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASAddAppointmentFXMLController implements Initializable {
    
    @FXML private Label sceneTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private ComboBox customerComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField contactTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField urlTextField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startHourComboBox;
    @FXML private ComboBox startMinuteComboBox;
    @FXML private ComboBox startPeriodComboBox;
    @FXML private ComboBox endHourComboBox;
    @FXML private ComboBox endMinuteComboBox;
    @FXML private ComboBox endPeriodComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private void segueToNewScene(ActionEvent event) throws IOException {
        //TODO: Continue implementation here.
    }
}
