package Utilities;

/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */

/**
 * Exception representing the concept that the user has failed to enter
 * required information.
 * 
 * @author walterallen
 */
public class MissingInformationException extends Exception {
    
    /**
     * Constructor class
     * 
     * @param message a String representing the message that is to be passed
     * to the method that throws the exception.
     */
    public MissingInformationException(String message) {
        super(message);
    }
}
