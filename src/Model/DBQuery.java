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
    
    // TODO: This should be in the AppointmentDatabase class OR should be 
    // generalized so that it's not Database specific.
    /**
     * Queries the company database to see if there is a matching user with the
     * given password. The method will return an int representing the userId
     * value in the database if the username and password combination is found.
     * Else, the method will return a -1.
     * 
     * @param username a String representing the username value in the database
     * @param password a String representing the user password value in the database
     * @return an int representing the userId or -1 if not found
     */
    public static int fetchUserId(String username, String password) {
        int userId = -1;
        
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
            prepstmt.setString(1, username);
            prepstmt.setString(2, password);

            ResultSet rs = prepstmt.executeQuery();
            if(rs.next()) {
                userId = rs.getInt("userId");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery fetchUserId method.");
            System.out.println(ex);
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
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, String value,
            String primaryKeyColumnName, int primaryKeyValue) {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "= ?" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        
        try {
            PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
            prepstmt.setString(1, value);

            if(prepstmt.executeUpdate() > 0) { success = true; }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery updateSingleValueInDatabase method");
            System.out.println(ex);
        }
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
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, int value,
            String primaryKeyColumnName, int primaryKeyValue) {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "= ?" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        
        try {
            PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
            prepstmt.setInt(1, value);

            if(prepstmt.executeUpdate() > 0) { success = true; }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery in updateSingleValueInDatabase method.");
            System.out.println(ex);
        }
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
     */
    public static boolean updateSingleValueInDatabase(String table, String columnName, Timestamp value,
            String primaryKeyColumnName, int primaryKeyValue) {
        boolean success = false;
        
        String query = "UPDATE " + table + " SET " + columnName + "= ?" +
                " WHERE " + primaryKeyColumnName + "=" + primaryKeyValue;
        
        try {
            PreparedStatement prepstmt = DBConnection.conn.prepareStatement(query);
            prepstmt.setTimestamp(1, value);

            if(prepstmt.executeUpdate() > 0) { success = true; }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery updateSingleValueInDatabase method.");
            System.out.println(ex);
        }
        
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
     */
    public static int recordExistsInDatabase(String tableName, HashMap<String, Object> filterData) {
        int rowNum = -1;
        
        String[] columnNamesToReturn = { "*" };
        String sqlFilter = implodeFilterData(filterData);
        
        try {
            ResultSet rs = getResultSetForFilteredSelectStatement(columnNamesToReturn, tableName, sqlFilter);

            if(rs.next()) { rowNum = rs.getInt(1); }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery in recordExistsInDatabase method");
            System.out.println(ex);
        }
        
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
     */
    public static int insertRowIntoDatabase(String table, HashMap<String, Object> data,
            String keyColumn) {
        int genKey = -1;
       
        // Implodes the column and value data into two separate strings, which
        // is returned in a hashmap with the key values "columns" and "values".
        HashMap<String, String> implodedData = implodeColumnValueData(data);
        String query = "INSERT INTO " + table + " (" + implodedData.get("columns") 
                + ") VALUES (" + implodedData.get("values") + ")";
        
        try {
            Statement stmt = DBConnection.conn.createStatement();

            String[] keyColumns = { keyColumn };
            stmt.executeUpdate(query, keyColumns);
            ResultSet genKeys = stmt.getGeneratedKeys();
            if(genKeys.next()) {
                genKey = genKeys.getInt(1);
        }
        } catch (SQLException ex) {
            System.out.println("SQLException in DBQuery insertRowIntoDatabase method.");
            System.out.println(ex);
        }
        
        return genKey;
    }
    
    /**
     * Deletes records from a table in the database by matching records to the
     * filter data that is passed into the method.
     * 
     * @param tableName a String representing the name of the table from which 
     * rows are to be deleted.
     * @param filterData a HashMap mapping Strings to Objects, where the String
     * represents the name of the column to check and the Object represents the
     * value to match in the given column.
     * @return an int representing the number of rows that were successfully deleted 
     * from the table.
     */
    public static int deleteFromTable(String tableName, HashMap<String, Object> filterData) {
        int success = 0;
        
        String filter = implodeFilterData(filterData);
        String query = "DELETE FROM " + tableName + " WHERE " + filter;
        
        try {
            success = DBConnection.conn.createStatement().executeUpdate(query);
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery deleteFromTable() method.");
            System.out.println(ex);
        }
        
        return success;
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
     * @return the ResultSet from the executed query statement, or null if there
     * is an error in the sql statement.
     */
    public static ResultSet getResultSetForFilteredOrderedSelectStatement(
            String[] columnNames, String tableName, String sqlFilter,
            String orderByColumnName) {
        
        ResultSet rs = null;
        
        String query = "SELECT " + implodeStrings(columnNames, ",") + " FROM " + tableName;
        
        if(!sqlFilter.equals("")) {
            query += " WHERE " + sqlFilter;
        }
        
        if(!orderByColumnName.equals("")) {
            query += " ORDER BY " + orderByColumnName;
        }
        
        try {
            rs = executeStatementFromQuery(query);
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery executeStatementFromQuery method.");
            System.out.println(ex);
        }
        
        return rs;
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
     */
    public static ResultSet getResultSetForOrderedSelectStatement(String[] columnNames,
            String tableName, String orderByColumnName) {
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
     */
    public static ResultSet getResultSetForFilteredSelectStatement(String[] columnNames,
            String tableName, String sqlFilter) {
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
     */
    public static ResultSet getResultSetForSelectStatement(String[] columnNames,
            String tableName) {
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", "");
    }
    
    /**
     * Allows the user to query a database table and retrieve a all rows, ordered
     * in ascending order.
     * 
     * @param tableName a String representing the name of the table to query
     * @param orderByColumn a String representing the name column to use to order
     * the results
     * @return the ResultSet from the executed query statement
     */
    public static ResultSet getResultSetOfAllOrderedRows(String tableName, String orderByColumn) {
        String[] columnNames = { "*" };
        return getResultSetForFilteredOrderedSelectStatement(columnNames, tableName, "", orderByColumn);
    }
    
    /**
     * Allows the user to query a table and retrieve the number of rows that match a
     * given set of filtered values.
     * 
     * @param tableName a String object representing the name of the table to be queried
     * @param filterData a Hashmap mapping String instances to Object instances where
     * the Strings represent the column name and the Objects represent the values
     * to be searched for in those columns.
     * @return an int representing the number of rows found that match the filter data.
     */
    public static int getCountOfRowsForFilterData(String tableName, HashMap<String, Object> filterData) {
        int numRows = 0;
        
        String filter = implodeFilterData(filterData);
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + filter;
        
        try {
            ResultSet rs = DBConnection.conn.createStatement().executeQuery(query);
            while(rs.next()) {
                numRows = rs.getInt("COUNT(*)");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException in DBQuery in getCountOfRowsForFilterData method.");
            System.out.println(ex);
        }
        
        return numRows;
    }
    
    /**
     * Creates and returns a HashMap from the rows in the given ResultSet. The HashMap
     * is mapped from String instances, representing the column name, to Object instances,
     * representing the value in the column. The Object instance types are identified
     * by examining the metadata for each column-value set in the ResultSet.
     * 
     * @param rs the ResultSet with data to be formatted into a HashMap<String, Object>
     * @return a HashMap<String, Object> representing the data in the ResultSet
     */
    public static HashMap<String, Object> getHashMapFromResultSetRow(ResultSet rs) {
        HashMap<String, Object> rowData = new HashMap<>();
        
        try {
        if(rs.next()) {
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String colName = rs.getMetaData().getColumnLabel(i);
                String colType = rs.getMetaData().getColumnClassName(i);
                rowData.put(colName, rs.getObject(i, Class.forName(colType)));
            }
        }
        } catch(SQLException ex) {
            System.out.println("SQLException in AppointmentDatabase getHashMapFromResultSetRow method.");
            System.out.println(ex);
        } catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException in AppointmentDatabase getHashMapFromResultSetRow method.");
            System.out.println(ex);
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
     * Implodes a set of string to object maps into two separate strings, each
     * delimited by a comma. The strings (or keys in the original
     * HashMap) represent the column names and the objects represent
     * the values to be inserted into those columns. The data is returned in a
     * HashMap with the strings mapped to the key "columns" and the
     * objects mapped to the key "values."
     * 
     * @param data a HashMap<String, Object> where the keys are String instances
     * representing the names of columns into which data is to be inserted and
     * the values are Object instances representing the data to be inserted into the
     * respective columns.
     * @return a HashMap<String, String> consisting of two key-value pairs: (1) for
     * the key "columns", a String representing a list of the columns into which data
     * is to be inserted, and (2) for the key "values", a String representing the
     * list of data to be inserted respectively.
     */
    private static HashMap<String, String> implodeColumnValueData(HashMap<String, Object> data) {
        HashMap<String, String> implodedData = new HashMap<>();
        
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        
        for(String col : data.keySet()) {
            keys.add(col);
            
            Object value = data.get(col);
            if (value instanceof SQLCommand) {
                values.add(((SQLCommand) value).command());
            } else if (value instanceof String) {
                values.add("'" + (String)value + "'");
            } else if (value instanceof Boolean) {
                values.add((Boolean)value ? "1" : "0");
            } else {
                values.add(value.toString());
            }
        }
        
        String implodedKeys = implodeStrings(keys.toArray(new String[0]), ",");
        String implodedValues = implodeStrings(values.toArray(new String[0]), ",");
        
        implodedData.put("columns", implodedKeys);
        implodedData.put("values", implodedValues);
        return implodedData;
    }
    
    /**
     * Implodes the keys and values in a HashMap into a formatted list for filtering
     * a set of results from an sql query. For example, the map { "id" -> "2",
     * "name" -> "Joe" } would be imploded into the following format:
     * "id=2 and name='Joe'. The method takes into account the formatting necessary
     * to differentiate String values, Boolean values, SQL Command values, and
     * numerical values.
     * 
     * @param data a HashMap<String, Object> representing the data to be imploded,
     * where the string represents the column to search and the Object
     * represents the value to look for in that column.
     * @return a String representing the HashMap data in a format suitable for a 
     * WHERE statement in an sql query.
     */
    private static String implodeFilterData(HashMap<String, Object> data) {
        String filter = "";
        
        for(String columnName : data.keySet()) {
            filter += columnName + "=";

            Object value = data.get(columnName);            
            if (value instanceof SQLCommand) {
                filter += ((SQLCommand) value).command();
            } else if(value instanceof String) { 
                filter += "'" + (String)value + "'"; 
            } else if (value instanceof Boolean) {
                filter += ((Boolean)value ? 1 : 0);
            } else { 
                filter += value.toString();
            }
            // TODO: Add if-else brance for a TimeStamp & investigate other needs.
            
            filter += " AND ";
        }
        
        int lastIndexOfAnd = filter.lastIndexOf(" AND ");
        filter = filter.substring(0, lastIndexOfAnd);
        
        return filter;
    }
}
