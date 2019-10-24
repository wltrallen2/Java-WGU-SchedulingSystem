/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides a database connection to the uCertify mySQL Database
 * provided as the basis for this project.
 * 
 * @author walterallen
 */
public class DBConnection {
    
    private static final String DB_NAME = "U05MMg";
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + DB_NAME;
    private static final String USERNAME = "U05MMg";
    private static final String PASSWORD = "53688543383";
    
    public static Connection conn; 
    
    /**
     * Creates a database connection.
     * 
     * @throws SQLException 
     */
    public static void makeConnection() throws SQLException {
        conn = (Connection) DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
    
    /**
     * Closes the database connection.
     * 
     * @throws SQLException 
     */
    public static void closeConnection() throws SQLException {
        conn.close();
    }
}
