/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Controllers;

import Utilities.ASForm;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class ASReportMenuFXMLController implements Initializable {
    
    @FXML ComboBox<ASForm> reportsComboBox;
    @FXML Button viewReportButton;
    @FXML Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setReportsComboBoxItems();
    }    
    
    private void setReportsComboBoxItems() {
        ObservableList<ASForm> forms = 
                FXCollections.observableArrayList(ASForm.TYPE_SUMMARY,
                        ASForm.CONSULTANT_SCHEDULE, ASForm.CUSTOMER_PHONES);
        SortedList<ASForm> sortedForms = forms.sorted((f1, f2) ->
                f1.getTitle().compareTo(f2.getTitle()));
        
        Callback<ListView<ASForm>, ListCell<ASForm>> cellFactory = (cell -> {
            return new ListCell<ASForm>() {
                @Override
                protected void updateItem(ASForm item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(item == null || empty) {
                        setText("");
                    } else {
                        setText(item.getTitle());
                    }
                }
            };
        });
        
        reportsComboBox.setCellFactory(cellFactory);
        reportsComboBox.setButtonCell(cellFactory.call(null));
        
        reportsComboBox.setItems(sortedForms);
        reportsComboBox.getSelectionModel().select(0);
    }
    
    @FXML void segueToNewScene(ActionEvent event) throws IOException {
        String filename = "/Views/ASReportFXML.fxml";
        if(event.getSource().equals(backButton)) {
            filename = "/Views/ASScheduleFXML.fxml";
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        
        System.out.println(event.getSource().toString());
        if(event.getSource().equals(viewReportButton)) {
            ASReportFXMLController controller = loader.getController();
            controller.setFormType(reportsComboBox.getSelectionModel().getSelectedItem());
        }
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
