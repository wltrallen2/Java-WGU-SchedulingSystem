/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package allenschedulingsystem;

import Model.DBConnection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author walterallen
 */
public class AllenSchedulingSystem extends Application {
    
    /**
     * Makes a connection to the database using the DBConnection static class,
     * and then loads the initial FXML GUI, which is a login screen requesting the
     * user name and password to the company database.
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        DBConnection.makeConnection();

        Parent root = FXMLLoader.load(getClass().getResource("ASLoginFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Closes the database connection with a call to the static DBConnection
     * class.
     * 
     * @throws SQLException 
     */
    @Override
    public void stop() throws SQLException {
        DBConnection.closeConnection();
    }

    /**
     * Initializes the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
