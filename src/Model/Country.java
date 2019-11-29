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
public class Country {
    
    /***************************************************************************
     * CONSTANTS
     **************************************************************************/
    public static final String TABLE_NAME = "country";
    public static final String COUNTRY_ID = "countryId";
    public static final String COUNTRY_NAME = "country";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    /***************************************************************************
     * PARAMETERS
     **************************************************************************/
    private int countryId;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    /***************************************************************************
     * CONSTRUCTOR
     **************************************************************************/
  
    /**
     * Creates an instance of a Country object based on the uCertify database
     * model.
     * 
     * @param id - an int representing the country id number
     * @param name - a String representing the country name
     * @param createDate - a Timestamp object representing the date and time the
     * database record was created
     * @param creatorName - a String representing the name of the database user
     * who created the record
     * @param updateDate - a Timestamp object representing the date and time that
     * the database record was last updated.
     * @param updatorName - a String representing the name of the database user
     * who last updated the record.
     */
    public Country(int id, String name, Timestamp createDate, String creatorName, Timestamp updateDate, String updatorName) {
        this.countryId = id;
        this.country = name;
        this.createDate = createDate;
        this.createdBy = creatorName;
        this.lastUpdate = updateDate;
        this.lastUpdateBy = updatorName;
    }
    
    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the country id for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param id - an int representing the countryId in the database
     * @throws java.sql.SQLException
     */
    public void setCountryId(int id) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, COUNTRY_ID, id, COUNTRY_ID, this.countryId);
        this.countryId = id;
    }
    
    /**
     * Sets the country name for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param name - a String representing the country name in the database
     * @throws java.sql.SQLException
     */
    public void setCountryName(String name) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, COUNTRY_NAME, name, COUNTRY_ID, this.countryId);
        this.country = name;
    }

    /**
     * Sets the creation date for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the creation date 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, timestamp, COUNTRY_ID, this.countryId);
        this.createDate = timestamp;
    }

    /**
     * Sets the "created by" value for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param creator - a String object representing the creator 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreatedBy(String creator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, creator, COUNTRY_ID, this.countryId);
        this.createdBy = creator;
    }
    
    /**
     * Sets the date of the last update for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the date of the last update 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setUpdateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, timestamp, COUNTRY_ID, this.countryId);
        this.lastUpdate = timestamp;
    }

    /**
     * Sets the "updated by" value for the Country object in both the object instance
     * and in the database itself.
     * 
     * @param updator - a String object representing the name of the last user to update 
     * the database record
     * @throws java.sql.SQLException
     */
    public void setUpdatedBy(String updator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE_BY, updator, COUNTRY_ID, this.countryId);
        this.lastUpdateBy = updator;
    }

    /***************************************************************************
     * GETTERS
     **************************************************************************/
    
    /**
     * 
     * @return an int representing the country id.
     */
    public int getCountryId() {
        return countryId;
    }
    
    /**
     * 
     * @return a String representing the name of the country
     */
    public String getCountryName() {
        return country;
    }
    
    /**
     * 
     * @return a String representing the name of the user who created the database
     * record
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
        
    /**
     * Creates an instance of the Country class based on the key-value pairs where
     * the keys are the variable names for an instance of the Country class, and the
     * values are the values that are to be stored in those variable.
     * @param data a HashMap<String, Object> where the Strings represent the variable
     * names for an instance of the Country class, and the values are the values
     * that are to be stored in those variables.
     * @return a Country instance with all variables set to the passed-in data.
     * @throws Exception 
     */
    public static Country createCountryInstanceFromHashMap(HashMap<String, Object> data)
            throws Exception {
        if(data.containsKey(COUNTRY_ID) && data.get(COUNTRY_ID) instanceof Integer
                && data.containsKey(COUNTRY_NAME) && data.get(COUNTRY_NAME) instanceof String
                && data.containsKey(CREATE_DATE) && data.get(CREATE_DATE) instanceof Timestamp
                && data.containsKey(CREATED_BY) && data.get(CREATED_BY) instanceof String
                && data.containsKey(LAST_UPDATE) && data.get(LAST_UPDATE) instanceof Timestamp
                && data.containsKey(LAST_UPDATE_BY) && data.get(LAST_UPDATE_BY) instanceof String) {
            
            int id = (Integer)data.get(COUNTRY_ID);
            String name = (String)data.get(COUNTRY_NAME);
            Timestamp createDate = (Timestamp)data.get(CREATE_DATE);
            String createdBy = (String)data.get(CREATED_BY);
            Timestamp lastUpdate = (Timestamp)data.get(LAST_UPDATE);
            String lastUpdateBy = (String)data.get(LAST_UPDATE_BY);
            
            return new Country(id, name, createDate, createdBy, lastUpdate, lastUpdateBy);
        }
        
        throw new Exception("Error reading from array created from ResultSet row.");
    }
}
