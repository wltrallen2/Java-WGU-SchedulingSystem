/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author walterallen
 */
public class Address {
    private final String TABLE_NAME = "address";
    private final String ADDRESS_ID = "addressid";
    private final String ADDRESS_LINE1 = "address";
    private final String ADDRESS_LINE2 = "address2";
    private final String CITY_ID = "cityId";
    private final String POSTAL_CODE = "postalCode";
    private final String PHONE = "phone";
    private final String CREATE_DATE = "createDate";
    private final String CREATED_BY = "createdBy";
    private final String LAST_UPDATE = "lastUpdate";
    private final String LAST_UPDATE_BY = "lastUpdateBy";
    
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
    public void init (int id, String address1, String address2, City city, 
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
    
    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the address id for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param id - an int representing the addressId in the database
     * @throws java.sql.SQLException
     */
    public void setAddressId(int id) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_ID, id, ADDRESS_ID, this.addressId);
        this.addressId = id;
    }
    
    /**
     * Sets the first line of the address for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param address - a String representing the first line of the address in the database
     * @throws java.sql.SQLException
     */
    public void setAddressLine1(String address) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_LINE1, address, ADDRESS_ID, this.addressId);
        this.address = address;
    }

    /**
     * Sets the second line of the address for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param address - a String representing the second line of the address in the database
     * @throws java.sql.SQLException
     */
    public void setAddressLine2(String address) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_LINE2, address, ADDRESS_ID, this.addressId);
        this.address2 = address;
    }

    /**
     * Sets the city for the Address object in both the object instance
     * and the city id in the database itself.
     * 
     * @param city - an instance of a City object representing 
     * the city for the given address
     * @throws java.sql.SQLException
     */
    public void setCityId(City city) throws SQLException {
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
     * 
     * @throws SQLException 
     */
    public void setPostalCode(String postalCode) throws SQLException {
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
     * 
     * @throws SQLException 
     */
    public void setPhone(String phoneNumber) throws SQLException {
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
     * @throws java.sql.SQLException
     */
    public void setCreateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, timestamp, ADDRESS_ID, this.addressId);
        this.createDate = timestamp;
    }

    /**
     * Sets the "created by" value for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param creator - a String object representing the creator 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreatedBy(String creator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, creator, ADDRESS_ID, this.addressId);
        this.createdBy = creator;
    }
    
    /**
     * Sets the date of the last update for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the date of the last update 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setUpdateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, timestamp, ADDRESS_ID, this.addressId);
        this.lastUpdate = timestamp;
    }

    /**
     * Sets the "updated by" value for the Address object in both the object instance
     * and in the database itself.
     * 
     * @param updator - a String object representing the name of the last user to update 
     * the database record
     * @throws java.sql.SQLException
     */
    public void setUpdatedBy(String updator) throws SQLException {
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
}
