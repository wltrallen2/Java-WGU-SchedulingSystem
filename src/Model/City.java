/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import Utilities.DBQuery;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author walterallen
 */
public class City {
    public static final String TABLE_NAME = "city";
    public static final String CITY_ID = "cityId";
    public static final String CITY_NAME = "city";
    public static final String COUNTRY_ID = "countryId";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";
    
    private int cityId;
    private String city;
    private Country country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     * Creates an instance of a City object based on the uCertify database
     * model.
     * 
     * @param id - an int representing the city id number
     * @param name - a String representing the city name
     * @param country - an instance of a Country object representing the country
     * for the given city
     * @param createDate - a Timestamp object representing the date and time the
     * database record was created
     * @param creatorName - a String representing the name of the database user
     * who created the record
     * @param updateDate - a Timestamp object representing the date and time that
     * the database record was last updated.
     * @param updatorName - a String representing the name of the database user
     * who last updated the record.
     */
    public City (int id, String name, Country country, 
            Timestamp createDate, String creatorName, 
            Timestamp updateDate, String updatorName) {
        this.cityId = id;
        this.city = name;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = creatorName;
        this.lastUpdate = updateDate;
        this.lastUpdateBy = updatorName;
    }
    
    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the city id for the City object in both the object instance
     * and in the database itself.
     * 
     * @param id - an int representing the cityId in the database
     * @throws java.sql.SQLException
     */
    public void setCityId(int id) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CITY_ID, id, CITY_ID, this.cityId);
        this.cityId = id;
    }
    
    /**
     * Sets the city name for the City object in both the object instance
     * and in the database itself.
     * 
     * @param name - a String representing the city name in the database
     * @throws java.sql.SQLException
     */
    public void setCityName(String name) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CITY_NAME, name, CITY_ID, this.cityId);
        this.city = name;
    }

    /**
     * Sets the country for the City object in both the object instance
     * and the country id in the database itself.
     * 
     * @param country - an instance of a Country object representing 
     * the country for the given city
     * @throws java.sql.SQLException
     */
    public void setCountryId(Country country) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, COUNTRY_ID, country.getCountryId(),
                CITY_ID, this.cityId);
        this.country = country;
    }
    
    /**
     * Sets the creation date for the City object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the creation date 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, timestamp, CITY_ID, this.cityId);
        this.createDate = timestamp;
    }

    /**
     * Sets the "created by" value for the City object in both the object instance
     * and in the database itself.
     * 
     * @param creator - a String object representing the creator 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreatedBy(String creator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, creator, CITY_ID, this.cityId);
        this.createdBy = creator;
    }
    
    /**
     * Sets the date of the last update for the City object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the date of the last update 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setUpdateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, timestamp, CITY_ID, this.cityId);
        this.lastUpdate = timestamp;
    }

    /**
     * Sets the "updated by" value for the City object in both the object instance
     * and in the database itself.
     * 
     * @param updator - a String object representing the name of the last user to update 
     * the database record
     * @throws java.sql.SQLException
     */
    public void setUpdatedBy(String updator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE_BY, updator, CITY_ID, this.cityId);
        this.lastUpdateBy = updator;
    }

    /***************************************************************************
     * GETTERS
     **************************************************************************/
    
    /**
     * 
     * @return an int representing the city id.
     */
    public int getCityId() {
        return cityId;
    }
    
    /**
     * 
     * @return a String representing the name of the city
     */
    public String getCityName() {
        return city;
    }
    
    /**
     * 
     * @return an instance of a Country object representing the country to
     * which the city belongs.
     */
    public Country getCountry() {
        return country;
    }
    
    /**
     * 
     * @return a String representing the name of the user who created the database
     * record.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    
    /**
     * 
     * @return a Timestamp object representing the date and time the database
     * record was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * 
     * @return a String representing the name of the user who last updated
     * the record in the database
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    /**
     * 
     * @return a Timestamp object representing the date and time the database
     * record was last updated
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    
    /***************************************************************************
     * PUBLIC STATIC METHODS
     **************************************************************************/
        
    /** Creates and returns an instance of a City object based on the paired values
     * in a HashMap, where the key is a String representing the names of variables
     * of a City object and the values are the values to be assigned to those variables.
     * 
     * @param data a HashMap<String, Object> representing the data to be used, where
     * the keys are the names of the variables in a City object, and the values are
     * Object instances representing the values to be passed. If the values are not
     * instances of the correct type, the data will not be accepted and an exception
     * will be thrown.
     * @return a City instance that contains the data that was passed into the method.
     * @throws Exception 
     */    
    public static City createCityInstanceFromHashMap(HashMap<String, Object> data) 
            throws Exception {
        if(data.containsKey(CITY_ID) && data.get(CITY_ID) instanceof Integer
                && data.containsKey(CITY_NAME) && data.get(CITY_NAME) instanceof String
                && data.containsKey(COUNTRY_ID) && data.get(COUNTRY_ID) instanceof Integer
                && data.containsKey(CREATE_DATE) && data.get(CREATE_DATE) instanceof Timestamp
                && data.containsKey(CREATED_BY) && data.get(CREATED_BY) instanceof String
                && data.containsKey(LAST_UPDATE) && data.get(LAST_UPDATE) instanceof Timestamp
                && data.containsKey(LAST_UPDATE_BY) && data.get(LAST_UPDATE_BY) instanceof String) {
            
            int id = (Integer)data.get(CITY_ID);
            String name = (String)data.get(CITY_NAME);
            int countryId = (Integer)data.get(COUNTRY_ID);
            Country country = AppointmentDatabase.getInstance().getCountryWithId(countryId);
            Timestamp createDate = (Timestamp)data.get(CREATE_DATE);
            String createdBy = (String)data.get(CREATED_BY);
            Timestamp lastUpdate = (Timestamp)data.get(LAST_UPDATE);
            String lastUpdateBy = (String)data.get(LAST_UPDATE_BY);
            
            return new City(id, name, country, createDate, createdBy, lastUpdate, lastUpdateBy);
        }
        
        throw new Exception("Error reading from array created from ResultSet row.");
    }
}
