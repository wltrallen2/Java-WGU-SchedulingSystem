/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author walterallen
 */
public class AppointmentDatabase {
    
    private static AppointmentDatabase singletonInstance = null;
    
    private String userName;
    private HashMap<Integer, Customer> customers;
    private HashMap<Integer, Address> addresses;
    private HashMap<Integer, City> cities;
    private HashMap<Integer, Country> countries;
    
    private AppointmentDatabase(String userName) throws SQLException {
        this.userName = userName;
        
        pullCountriesFromDB();
        pullCitiesFromDB();
        // pullAddressesFromDB();
        // pullCustomersFromDB();
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public static AppointmentDatabase getInstance() throws SQLException {
        if(singletonInstance == null) {
            throw new AssertionError("You must call init(String username) first.");
        }
        
        return singletonInstance;
    }
    
    public synchronized static AppointmentDatabase init(String userName) throws SQLException {
        if(singletonInstance != null) {
            throw new AssertionError("This Singleton Instance has already been initialized.");
        }
        
        singletonInstance = new AppointmentDatabase(userName);
        return singletonInstance;
    }
    
    public HashMap<Integer, Country> getCountries() {
        //TODO: Lazy instantiation
        //TODO: Check for last update and reload if not current.
        return countries;
    }
    
    public HashMap<Integer, City> getCities() {
        return cities;
    }
    
    public HashMap<Integer, Address> getAddresses() {
        return addresses;
    }
    
    public HashMap<Integer, Customer> getCustomers() {
        return customers;
    }
    
    public int rowExistsInDatabase(String tableName,
            String columnName, String columnValue) throws SQLException {
        int rowNum = -1;
        
        String[] columnNames = { "*" };
        String sqlFilter = columnName + "='" + columnValue + "'";
        ResultSet rs = DBQuery.getResultSetForFilteredSelectStatement(columnNames, tableName, sqlFilter);
        
        if(rs.next()) {
            rowNum = rs.getInt(1);
        }
        
        return rowNum;
    }
        
    private void pullCountriesFromDB() throws SQLException {
        countries = new HashMap<>();
        String tableName = "country";
        String orderByColumnName = "country";
        ResultSet rs = DBQuery.getResultSetOfAllOrderedRows(tableName, orderByColumnName);
        
        while(rs.next()) {
            int id = rs.getInt(Country.COUNTRY_ID);
            String name = rs.getString(Country.COUNTRY_NAME);
            Timestamp createDate = rs.getTimestamp(Country.CREATE_DATE);
            String createdBy = rs.getString(Country.CREATED_BY);
            Timestamp lastUpdate = rs.getTimestamp(Country.LAST_UPDATE);
            String lastUpdateBy = rs.getString(Country.LAST_UPDATE_BY);
            
            countries.put(id, new Country(id, name, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
    }
    
    private void pullCitiesFromDB() throws SQLException {
        cities = new HashMap<>();
        String tableName = "city";
        String orderByColumnName = "city";
        ResultSet rs = DBQuery.getResultSetOfAllOrderedRows(tableName, orderByColumnName);

        while(rs.next()) {
            int id = rs.getInt(City.CITY_ID);
            String name = rs.getString(City.CITY_NAME);
            int countryId = rs.getInt(City.COUNTRY_ID);
            Timestamp createDate = rs.getTimestamp(City.CREATE_DATE);
            String createdBy = rs.getString(City.CREATED_BY);
            Timestamp lastUpdate = rs.getTimestamp(City.LAST_UPDATE);
            String lastUpdateBy = rs.getString(City.LAST_UPDATE_BY);
            
            Country country = countries.get(countryId);
            cities.put(id, new City(id, name, country, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
    }
}
