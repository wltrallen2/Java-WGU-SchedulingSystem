/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import Utilities.DBQuery;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author walterallen
 */
public class Address {
    public static final String TABLE_NAME = "address";
    public static final String ADDRESS_ID = "addressId";
    public static final String ADDRESS_LINE1 = "address";
    public static final String ADDRESS_LINE2 = "address2";
    public static final String CITY_ID = "cityId";
    public static final String POSTAL_CODE = "postalCode";
    public static final String PHONE = "phone";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";
    
    private int addressId;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     * Creates an instance of an Address object based on the uCertify database
     * model.
     * 
     * @param id - an int representing the address id number
     * @param address1 - a String representing the first line of the address
     * @param address2 - a String representing the second line of the address
     * @param city - an instance of a City object representing the city
     * for the given address
     * @param postalCode - a String representing the postal code for the address
     * @param phoneNumber - a String representing the phone number associated
     * with this address
     * @param createDate - a Timestamp object representing the date and time the
     * database record was created
     * @param creatorName - a String representing the name of the database user
     * who created the record
     * @param updateDate - a Timestamp object representing the date and time that
     * the database record was last updated.
     * @param updatorName - a String representing the name of the database user
     * who last updated the record.
     */
    public Address (int id, String address1, String address2, City city, 
            String postalCode, String phoneNumber,
            Timestamp createDate, String creatorName, 
            Timestamp updateDate, String updatorName) {
        this.addressId = id;
        this.address = address1;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phoneNumber;
        this.createDate = createDate;
        this.createdBy = creatorName;
        this.lastUpdate = updateDate;
        this.lastUpdateBy = updatorName;
    }
        
