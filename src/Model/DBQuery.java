/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains static methods that query the company database to return
 * the requested information.
 * 
 * @author walterallen
 */
public class DBQuery {
    /**
     * Queries the company database to see if there is a matching user with the
     * given password. The method will return an int representing the userId
     * value in the database if the username and password combination is found.
     * Else, the method will return a -1.
     * 
     * @param username a String representing the username value in the database
     * @param password a String representing the user password value in the database
     * @return an int representing the userId or -1 if not found
     * @throws SQLException 
     */
    public static int fetchUserId(String username, String password) throws SQLException {
        int userId = -1;
        
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
        prepstmt.setString(1, username);
        prepstmt.setString(2, password);
        
        ResultSet rs = prepstmt.executeQuery();
        if(rs.next()) {
            userId = rs.getInt("userId");
        }
        
        return userId;
    }
    
    /**
     * Updates a single string value in one matching record in the database.
     * The record is identified using a WHERE clause in the SQL query, in which
     * the value in the primaryKeyColumnName matches the passed primaryKeyValue.
     * 
     * @param table - a string representing the name of the table to update
     * @param columnName - a string representing the name of the column to update
     * @param value - a string representing the value that is to be placed in
     * the given column
     * @param primaryKeyColumnName - a string representing the name of the
     * primary key column in the database
     * @param primaryKeyValue - an int representing the id of the row to be
     * updated
     * 
     * @return true if the update was successful, false if the update was unsuccessful
     * 
     * @throws SQLException 
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, String value,
            String primaryKeyColumnName, int primaryKeyValue) throws SQLException {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "='?'" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
        prepstmt.setString(1, value);
        
        if(prepstmt.executeUpdate() > 0) { success = true; }
        return success;
    }
    
        /**
     * Updates a single int value in one matching record in the database.
     * The record is identified using a WHERE clause in the SQL query, in which
     * the value in the primaryKeyColumnName matches the passed primaryKeyValue.
     * 
     * @param table - a string representing the name of the table to update
     * @param columnName - a string representing the name of the column to update
     * @param value - an int representing the value that is to be placed in
     * the given column
     * @param primaryKeyColumnName - a string representing the name of the
     * primary key column in the database
     * @param primaryKeyValue - an int representing the id of the row to be
     * updated
     * 
     * @return true if the update was successful, false if the update was unsuccessful
     * 
     * @throws SQLException 
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, int value,
            String primaryKeyColumnName, int primaryKeyValue) throws SQLException {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "='?'" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
        prepstmt.setInt(1, value);
        
        if(prepstmt.executeUpdate() > 0) { success = true; }
        return success;
    }
    
        /**
     * Updates a single timestamp value in one matching record in the database.
     * The record is identified using a WHERE clause in the SQL query, in which
     * the value in the primaryKeyColumnName matches the passed primaryKeyValue.
     * 
     * @param table - a string representing the name of the table to update
     * @param columnName - a string representing the name of the column to update
     * @param value - a Timestamp object representing the value that is to be placed in
     * the given column
     * @param primaryKeyColumnName - a string representing the name of the
     * primary key column in the database
     * @param primaryKeyValue - an int representing the id of the row to be
     * updated
     * 
     * @return true if the update was successful, false if the update was unsuccessful
     * 
     * @throws SQLException 
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, Timestamp value,
            String primaryKeyColumnName, int primaryKeyValue) throws SQLException {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "='?'" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
        prepstmt.setTimestamp(1, value);
        
        if(prepstmt.executeUpdate() > 0) { success = true; }
        return success;
    }
    
    public static int insertRowIntoDatabase(String table, String[] columnNames,
            String[] values, String keyColumn) throws SQLException {
        int genKey = -1;
        
        String query = "INSERT INTO " + table + " (" + implodeStrings(columnNames, ", ") 
                + ") VALUES (" + implodeStrings(values, ", ") + ")";
        Statement stmt = DBConnection.conn.createStatement();
        
        String[] keyColumns = { keyColumn };
        stmt.executeUpdate(query, keyColumns);
        ResultSet genKeys = stmt.getGeneratedKeys();
        if(genKeys.next()) {
            genKey = genKeys.getInt(1);
        }
        
        return genKey;
    }

    public static ResultSet getResultSetForFilteredOrderedSelectStatement(
            String[] columnNames, String tableName, String sqlFilter,
            String orderByColumnName) throws SQLException {
        String query = "SELECT " + implodeStrings(columnNames, ",") + " FROM " + tableName;
        
        if(!sqlFilter.equals("")) {
            query += " WHERE " + sqlFilter;
        }
        
        if(!orderByColumnName.equals("")) {
            query += " ORDER BY " + orderByColumnName;
        }
        
        return executeStatementFromQuery(query);
    }
    
    public static ResultSet getResultSetForOrderedSelectStatement(String[] columnNames,
            String tableName, String orderByColumnName) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames,
                tableName, "", orderByColumnName);
    }
    
    public static ResultSet getResultSetForFilteredSelectStatement(String[] columnNames,
            String tableName, String sqlFilter) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames,
                tableName, sqlFilter, "");
    }
    
    public static ResultSet getResultSetForSelectStatement(String[] columnNames,
            String tableName) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", "");
    }
    
    public static ResultSet getResultSetOfAllOrderedRows(String tableName, String orderByColumn)
            throws SQLException {
        String[] columnNames = { "*" };
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", orderByColumn);
    }
    
    public static HashMap<String, Object> getArrayFromResultSetRow(ResultSet rs) throws SQLException, ClassNotFoundException {
        HashMap<String, Object> rowData = new HashMap<>();
        if(rs.next()) {
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String colName = rs.getMetaData().getColumnLabel(i);
                String colType = rs.getMetaData().getColumnClassName(i);
                rowData.put(colName, rs.getObject(i, Class.forName(colType)));
            }
        }
        
        return rowData;
    }
                    
    private static String implodeStrings(String[] strings, String delimiter) {
        String result = "";
        for(int i = 0; i < strings.length; i++) {
            result += strings[i];
            if(i != strings.length - 1) {
                result += delimiter + " ";
            }
        }
        
        return result;
    }
    
    public static Timestamp getLastUpdateTimestampForTable(String tableName) throws SQLException {
        Timestamp ts = null;
        
        String query = "SELECT MAX(lastupdate) FROM " + tableName;
        ResultSet rs = executeStatementFromQuery(query);
        if(rs.next()) {
            ts = rs.getTimestamp(0);
        }
        
        return ts;
    }
    
    private static ResultSet executeStatementFromQuery(String query) throws SQLException {
        Statement stmt = DBConnection.conn.createStatement();
        return stmt.executeQuery(query);
    }
}
