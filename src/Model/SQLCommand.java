/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Model;

/**
 *
 * @author walterallen
 */
public enum SQLCommand {
    NOW;
    
    private final String command;
    SQLCommand() {
        command = this.name() + "()";
    }
    
    public String command() {
        return command;
    }
        
}
