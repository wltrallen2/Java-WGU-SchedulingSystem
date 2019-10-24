/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
