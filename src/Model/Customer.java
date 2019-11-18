/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020
 */
package Model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author walterallen
 */
public class Customer {
    public static final String TABLE_NAME = "customer";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String ADDRESS_ID = "addressId";
    public static final String ACTIVE = "active";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";
    
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
    public Customer (int id, String name, Address address, Boolean active,
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
    
    /**
     * @return a String representation of the Customer object containing the
     * String value of the customerName property.
     */
    @Override
    public String toString() {
        return customerName + "\n" + address.toString();
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
    
    /***************************************************************************
     * PUBLIC STATIC METHODS
     **************************************************************************/        

    /**
     * Creates and returns an instance of a Customer object based on the paired values
     * in a HashMap, where the key is a String representing the names of variables
     * of a Customer object and the values are the values to be assigned to those variables.
     * 
     * @param data a HashMap<String, Object> representing the data to be used, where
     * the keys are the names of the variables in a Customer object, and the values are
     * Object instances representing the values to be passed. If the values are not
     * instances of the correct type, the data will not be accepted and an exception
     * will be thrown.
     * @return a Customer instance that contains the data that was passed into the method.
     * @throws Exception 
     */
    public static Customer createCustomerInstanceFromHashMap(HashMap<String, Object> data) 
            throws Exception {
        if(data.containsKey(CUSTOMER_ID) && data.get(CUSTOMER_ID) instanceof Integer
                && data.containsKey(CUSTOMER_NAME) && data.get(CUSTOMER_NAME) instanceof String
                && data.containsKey(ADDRESS_ID) && data.get(ADDRESS_ID) instanceof Integer
                && data.containsKey(ACTIVE) && data.get(ACTIVE) instanceof Boolean
                && data.containsKey(CREATE_DATE) && data.get(CREATE_DATE) instanceof Timestamp
                && data.containsKey(CREATED_BY) && data.get(CREATED_BY) instanceof String
                && data.containsKey(LAST_UPDATE) && data.get(LAST_UPDATE) instanceof Timestamp
                && data.containsKey(LAST_UPDATE_BY) && data.get(LAST_UPDATE_BY) instanceof String) {
            
            int id = (Integer)data.get(CUSTOMER_ID);
            String name = (String)data.get(CUSTOMER_NAME);
            int address_id = (Integer)data.get(ADDRESS_ID);
            Address address = AppointmentDatabase.getInstance().getAddressWithId(address_id);
            Boolean active = (Boolean)data.get(ACTIVE);
            Timestamp createDate = (Timestamp)data.get(CREATE_DATE);
            String createdBy = (String)data.get(CREATED_BY);
            Timestamp lastUpdate = (Timestamp)data.get(LAST_UPDATE);
            String lastUpdateBy = (String)data.get(LAST_UPDATE_BY);
            
            return new Customer(id, name, address, active, createDate,
                createdBy, lastUpdate, lastUpdateBy);
        }
        
        throw new Exception("Error reading from array created from ResultSet row.");
    }
}
