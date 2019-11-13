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
        customers = pullCustomersFromDB();
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
        // TODO: Can we do this with lazy instantiation so that we can refractor
        // some of the lenghtier sections of code?
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
        try {
            if(!getLastUpdateTimestampForTable(Address.TABLE_NAME).equals(lastUpdateToAddresses)) {
                addresses = pullAddressesFromDB();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return addresses;
    }
    
    /**
     * Returns a HashMap that maps the customerIds to the Customer objects.
     * 
     * @return a HashMap<Integer, Customer> representing the list of Customer
     * objects in the database.
     */
    public HashMap<Integer, Customer> getCustomers() {
        try {
            if(!getLastUpdateTimestampForTable(Customer.TABLE_NAME).equals(lastUpdateToCustomers)) {
                customers = pullCustomersFromDB();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
     * Allows the user to access an instance of an Address using the addressId value.
     * 
     * @param addressId an int representing the address id to be accessed.
     * @return an Address object whose address id value matches the address id passed
     */
    public Address getAddressWithId(int addressId) {
        return addresses.get(addressId);
    }
    
    /**
     * Allows the user to access an instance of a Customer using the customerId value.
     * 
     * @param customerId an int representing the customer id to be accessed.
     * @return a Customer object whose address id value matches the customer id passed
     */
    public Customer getCustomerWithId(int customerId) {
        return customers.get(customerId);
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
        
        // TODO: Can these be refractored to use the getHashMapFromResultSetRow method
        // Current problem is that the getHashMapFromResultSetRow method calls on the
        // Singleton instance, which is not yet instantiated fully. What is the workaround?
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
     * Pulls the customers from the Customer table in the database and creates a
     * HashMap that maps the customerIds to instances of Customer objects representing
     * each address.

     * @return a HashMap mapping ints (representing customer ids) to Customer objects
     * (representing each customer in the database)
     * @throws SQLException 
     */
    private HashMap<Integer, Customer> pullCustomersFromDB() throws SQLException {
        HashMap<Integer, Customer> map = new HashMap<>();
        String tableName = Customer.TABLE_NAME;
        String orderByColumnName = Customer.CUSTOMER_NAME;
        ResultSet rs = DBQuery.getResultSetOfAllOrderedRows(tableName, orderByColumnName);

        while(rs.next()) {
            int id = rs.getInt(Customer.CUSTOMER_ID);
            String name = rs.getString(Customer.CUSTOMER_NAME);
            int addressId = rs.getInt(Customer.ADDRESS_ID);
            Boolean active = rs.getBoolean(Customer.ACTIVE);
            Timestamp createDate = rs.getTimestamp(City.CREATE_DATE);
            String createdBy = rs.getString(City.CREATED_BY);
            Timestamp lastUpdate = rs.getTimestamp(City.LAST_UPDATE);
            String lastUpdateBy = rs.getString(City.LAST_UPDATE_BY);
            
            if(lastUpdateToCustomers == null || !lastUpdate.equals(lastUpdateToCustomers)) {
                lastUpdateToCustomers = lastUpdate;
            }
            
            Address address = addresses.get(addressId);
            map.put(id, new Customer(id, name, address, active,
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
        HashMap<String, Object> data = new HashMap<>();
        data.put(Country.COUNTRY_NAME, name);
        
        // Check to see if the country already exists in the database, and
        // if it does, return a Country instance that represents that country.
        int countryId = DBQuery.recordExistsInDatabase(Country.TABLE_NAME, data);
        if(countryId != -1) {
            return getCountryWithId(countryId);
        }
        
        // Create a new HashMap repesenting the data required to insert
        // a row into the Country table in the database, and insert the row.
        insertStandardMetaDataIntoMap(data);
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
        HashMap<String, Object> data = new HashMap<>();
        data.put(City.CITY_NAME, name);
        data.put(City.COUNTRY_ID, country.getCountryId());
        
        int rowNum = DBQuery.recordExistsInDatabase(City.TABLE_NAME, data);
        if(rowNum != -1) {
            return getCityWithId(rowNum);
        }
        
        insertStandardMetaDataIntoMap(data);
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
    
    /**
     * Retrieves an address from the database if it already exists, OR adds a new row
     * in the Address table in the database representing a new address if the address does
     * not already exists and returns a new instance of the Address object.
     * 
     * @param address1 a String representing the first line of the address
     * @param address2 a String representing the second line of the address
     * @param city a City object representing the city in which the address resides
     * @param zip a String representing the address zip code
     * @param phone a String representing the phone number associated with this address
     * @return an Address object representing the new address.
     */
    public Address getAddressFromDb(String address1, String address2, City city,
            String zip, String phone) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(Address.ADDRESS_LINE1, address1);
        data.put(Address.ADDRESS_LINE2, address2);
        data.put(Address.CITY_ID, city.getCityId());
        data.put(Address.POSTAL_CODE, zip);
        data.put(Address.PHONE, phone);
        
        int rowNum = DBQuery.recordExistsInDatabase(Address.TABLE_NAME, data);
        if(rowNum != -1) {
            return getAddressWithId(rowNum);
        }
        
        insertStandardMetaDataIntoMap(data);
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
    
    /**
     * Retrieves a Customer from the database if it already exists, OR adds a new row
     * in the Customer table in the database representing a new customer if the customer does
     * not already exists and returns a new instance of the Customer object.
     * 
     * @param customerName a String representing the name of the customer
     * @param address an Address representing the address of the customer
     * @param active a Boolean representing the active status of the customer, true
     * if active and false if inactive
     * @return a City object representing the new city.
     */
    public Customer getCustomerFromDb(String customerName, Address address, Boolean active) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(Customer.CUSTOMER_NAME, customerName);
        data.put(Customer.ADDRESS_ID, address.getAddressId());
        data.put(Customer.ACTIVE, active);
        
        int rowNum = DBQuery.recordExistsInDatabase(Customer.TABLE_NAME, data);
        if(rowNum != -1) {
            return getCustomerWithId(rowNum);
        }
        
        insertStandardMetaDataIntoMap(data);
        int newId = DBQuery.insertRowIntoDatabase(Customer.TABLE_NAME, data, Customer.CUSTOMER_ID);
        
        if(newId == -1) { return null; }

        String[] colNamesToRetrieve = { "*" };
        HashMap<String, Object> dataMap = DBQuery.getHashMapFromResultSetRow(
                DBQuery.getResultSetForFilteredSelectStatement(
                colNamesToRetrieve, Customer.TABLE_NAME, Customer.CUSTOMER_ID + "=" + newId));
        
        Customer newCustomer = null;
        try {
            newCustomer = Customer.createCustomerInstanceFromHashMap(dataMap);
            customers.put(newId, newCustomer);
            lastUpdateToCustomers = newCustomer.getLastUpdate();
        } catch(Exception ex) {
            System.out.println("Exception in AppointmentDatabase getCustomerFromDb method");
            System.out.println(ex);
        }
        
        return newCustomer;
    }
    
    /**
     * Removes a customer from the Customer table in the database as well as removes
     * the corresponding Customer instance from the AppointmentDatabase singleton
     * instance. Additionally, since the main structure of the database is unable to
     * be altered, this method will then call the removeAddress function which will
     * test to see if that address is assigned to another customer, and if not,
     * will remove it from the database.
     * 
     * @param customer a Customer instance representing the customer to be removed
     * @return true if the customer was removed from the database, false if not
     */
    public boolean removeCustomer(Customer customer) {
        boolean success = false;
        
        Address address = customer.getAddress();
        
        HashMap<String, Object> filterData = new HashMap<>();
        filterData.put(Customer.CUSTOMER_ID, customer.getCustomerId());
        if(DBQuery.deleteFromTable(Customer.TABLE_NAME, filterData) == 1) {
            customers.remove(customer.getCustomerId());
            removeAddress(address);
            success = true;
        }
        
        return success;
    }
    
    /**
     * Removes an address from the Address table in the database as well as removes
     * the corresponding Address instance from the AppointmentDatabase singleton
     * instance if and only if the address is not assigned to another customer in
     * the customer table of the database. Additionally, since the main structure
     * of the database is unable to be altered, this method will then call the 
     * removeCity function which will test to see if that city is assigned to 
     * another address, and if not, will remove it from the database.
     * 
     * @param address an Address instance representing the address to be removed
     * @return true if the address was removed from the database, false if not
     */
    public boolean removeAddress(Address address) {
        boolean success = false;
        
        City city = address.getCity();
        
        HashMap<String, Object> filterData = new HashMap<>();
        filterData.put(Address.ADDRESS_ID, address.getAddressId());
        if(DBQuery.getCountOfRowsForFilterData(Customer.TABLE_NAME, filterData) == 0
                && DBQuery.deleteFromTable(Address.TABLE_NAME, filterData) == 1) {
            addresses.remove(address.getAddressId());
            removeCity(city);
            success = true;
        }
        
        return success;
    }
    
    /**
     * Removes a city from the City table in the database as well as removes
     * the corresponding City instance from the AppointmentDatabase singleton
     * instance, if and only if the city is not assigned to another address in
     * the address table. Additionally, since the main structure of the database
     * is unable to be altered, this method will then call the removeCountry 
     * function which will test to see if that country is assigned to another
     * city, and if not, will remove it from the database.
     * 
     * @param city a City instance representing the city to be removed
     * @return true if the city was removed from the database, false if not
     */
    public boolean removeCity(City city) {
        boolean success = false;
        
        Country country = city.getCountry();
        
        HashMap<String, Object> filterData = new HashMap<>();
        filterData.put(City.CITY_ID, city.getCityId());
        if(DBQuery.getCountOfRowsForFilterData(Address.TABLE_NAME, filterData) == 0
                && DBQuery.deleteFromTable(City.TABLE_NAME, filterData) == 1) {
            cities.remove(city.getCityId());
            removeCountry(country);
            success = true;
        }
        
        return success;
    }
    
    /**
     * Removes a country from the Country table in the database as well as removes
     * the corresponding Country instance from the AppointmentDatabase singleton
     * instance, if and only if the Country is not assigned to a city in the 
     * city table of the database.
     * 
     * @param country a Country instance representing the country to be removed
     * @return true if the country was removed from the database, false if not
     */
    public boolean removeCountry(Country country) {
        boolean success = false;
        
        HashMap<String, Object> filterData = new HashMap<>();
        filterData.put(Country.COUNTRY_ID, country.getCountryId());
        if(DBQuery.getCountOfRowsForFilterData(City.TABLE_NAME, filterData) == 0
                && DBQuery.deleteFromTable(Country.TABLE_NAME, filterData) == 1) {
            countries.remove(country.getCountryId());
            success = true;
        }
        
        return success;
    }

    /** Updates the information for a customer by comparing the original Customer
     * instance with a new instance of Customer containing the update information.
     * The method will update the customer name and addressId as necessary. Then,
     * the method calls on the remove methods for address, city, and country, which
     * will delete the old address, city, and/or country from the database if they
     * are not associated with any current customer records.
     * 
     * @param oldCustomer a Customer instance representing the original customer
     * data that is to be altered.
     * @param newCustomer a Customer instance representing the new customer data
     * that is to replace the old data.
     */
    public void updateCustomerInformation(Customer oldCustomer, Customer newCustomer) {
        if(!oldCustomer.getCustomerName().equals(newCustomer.getCustomerName())) {
            DBQuery.updateSingleValueInDatabase
                    (Customer.TABLE_NAME, Customer.CUSTOMER_NAME, newCustomer.getCustomerName(),
                    Customer.CUSTOMER_ID, oldCustomer.getCustomerId());
        }
        
        if(!oldCustomer.getAddress().equals(newCustomer.getAddress())) {
            DBQuery.updateSingleValueInDatabase
                    (Customer.TABLE_NAME, Customer.ADDRESS_ID, newCustomer.getAddress().getAddressId(),
                    Customer.CUSTOMER_ID, oldCustomer.getCustomerId());
        }

        Address address = oldCustomer.getAddress();
        City city = address.getCity();
        Country country = city.getCountry();
        
        removeAddress(address);
        removeCity(city);
        removeCountry(country);
    }

    /**
     * Inserts the standard four metadata column-value pairs into the data HashMap.
     * These pairs include the SQL Command NOW() for the createDate and lastUpdate
     * columns and the username for the createdBy and the lastUpdateBy columns.
     * 
     * @param data a HashMap that maps String instances representing the column name
     * to Object instances representing the values to be stored in those columns.
     */
    private void insertStandardMetaDataIntoMap(HashMap<String, Object> data) {
        data.put(Country.CREATE_DATE, SQLCommand.NOW);
        data.put(Country.CREATED_BY, userName);
        data.put(Country.LAST_UPDATE, SQLCommand.NOW);
        data.put(Country.LAST_UPDATE_BY, userName);
    }
}
