/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Utilities;

/**
 * An enum representing SQL Commands (which require no parameters to be passed)
 * that can be used in SQL statements. The command()method returns the name of 
 * the command plus an empty set of parentheses to be inserted directly into the
 * SQL statement.
 * 
 * <ul>
 * <li>NOW - NOW()</li>
 * </ul>
 * 
 * @author walterallen
 */
public enum SQLCommand {
    NOW;
            
    public String command() {
        return this.name() + "()";
    }
        
}
