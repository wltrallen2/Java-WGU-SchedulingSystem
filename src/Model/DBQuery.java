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
    
    /**
     * Checks to see if a record exists in the database based on a set of 
     * string-to-string pairs where the first string is the column name and the
     * second string is the value expected in that column. The first column of 
     * the table being queried should be an int value.
     * 
     * @param tableName a String representing the name of the table to check
     * @param filterData a HashMap<String, String> where the keys are strings
     * representing the names of the column in the database and the values are
     * strings representing the values expected in the column.
     * @return an int representing the value stored in the first column of the table
     * row if it exists in the database, or -1 if the row does not exist.
     * @throws SQLException 
     */
    public static int recordExistsInDatabase(String tableName, HashMap<String, String> filterData)
            throws SQLException {
        int rowNum = -1;
        
        String[] columnNamesToReturn = { "*" };
        String sqlFilter = implodeFilterData(filterData);
        ResultSet rs = getResultSetForFilteredSelectStatement(columnNamesToReturn, tableName, sqlFilter);
        
        if(rs.next()) { rowNum = rs.getInt(1); }
        
        return rowNum;
    }
        
    /**
     * Inserts a row into a database table.
     * 
     * @param table a String representing the name of the table into which a row
     * will be inserted
     * @param data a HashMap<String, String> in which the keys are String objects
     * representing the names of the columns and the values are String objects
     * representing the values to be inserted into those columns.
     * @param keyColumn the name of the column that is the primary key for the
     * queried table.
     * @return an int representing the value stored in the primary key column after
     * the row has been inserted, or -1 if the insertion fails.
     * @throws SQLException 
     */
    public static int insertRowIntoDatabase(String table, HashMap<String, String> data,
            String keyColumn) throws SQLException {
        int genKey = -1;
        
        String query = "INSERT INTO " + table + " (" + implodeStrings((String[])data.keySet().toArray(), ", ") 
                + ") VALUES (" + implodeStrings((String[])data.values().toArray(), ", ") + ")";
        Statement stmt = DBConnection.conn.createStatement();
        
        String[] keyColumns = { keyColumn };
        stmt.executeUpdate(query, keyColumns);
        ResultSet genKeys = stmt.getGeneratedKeys();
        if(genKeys.next()) {
            genKey = genKeys.getInt(1);
        }
        
        return genKey;
    }

    /**
     * Allows the user to query a database table and retrieve a set of rows, ordered
     * in ascending order by a chosen column name and filtered by the passed sql-formatted
     * string.
     * 
     * @param columnNames an array of String objects representing the names of columns
     * to be returned
     * @param tableName a String representing the name of the table to be queried
     * @param sqlFilter a String representing an sql formatted filter to be applied
     * to the query
     * @param orderByColumnName a String representing the name column to use to order
     * the results
     * @return the ResultSet from the executed query statement
     * @throws SQLException 
     */
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
    
    /**
     * Allows the user to query a database table and retrieve a set of rows, ordered
     * in ascending order by a chosen column name.
     * 
     * @param columnNames an array of String objects representing the names of columns
     * to be returned
     * @param tableName a String representing the name of the table to be queried
     * @param orderByColumnName a String representing the name column to use to order
     * the results
     * @return the ResultSet from the executed query statement
     * @throws SQLException 
     */
    public static ResultSet getResultSetForOrderedSelectStatement(String[] columnNames,
            String tableName, String orderByColumnName) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames,
                tableName, "", orderByColumnName);
    }
    
    /**
     * Allows the user to query a database table and retrieve a set of rows, filtered
     * by the passed sql-formatted string.
     * 
     * @param columnNames an array of String objects representing the names of columns
     * to be returned
     * @param tableName a String representing the name of the table to be queried
     * @param sqlFilter a String representing an sql formatted filter to be applied
     * to the query
     * @return the ResultSet from the executed query statement
     * @throws SQLException 
     */
    public static ResultSet getResultSetForFilteredSelectStatement(String[] columnNames,
            String tableName, String sqlFilter) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames,
                tableName, sqlFilter, "");
    }
    
    /**
     * Allows the user to query a database table and retrieve a set of rows.
     * 
     * @param columnNames an array of String objects representing the names of columns
     * to be returned
     * @param tableName a String representing the name of the table to be queried
     * @return the ResultSet from the executed query statement
     * @throws SQLException 
     */
    public static ResultSet getResultSetForSelectStatement(String[] columnNames,
            String tableName) throws SQLException {
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", "");
    }
    
    /**
     * Allows the user to query a database table and retrieve a all rows, ordered
     * in ascending order.
     * 
     * @param orderByColumn a String representing the name column to use to order
     * the results
     * @return the ResultSet from the executed query statement
     * @throws SQLException 
     */
    public static ResultSet getResultSetOfAllOrderedRows(String tableName, String orderByColumn)
            throws SQLException {
        String[] columnNames = { "*" };
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", orderByColumn);
    }
    
    /**
     * Creates and returns a HashMap from the rows in the given ResultSet. The HashMap
     * is mapped from String instances, representing the column name, to Object instances,
     * representing the value in the column. The Object instance types are identified
     * by examining the metadata for each column-value set in the ResultSet.
     * 
     * @param rs the ResultSet with data to be formatted into a HashMap<String, Object>
     * @return a HashMap<String, Object> representing the data in the ResultSet
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static HashMap<String, Object> getHashMapFromResultSetRow(ResultSet rs) throws SQLException, ClassNotFoundException {
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
    
    /**
     * Allows a user to execute a custom query statement.
     * 
     * @param query a String representing the query to be executed.
     * @return the ResultSet containing the results of the query.
     * @throws SQLException 
     */
    public static ResultSet executeStatementFromQuery(String query) throws SQLException {
        Statement stmt = DBConnection.conn.createStatement();
        return stmt.executeQuery(query);
    }
                    
    /**
     * Implodes the value in an array of strings into a list of values separated
     * by the delimeter. For example, [ "cat", "dog", "cow" ] will be joined into
     * one String with the items separated by the delimeter, for example, ", ":
     * "cat, dog, cow".
     * 
     * @param strings an array of String representing the items in the list
     * @param delimiter a String representing the delimeter to be used between each
     * item in the list
     * @return a String representing the items in the array separated by the delimiter.
     */
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
    
    /**
     * Implodes the keys and values in a HashMap into a formatted list for filtering
     * a set of results from an sql query. For example, the map { "id" -> "2",
     * "name" -> "Joe" } would be imploded into the following format:
     * "id='2' and name='Joe'.
     * 
     * @param data a HashMap<String, String> representing the data to be imploded,
     * where the first string represents the column to search and the second String
     * represents the value to look for in that column.
     * @return a String representing the HashMap data in a format suitable for a 
     * WHERE statement in an sql query.
     */
    private static String implodeFilterData(HashMap<String, String> data) {
        String filter = "";
        
        for(String columnName : data.keySet()) {
            filter += columnName + "=" + data.get(columnName) + " AND ";
        }
        int lastIndexOfAnd = filter.lastIndexOf(" AND ");
        filter = filter.substring(0, lastIndexOfAnd);
        
        return filter;
    }
}
