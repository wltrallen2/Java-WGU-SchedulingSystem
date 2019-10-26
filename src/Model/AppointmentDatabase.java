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
import java.util.Iterator;
import java.util.Map;

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
    
    /**
     * Class constructor. Sets the name of the user that is accessing the database.
     * 
     * @param userName a String representing the name of the user accessing the database.
     * @throws SQLException 
     */
    private AppointmentDatabase(String userName) throws SQLException {
        this.userName = userName;
        
        countries = pullCountriesFromDB();
        cities = pullCitiesFromDB();
        // TODO: pullAddressesFromDB();
        // TODO: pullCustomersFromDB();
    }
    
    /**
     * 
     * @return the Singleton instance of an AppointmentDatabase object.
     */
    public static AppointmentDatabase getInstance() {
        if(singletonInstance == null) {
            throw new AssertionError("You must call init(String username) first.");
        }
        
        return singletonInstance;
    }
    
    /**
     * Synchronized class constructor that ensures that only one Singleton instance
     * of the AppointmentDatabase object is initialized with a given user name value.
     * 
     * @param userName a String representing the user accessing the database.
     * @return a singleton instance of the AppointmentDatabase object.
     * @throws SQLException 
     */
    public synchronized static AppointmentDatabase init(String userName) throws SQLException {
        if(singletonInstance != null) {
            throw new AssertionError("This Singleton Instance has already been initialized.");
        }
        
        singletonInstance = new AppointmentDatabase(userName);
        return singletonInstance;
    }
    
    /**
     * Sets the name of the user that is accessing the database.
     * 
     * @param userName a String representing the name of the user accessing the database.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 
     * @return a String representing the name of the user accessing the database.
     */
    public String getUserName() {
        return userName;
    }
        
    /**
     * Returns a HashMap that maps the countryIds to the Country objects. 
     * 
     * @return a HashMap<Integer, Country> representing the list of Country objects
     * in the database.
     */
    public HashMap<Integer, Country> getCountries() {
        //TODO: Check for last update and reload if not current.
        return countries;
    }
    
    /**
     * Returns a HashMap that maps the cityIds to the City objects.
     * 
     * @return a HashMap<Integer, City> representing the list of City objects in
     * the database.
     */
    public HashMap<Integer, City> getCities() {
        //TODO: Check for last update and reload if not current.
        return cities;
    }
    
    /**
     * Returns a HashMap that maps the addressIds to the Address objects.
     * 
     * @return a HashMap<Integer, Address> representing the list of Addresses
     * objects in the database.
     */
    public HashMap<Integer, Address> getAddresses() {
        return addresses;
    }
    
    /**
     * Returns a HashMap that maps the customerIds to the Customer objects.
     * 
     * @return a HashMap<Integer, Customer> representing the list of Customer
     * objects in the database.
     */
    public HashMap<Integer, Customer> getCustomers() {
        return customers;
    }
    
    /**
     * Allows the user to access an instance of a Country using the countryId value.
     * 
     * @param countryid an int representing the country id to be accessed.
     * @return a Country object whose country id value matches the country id passed
     */
    public Country getCountryWithId(int countryid) {
        return countries.get(countryid);
    }
    
    /**
     * Allows the user to access an instance of a City using the cityId value.
     * 
     * @param cityId an int representing the city id to be accessed.
     * @return a City object whose city id value matches the city id passed
     */
    public City getCityWithId(int cityId) {
        return cities.get(cityId);
    }
    
    /**
     * Allows a user to find the last date and time that any one of the records 
     * of a table were updated. Note that if a record with the most recent lastupdate
     * value is deleted, then the lastupdate value returned from this method will
     * be earlier than that delete timestamp.
     * 
     * @param tableName the String representing the table to query.
     * @return a Timestamp object that represents update date and time of the row 
     * in the database that was most recently updated.
     * @throws SQLException 
     */
    public Timestamp getLastUpdateTimestampForTable(String tableName) throws SQLException {
        Timestamp ts = null;
        
        String query = "SELECT MAX(lastupdate) FROM " + tableName;
        ResultSet rs = DBQuery.executeStatementFromQuery(query);
        if(rs.next()) {
            ts = rs.getTimestamp(0);
        }
        
        return ts;
    }
    
    /**
     * Pulls the countries from the Country table in the database and creates a
     * HashMap that maps the countryIds to instances of Country objects representing
     * each country.
     * 
     * @return a HashMap mapping ints (representing country ids) to Country objects
     * (representing each country in the database)
     * @throws SQLException 
     */
    private HashMap<Integer, Country> pullCountriesFromDB() throws SQLException {
        HashMap<Integer, Country>map = new HashMap<>();
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
            
            map.put(id, new Country(id, name, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
        
        return map;
    }
    
    /**
     * Pulls the cities from the City table in the database and creates a
     * HashMap that maps the cityIds to instances of City objects representing
     * each city.

     * @returna HashMap mapping ints (representing city ids) to City objects
     * (representing each city in the database)
     * @throws SQLException 
     */
    private HashMap<Integer, City> pullCitiesFromDB() throws SQLException {
        HashMap<Integer, City> map = new HashMap<>();
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
            map.put(id, new City(id, name, country, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
        
        return map;
    }
}