    @Override
    public String toString() {
        return address + "\n" + address2 + "\n" + city.getCityName() + ", "
                + city.getCountry().getCountryName() + " " + postalCode
                + "\n" + phone;
    }

    
    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the address id for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param id - an int representing the addressId in the database
     */
    public void setAddressId(int id) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_ID, id, ADDRESS_ID, this.addressId);
        this.addressId = id;
    }
    
    /**
     * Sets the first line of the address for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param address - a String representing the first line of the address in the database
     */
    public void setAddressLine1(String address) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_LINE1, address, ADDRESS_ID, this.addressId);
        this.address = address;
    }

    /**
     * Sets the second line of the address for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param address - a String representing the second line of the address in the database
     */
    public void setAddressLine2(String address) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_LINE2, address, ADDRESS_ID, this.addressId);
        this.address2 = address;
    }

    /**
     * Sets the city for the Address object in both the object instance
     * and the city id in the database itself.
     * 
     * @param city - an instance of a City object representing 
     * the city for the given address
     */
    public void setCityId(City city) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CITY_ID, city.getCityId(),
                ADDRESS_ID, this.addressId);
        this.city = city;
    }
    
    /**
     * Sets the postal code for the Address object in both the object instance
     * and the database itself.
     * 
     * @param postalCode - a String representing the postal code for the
     * given address
     */
    public void setPostalCode(String postalCode) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, POSTAL_CODE, postalCode,
                ADDRESS_ID, addressId);
        this.postalCode = postalCode;
    }
    
    /**
     * Sets the phone number for the Address object in both the object instance
     * and the database itself.
     * 
     * @param phoneNumber - a String representing the phone number associated with the
     * given address
     */
    public void setPhone(String phoneNumber) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, PHONE, phoneNumber,
                ADDRESS_ID, addressId);
        this.phone = phoneNumber;
    }

    /**
     * Sets the creation date for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the creation date 
     * of the database record
     */
    public void setCreateDate(Timestamp timestamp) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, timestamp, ADDRESS_ID, this.addressId);
        this.createDate = timestamp;
    }

    /**
     * Sets the "created by" value for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param creator - a String object representing the creator 
     * of the database record
     */
    public void setCreatedBy(String creator) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, creator, ADDRESS_ID, this.addressId);
        this.createdBy = creator;
    }
    
    /**
     * Sets the date of the last update for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the date of the last update 
     * of the database record
     */
    public void setUpdateDate(Timestamp timestamp) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, timestamp, ADDRESS_ID, this.addressId);
        this.lastUpdate = timestamp;
    }

    /**
     * Sets the "updated by" value for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param updator - a String object representing the name of the last user to update 
     * the database record
     */
    public void setUpdatedBy(String updator) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE_BY, updator, ADDRESS_ID, this.addressId);
        this.lastUpdateBy = updator;
    }

    /***************************************************************************
     * GETTERS
     **************************************************************************/
    
    /**
     * 
     * @return an int representing the address id.
     */
    public int getAddressId() {
        return addressId;
    }
    
    /**
     * 
     * @return a String representing the first line of the address.
     */
    public String getAddressLine1() {
        return address;
    }
    
    /**
     * 
     * @return a String representing the second line of the address.
     */
    public String getAddressLine2() {
        return address2;
    }
    
    /**
     * 
     * @return an instance of a City object representing the city associated
     * with this address.
     */
    public City getCity() {
        return city;
    }
    
    /**
     * 
     * @return a String representing the postal code for this address.
     */
    public String getPostalCode() {
        return postalCode;
    }
    
    /**
     * 
     * @return a String representing the phone number associated with this
     * address.
     */
    public String getPhone() {
        return phone;
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
     * Creates and returns an instance of an Address object based on the paired values
     * in a HashMap, where the key is a String representing the names of variables
     * of a Address object and the values are the values to be assigned to those variables.
     * 
     * @param data a HashMap<String, Object> representing the data to be used, where
     * the keys are the names of the variables in an Address object, and the values are
     * Object instances representing the values to be passed. If the values are not
     * instances of the correct type, the data will not be accepted and an exception
     * will be thrown.
     * @return an Address instance that contains the data that was passed into the method.
     * @throws Exception 
     */
    public static Address createAddressInstanceFromHashMap(HashMap<String, Object> data) 
            throws Exception {
        if(data.containsKey(ADDRESS_ID) && data.get(ADDRESS_ID) instanceof Integer
                && data.containsKey(ADDRESS_LINE1) && data.get(ADDRESS_LINE1) instanceof String
                && data.containsKey(ADDRESS_LINE2) && data.get(ADDRESS_LINE2) instanceof String
                && data.containsKey(CITY_ID) && data.get(CITY_ID) instanceof Integer
                && data.containsKey(POSTAL_CODE) && data.get(POSTAL_CODE) + "" instanceof String
                && data.containsKey(PHONE) && data.get(PHONE) + "" instanceof String
                && data.containsKey(CREATE_DATE) && data.get(CREATE_DATE) instanceof Timestamp
                && data.containsKey(CREATED_BY) && data.get(CREATED_BY) instanceof String
                && data.containsKey(LAST_UPDATE) && data.get(LAST_UPDATE) instanceof Timestamp
                && data.containsKey(LAST_UPDATE_BY) && data.get(LAST_UPDATE_BY) instanceof String) {
            
            int id = (Integer)data.get(ADDRESS_ID);
            String address1 = (String)data.get(ADDRESS_LINE1);
            String address2 = (String)data.get(ADDRESS_LINE2);
            int cityId = (Integer)data.get(CITY_ID);
            City city = AppointmentDatabase.getInstance().getCityWithId(cityId);
            String zip = "" + (String)data.get(POSTAL_CODE);
            String phone = "" + (String)data.get(PHONE);
            Timestamp createDate = (Timestamp)data.get(CREATE_DATE);
            String createdBy = (String)data.get(CREATED_BY);
            Timestamp lastUpdate = (Timestamp)data.get(LAST_UPDATE);
            String lastUpdateBy = (String)data.get(LAST_UPDATE_BY);
            
            return new Address(id, address1, address2, city, zip, phone,
                    createDate, createdBy, lastUpdate, lastUpdateBy);
                
        }
        
        throw new Exception("Error reading from array created from ResultSet row.");
    }
}
