/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *
 * @author walterallen
 */
public class Appointment {
    public static final String TABLE_NAME = "appointment";
    public static final String APPOINTMENT_ID = "appointmentId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String CONTACT = "contact";
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String START_TIME = "start";
    public static final String END_TIME = "end";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";
    
    private int appointmentId;
    private Customer customer;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     * Creates an instance of an Appointment object based on the UCertify database
     * model.
     * 
     * @param apptId an int representing the appointment ID
     * @param customer a Customer instance representing the customer linked to
     * this appointment
     * @param userId an int representing the user ID
     * @param title a String representing the title of the appointment
     * @param desc a String representing a description of the appointment
     * @param loc a String representing the location of the appointment
     * @param contact a String representing the contact for the appointment
     * @param type a String representing the type of appointment
     * @param url a String representing a url reference for the appointment
     * @param start a Timestamp representing the start time for the appointment
     * @param end a Timestamp representing the end time for the appointment
     * @param createDate a Timestamp representing the date and time the appointment
     * was created
     * @param createdBy a String representing the name of the user that created the
     * appointment
     * @param lastUpdate a Timestamp representing the date and time the appointment
     * was last updated
     * @param lastUpdateBy a String representing the name of the user the last updated
     * the appointment
     */
    public Appointment(int apptId, Customer customer, int userId, String title, String desc,
            String loc, String contact, String type, String url, Timestamp start, Timestamp end,
            Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.appointmentId = apptId;
        this.customer = customer;
        this.userId = userId;
        this.title = title;
        this.description = desc;
        this.location = loc;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    /**
     * A String representation of the appointment that includes the following
     * information: title, customer name, description, location, start, and end times.
     * @return 
     */
    @Override
    public String toString() {
        return "Appointment: " + title + "\n"
                + customer.getCustomerName() + "\n"
                + description + "\n"
                + location + ", " + start + " - " + end;
    }
    
    /**
     * Sets the lastUpdate property to the current date and time, and sets the
     * lastUpdatedBy property to the current user name.
     */
    private void setUpdatedValues() {
        setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
        setLastUpdateBy(AppointmentDatabase.getInstance().getUserName());
    }

    /***************************************************************************
     * SETTERS
     **************************************************************************/

    /**
     * Sets the appointment id for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param apptId - an int representing the appointmentId in the database
     */
    public void setAppointmentId(int apptId) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, APPOINTMENT_ID, apptId,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.appointmentId = apptId;
    }
    
