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
    
    private Timestamp lastUpdateToCustomers;
    private Timestamp lastUpdateToAddresses;
    private Timestamp lastUpdateToCities;
    private Timestamp lastUpdateToCountries;
    
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
        addresses = pullAddressesFromDB();
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
        try {
            if(!getLastUpdateTimestampForTable(Country.TABLE_NAME).equals(lastUpdateToCountries)) {
               countries = pullCountriesFromDB();
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        }
        return countries;
    }
    
    /**
     * Returns a HashMap that maps the cityIds to the City objects.
     * 
     * @return a HashMap<Integer, City> representing the list of City objects in
     * the database.
     */
    public HashMap<Integer, City> getCities() {
        try {
            if(!getLastUpdateTimestampForTable(City.TABLE_NAME).equals(lastUpdateToCities)) {
                cities = pullCitiesFromDB();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return cities;
    }
    
    /**
     * Returns a HashMap that maps the addressIds to the Address objects.
     * 
     * @return a HashMap<Integer, Address> representing the list of Addresses
     * objects in the database.
     */
    public HashMap<Integer, Address> getAddresses() {
        // TODO: Finish implementation to update lastUpdate value
        return addresses;
    }
    
    /**
     * Returns a HashMap that maps the customerIds to the Customer objects.
     * 
     * @return a HashMap<Integer, Customer> representing the list of Customer
     * objects in the database.
     */
    public HashMap<Integer, Customer> getCustomers() {
        // TODO: Finish implementation to update lastUpdate value
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
    
    public Address getAddressWithId(int addressId) {
        return addresses.get(addressId);
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
            ts = rs.getTimestamp(1);
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
            
            if(lastUpdateToCountries == null || !lastUpdate.equals(lastUpdateToCountries)) {
                lastUpdateToCountries = lastUpdate;
            }
            
            map.put(id, new Country(id, name, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
        
        return map;
    }
    
    /**
     * Pulls the cities from the City table in the database and creates a
     * HashMap that maps the cityIds to instances of City objects representing
     * each city.

     * @return a HashMap mapping ints (representing city ids) to City objects
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
            
            if(lastUpdateToCities == null || !lastUpdate.equals(lastUpdateToCities)) {
                lastUpdateToCities = lastUpdate;
            }
            
            Country country = countries.get(countryId);
            map.put(id, new City(id, name, country, createDate, createdBy, lastUpdate, lastUpdateBy));
        }
        
        return map;
    }
    
        /**
     * Pulls the addresses from the Address table in the database and creates a
     * HashMap that maps the addressIds to instances of Address objects representing
     * each address.

     * @return a HashMap mapping ints (representing address ids) to Address objects
     * (representing each address in the database)
     * @throws SQLException 
     */
    private HashMap<Integer, Address> pullAddressesFromDB() throws SQLException {
        HashMap<Integer, Address> map = new HashMap<>();
        String tableName = Address.TABLE_NAME;
        String orderByColumnName = Address.POSTAL_CODE;
        ResultSet rs = DBQuery.getResultSetOfAllOrderedRows(tableName, orderByColumnName);

        while(rs.next()) {
            int id = rs.getInt(Address.ADDRESS_ID);
            String address1 = rs.getString(Address.ADDRESS_LINE1);
            String address2 = rs.getString(Address.ADDRESS_LINE2);
            int cityId = rs.getInt(Address.CITY_ID);
            String zip = rs.getString(Address.POSTAL_CODE);
            String phone = rs.getString(Address.PHONE);
            Timestamp createDate = rs.getTimestamp(City.CREATE_DATE);
            String createdBy = rs.getString(City.CREATED_BY);
            Timestamp lastUpdate = rs.getTimestamp(City.LAST_UPDATE);
            String lastUpdateBy = rs.getString(City.LAST_UPDATE_BY);
            
            if(lastUpdateToAddresses == null || !lastUpdate.equals(lastUpdateToAddresses)) {
                lastUpdateToAddresses = lastUpdate;
            }
            
            City city = cities.get(cityId);
            map.put(id, new Address(id, address1, address2, city, zip, phone,
                    createDate, createdBy, lastUpdate, lastUpdateBy));
        }
        
        return map;
    }

    
    /**
     * Retrieves a country from the database if it already exists, OR adds the
     * country to the database if the country does not already exist and returns
     * the new instance of the Country object.
     * 
     * @param name a String representing the name of the country.
     * @return a Country instance representing the data passed into the method.
     */
    public Country getCountryFromDB(String name) {
        // Create a HashMap with the unique data that represents a Country object.
        HashMap<String, String> filterData = new HashMap<>();
        filterData.put(Country.COUNTRY_NAME, name);
        
        // Check to see if the country already exists in the database, and
        // if it does, return a Country instance that represents that country.
        int countryId = DBQuery.recordExistsInDatabase(Country.TABLE_NAME, filterData);
        if(countryId != -1) {
            return getCountryWithId(countryId);
        }
        
        // Create a new HashMap repesenting the data required to insert
        // a row into the Country table in the database, and insert the row.
        HashMap<String, String> data = new HashMap<>();
        data.put(Country.COUNTRY_NAME, "'" + name + "'");
        data.put(Country.CREATE_DATE, "NOW()");
        data.put(Country.CREATED_BY, "'" + userName + "'");
        data.put(Country.LAST_UPDATE, "NOW()");
        data.put(Country.LAST_UPDATE_BY, "'" + userName + "'");
        int newId = DBQuery.insertRowIntoDatabase(Country.TABLE_NAME, data, Country.COUNTRY_ID);
        
        // Verify that the row was successfully inserted
        if(newId == -1) { return null; }
        
        // Retrieve the new row (including autogenerated fields) from the table,
        // and instantiate and return a new instance of a Country object.
        String[] colNamesToRetrieve = { "*" };
        HashMap<String, Object> dataMap = DBQuery.getHashMapFromResultSetRow(
                DBQuery.getResultSetForFilteredSelectStatement
                (colNamesToRetrieve, Country.TABLE_NAME, Country.COUNTRY_ID + "=" + newId));
        
        Country newCountry = null;
        try {
         newCountry = Country.createCountryInstanceFromHashMap(dataMap);
         countries.put(newId, newCountry);
         lastUpdateToCountries = newCountry.getLastUpdate();
        } catch(Exception ex) {
            System.out.println("Exception in AppointmentDatabase getCountryFromDB method.");
            System.out.println(ex);
        }
        
        return newCountry;
    }

    /**
     * Retrieves a city from the database if it already exists, OR adds a new row
     * in the City table in the database representing a new city if the city does
     * not already exists and returns a new instance of the City object.
     * 
     * @param name a String representing the name of the city to be added to the table
     * @param country a Country object representing the country in which the city resides
     * @return a City object representing the new city.
     */
    public City getCityFromDB(String name, Country country) {
        HashMap<String, String> filterData = new HashMap<>();
        filterData.put(City.CITY_NAME, name);
        filterData.put(City.COUNTRY_ID, country.getCountryId() + "");
        
        int rowNum = DBQuery.recordExistsInDatabase(City.TABLE_NAME, filterData);
        if(rowNum != -1) {
            return getCityWithId(rowNum);
        }
        
        HashMap<String, String> data = new HashMap<>();
        data.put(City.CITY_NAME, "'" + name + "'");
        data.put(City.COUNTRY_ID, "'" + country.getCountryId() + "'");
        data.put(City.CREATE_DATE, "NOW()");
        data.put(City.CREATED_BY, "'" + userName + "'");
        data.put(City.LAST_UPDATE, "NOW()");
        data.put(City.LAST_UPDATE_BY, "'" + userName + "'");
        int newId = DBQuery.insertRowIntoDatabase(City.TABLE_NAME, data, City.CITY_ID);
        
        if(newId == -1) { return null; }

        String[] colNamesToRetrieve = { "*" };
        HashMap<String, Object> dataMap = DBQuery.getHashMapFromResultSetRow(
                DBQuery.getResultSetForFilteredSelectStatement(
                colNamesToRetrieve, City.TABLE_NAME, City.CITY_ID + "=" + newId));
        
        City newCity = null;
        try {
            newCity = City.createCityInstanceFromHashMap(dataMap);
            cities.put(newId, newCity);
            lastUpdateToCities = newCity.getLastUpdate();
        } catch(Exception ex) {
            System.out.println("Exception in AppointmentDatabase getCityFromDb method");
            System.out.println(ex);
        }
        
        return newCity;
    }
    
    public Address getAddressFromDb(String address1, String address2, City city,
            String zip, String phone) {
        HashMap<String, String> filterData = new HashMap<>();
        filterData.put(Address.ADDRESS_LINE1, address1);
        filterData.put(Address.ADDRESS_LINE2, address2);
        filterData.put(Address.CITY_ID, city.getCityId() + "");
        filterData.put(Address.POSTAL_CODE, zip);
        filterData.put(Address.PHONE, phone);
        
        int rowNum = DBQuery.recordExistsInDatabase(Address.TABLE_NAME, filterData);
        if(rowNum != -1) {
            return getAddressWithId(rowNum);
        }
        
        HashMap<String, String> data = new HashMap<>();
        data.put(Address.ADDRESS_LINE1, "'" + address1 + "'");
        data.put(Address.ADDRESS_LINE2, "'" + address2 + "'");
        data.put(Address.CITY_ID, "'" + city.getCityId() + "'");
        data.put(Address.POSTAL_CODE, "'" + zip + "'");
        data.put(Address.PHONE, "'" + phone + "'");
        data.put(City.CREATE_DATE, "NOW()");
        data.put(City.CREATED_BY, "'" + userName + "'");
        data.put(City.LAST_UPDATE, "NOW()");
        data.put(City.LAST_UPDATE_BY, "'" + userName + "'");
        int newId = DBQuery.insertRowIntoDatabase(Address.TABLE_NAME, data, Address.ADDRESS_ID);
        
        if(newId == -1) { return null; }

        String[] colNamesToRetrieve = { "*" };
        HashMap<String, Object> dataMap = DBQuery.getHashMapFromResultSetRow(
                DBQuery.getResultSetForFilteredSelectStatement(
                colNamesToRetrieve, Address.TABLE_NAME, Address.ADDRESS_ID + "=" + newId));
        
        Address newAddress = null;
        try {
            newAddress = Address.createAddressInstanceFromHashMap(dataMap);
            addresses.put(newId, newAddress);
            lastUpdateToAddresses = newAddress.getLastUpdate();
        } catch(Exception ex) {
            System.out.println("Exception in AppointmentDatabase getAddressFromDb method");
            System.out.println(ex);
        }
        
        return newAddress;
    }


}
