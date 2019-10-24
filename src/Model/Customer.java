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
public class Customer {
    private final String TABLE_NAME = "customer";
    private final String CUSTOMER_ID = "customerId";
    private final String CUSTOMER_NAME = "cusomterName";
    private final String ADDRESS_ID = "addressId";
    private final String ACTIVE = "active";
    private final String CREATE_DATE = "createDate";
    private final String CREATED_BY = "createdBy";
    private final String LAST_UPDATE = "lastUpdate";
    private final String LAST_UPDATE_BY = "lastUpdateBy";
    
    private int customerId;
    private String customerName;
    private Address address;
    private Boolean active;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     * Creates an instance of a Customer object based on the uCertify database
     * model.
     * 
     * @param id - an int representing the customer id number
     * @param name - a String representing the customer name
     * @param address - an instance of an Address object representing the address
     * for the given customer
     * @param active - a Boolean, true if the customer is active and false if the
     * customer in inactive
     * @param createDate - a Timestamp object representing the date and time the
     * database record was created
     * @param creatorName - a String representing the name of the database user
     * who created the record
     * @param updateDate - a Timestamp object representing the date and time that
     * the database record was last updated.
     * @param updatorName - a String representing the name of the database user
     * who last updated the record.
     */
    public void init (int id, String name, Address address, Boolean active,
            Timestamp createDate, String creatorName, 
            Timestamp updateDate, String updatorName) {
        this.customerId = id;
        this.customerName = name;
        this.address = address;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = creatorName;
        this.lastUpdate = updateDate;
        this.lastUpdateBy = updatorName;
    }
    
    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the customer id for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param id - an int representing the customerId in the database
     * @throws java.sql.SQLException
     */
    public void setCustomerId(int id) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CUSTOMER_ID, id, CUSTOMER_ID, this.customerId);
        this.customerId = id;
    }
    
    /**
     * Sets the customer name for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param name - a String representing the customer name in the database
     * @throws java.sql.SQLException
     */
    public void setcustomerName(String name) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CUSTOMER_NAME, name, CUSTOMER_ID, this.customerId);
        this.customerName = name;
    }

    /**
     * Sets the address for the Customer object in both the object instance
     * and the country id in the database itself.
     * 
     * @param address - an instance of an Address object representing 
     * the address for the given customer
     * @throws java.sql.SQLException
     */
    public void setAddressId(Address address) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ADDRESS_ID, address.getAddressId(),
                CUSTOMER_ID, this.customerId);
        this.address = address;
    }
    
    /**
     * Sets the boolean to true or false representing the Customer's active status
     * and sets the value to 1 for true or 0 for false in the corresponding database record.
     * 
     * @param active - a Boolean, true if the customer is active and false if
     * the customer is inactive
     * @throws SQLException 
     */
    public void setActive(Boolean active) throws SQLException {
        int activeInt = active ? 1 : 0;
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, ACTIVE, activeInt, CUSTOMER_NAME, customerId);
        this.active = active;
    }
    
    /**
     * Sets the creation date for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the creation date 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, timestamp, CUSTOMER_ID, this.customerId);
        this.createDate = timestamp;
    }

    /**
     * Sets the "created by" value for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param creator - a String object representing the creator 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setCreatedBy(String creator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, creator, CUSTOMER_ID, this.customerId);
        this.createdBy = creator;
    }
    
    /**
     * Sets the date of the last update for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param timestamp - a TimeStamp object representing the date of the last update 
     * of the database record
     * @throws java.sql.SQLException
     */
    public void setUpdateDate(Timestamp timestamp) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, timestamp, CUSTOMER_ID, this.customerId);
        this.lastUpdate = timestamp;
    }

    /**
     * Sets the "updated by" value for the Customer object in both the object instance
     * and in the database itself.
     * 
     * @param updator - a String object representing the name of the last user to update 
     * the database record
     * @throws java.sql.SQLException
     */
    public void setUpdatedBy(String updator) throws SQLException {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE_BY, updator, CUSTOMER_ID, this.customerId);
        this.lastUpdateBy = updator;
    }

    /***************************************************************************
     * GETTERS
     **************************************************************************/
    
    /**
     * 
     * @return an int representing the customer id.
     */
    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * 
     * @return a String representing the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * 
     * @return an instance of an Address object representing the address associatd
     * with the given customer.
     */
    public Address getAddress() {
        return address;
    }
    
    /**
     * 
     * @return a boolean, true if the customer is active or false if the customer
     * is inactive
     */
    public Boolean isActive() {
        return active;
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
