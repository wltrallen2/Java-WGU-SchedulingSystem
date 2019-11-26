/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Utilities;

/**
 *
 * @author walterallen
 */
public enum ASForm {
    TYPE_SUMMARY("Appointment Type Summary"),
    CONSULTANT_SCHEDULE("Consultant Schedule"),
    CUSTOMER_PHONES("Customer Phone Numbers");
    
    private final String title;
    
    private ASForm(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
}