    /**
     * Sets the customer for the Appointment object in the object instance
     * and the customerId in the database itself.
     * 
     * @param customer - a Customer representing the customer linked to this appointment
     */
    public void setCustomer(Customer customer) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CUSTOMER_ID, customer.getCustomerId(),
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.customer = customer;
    }
    
    /**
     * Sets the user id for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param userId - an int representing the userId connected to this appointment
     */
    public void setUserId(int userId) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, USER_ID, userId,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.userId = userId;
    }
    
    /**
     * Sets the title for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param title - a String representing the title of the appointment
     */
    public void setTitle(String title) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, TITLE, title,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.title = title;
    }
    
    /**
     * Sets the description for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param desc - a String representing the description of the appointment
     */
    public void setDescription(String desc) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, DESCRIPTION, desc,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.description = desc;
    }
    
    /**
     * Sets the location for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param loc - a String representing the location of the appointment
     */
    public void setLocation(String loc) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LOCATION, loc,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.location = loc;
    }
    
    /**
     * Sets the contact for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param contact - a String representing the contact for the appointment
     */
    public void setContact(String contact) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CONTACT, contact,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.contact = contact;
    }
    
    /**
     * Sets the type for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param type - a String representing the type of the appointment
     */
    public void setType(String type) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, TYPE, type,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.type = type;
    }
    
    /**
     * Sets the url for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param url - a String representing the url related to the appointment
     */
    public void setURL(String url) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, URL, url,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.url = url;
    }
    
    /**
     * Sets the start time for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param start - a Timestamp representing the start time of the appointment
     */
    public void setStart(Timestamp start) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, START_TIME, start,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.start = start;
    }
    
    /**
     * Sets the end time for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param end - a Timestamp representing the end time of the appointment
     */
    public void setEnd(Timestamp end) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, END_TIME, end,
                APPOINTMENT_ID, this.appointmentId);
        setUpdatedValues();
        this.end = end;
    }
    
    /**
     * Sets the create date for the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param date - a Timestamp representing the time and date the appointment was created
     */
    public void setCreateDate(Timestamp date) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATE_DATE, createDate,
                APPOINTMENT_ID, this.appointmentId);
        this.createDate = date;
    }
    
    /**
     * Sets the user name that created the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param user - a String representing the creator of the appointment
     */
    public void setCreatedBy(String user) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, CREATED_BY, createdBy,
                APPOINTMENT_ID, this.appointmentId);
        this.createdBy = user;
    }
    
    /**
     * Sets the time that the Appointment object was last updated in both the object instance
     * and in the database itself.
     * 
     * @param date - a Timestamp representing the time the appointment was
     * last updated
     */
    public void setLastUpdate(Timestamp date) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE, lastUpdate,
                APPOINTMENT_ID, this.appointmentId);
        this.lastUpdate = date;
    }
    
    /**
     * Sets the user name that last updated the Appointment object in both the object instance
     * and in the database itself.
     * 
     * @param user - a String representing the user that last updated the appointment
     */
    public void setLastUpdateBy(String user) {
        DBQuery.updateSingleValueInDatabase(TABLE_NAME, LAST_UPDATE_BY, lastUpdateBy,
                APPOINTMENT_ID, this.appointmentId);
        this.lastUpdateBy = user;
    }
    
    /***************************************************************************
     * GETTERS
     **************************************************************************/

    /**
     * 
     * @return an int representing the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }
   
    /**
     * 
     * @return a Customer representing the customer linked to the appointment
     */
    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * 
     * @return an int representing the user Id for the appointment
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * 
     * @return a String representing the title of the appointment
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 
     * @return a String representing a description of the appointment
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @return a String representing the location of the appointment
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * 
     * @return a String representing the contact for the appointment
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * 
     * @return a String representing the type of appointment
     */
    public String getType() {
        return type;
    }
    
    /**
     * 
     * @return a String representing a URL related to the appointment.
     */
    public String getURL() {
        return url;
    }
    
    /**
     * 
     * @return a Timestamp representing the start time of the appointment
     */
    public Timestamp getStart() {
        return start;
    }
    
    /**
     * 
     * @return a Timestamp representing the end time of the appointment
     */
    public Timestamp getEnd() {
        return end;
    }
    
    /**
     * 
     * @return a Timestamp representing the date and time the appointment was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    
    /**
     * 
     * @return a String representing the user that created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }
    
    /**
     * 
     * @return a Timestamp representing the date and time the appointment was 
     * last updated
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    
    /**
     * 
     * @return a String that represents the user that last updated the appointment
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    /***************************************************************************
     * PUBLIC STATIC METHODS
     **************************************************************************/

    /**
     * Creates and returns an instance of an Appointment object based on the paired values
     * in a HashMap, where the key is a String representing the names of variables
     * of an Appointment object and the values are the values to be assigned to those variables.
     * 
     * @param data a HashMap<String, Object> representing the data to be used, where
     * the keys are the names of the variables in an Appointment object, and the values are
     * Object instances representing the values to be passed. If the values are not
     * instances of the correct type, the data will not be accepted and an exception
     * will be thrown.
     * @return an Appointment instance that contains the data that was passed into the method.
     * @throws Exception 
     */
    public static Appointment createAppointmentInstanceFromHashMap(HashMap<String, Object> data) 
            throws Exception {
        if(data.containsKey(APPOINTMENT_ID) && data.get(APPOINTMENT_ID) instanceof Integer
                && data.containsKey(CUSTOMER_ID) && data.get(CUSTOMER_ID) instanceof Integer
                && data.containsKey(USER_ID) && data.get(USER_ID) instanceof Integer
                && data.containsKey(TITLE) && data.get(TITLE) instanceof String
                && data.containsKey(DESCRIPTION) && data.get(DESCRIPTION) instanceof String
                && data.containsKey(LOCATION) && data.get(LOCATION) instanceof String
                && data.containsKey(CONTACT) && data.get(CONTACT) instanceof String
                && data.containsKey(TYPE) && data.get(TYPE) instanceof String
                && data.containsKey(URL) && data.get(URL) instanceof String
                && data.containsKey(START_TIME) && data.get(START_TIME) instanceof Timestamp
                && data.containsKey(END_TIME) && data.get(END_TIME) instanceof Timestamp
                && data.containsKey(CREATE_DATE) && data.get(CREATE_DATE) instanceof Timestamp
                && data.containsKey(CREATED_BY) && data.get(CREATED_BY) instanceof String
                && data.containsKey(LAST_UPDATE) && data.get(LAST_UPDATE) instanceof Timestamp
                && data.containsKey(LAST_UPDATE_BY) && data.get(LAST_UPDATE_BY) instanceof String) {
            
            int id = (Integer)data.get(APPOINTMENT_ID);
            int custId = (Integer)data.get(CUSTOMER_ID);
            int userId = (Integer)data.get(USER_ID);
            String title = (String)data.get(TITLE);
            String description = (String)data.get(DESCRIPTION);
            String location = (String)data.get(LOCATION);
            String contact = (String)data.get(CONTACT);
            String type = (String)data.get(TYPE);
            String url = (String)data.get(URL);
            Timestamp start = (Timestamp)data.get(START_TIME);
            Timestamp end = (Timestamp)data.get(END_TIME);
            Timestamp createDate = (Timestamp)data.get(CREATE_DATE);
            String createdBy = (String)data.get(CREATED_BY);
            Timestamp lastUpdate = (Timestamp)data.get(LAST_UPDATE);
            String lastUpdateBy = (String)data.get(LAST_UPDATE_BY);
            
            Customer customer = AppointmentDatabase.getInstance().getCustomerWithId(custId);
            
            return new Appointment(id, customer, userId, title, description, location,
                contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy);
                
        }
        
        throw new Exception("Error reading from array created from ResultSet row.");
    }
}
