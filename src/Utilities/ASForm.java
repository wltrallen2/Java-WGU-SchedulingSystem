/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Utilities;

/**
 * Enum representing the types of reports that the user can request. Each
 * enum includes a title parameter which describes the report more fully.
 * <ul>
 * <li>TYPE_SUMMARY - Appointment Type Summary</li>
 * <li>CONSULTANT_SCHEDULE - Consultant Schedule</li>
 * <li>CUSTOMER_PHONES - Customer Phone Numbers</li>
 * </ul>
 * 
 * @author walterallen
 */
public enum ASForm {
    TYPE_SUMMARY("Appointment Type Summary"),
    CONSULTANT_SCHEDULE("Consultant Schedule"),
    CUSTOMER_PHONES("Customer Phone Numbers");
    
    private final String title;
    
    /**
     * Constructor class
     * 
     * @param title a String that represents the title of the report.
     */
    private ASForm(String title) {
        this.title = title;
    }
    
    /**
     * 
     * @return a String representing the title of the report.
     */
    public String getTitle() {
        return title;
    }
}
