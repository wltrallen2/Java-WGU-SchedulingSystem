/*
 * This program was developed as part of the course requirements
 * for the Software II - Advanced Java Concepts course 
 * (C192) at Western Governor's University for student Walter Allen, 
 * candidate for a B. S. in Software Development, graduation June 2020.
 */
package Utilities;

/**
 * Utility class with specialized String manipulation methods.
 * 
 * @author walterallen
 */
public class StringUtil {
    
    /**
     * Pads a string to the right utilizing the pad character to increase the
     * length of the string to the desired length. For example,
     * the word "Chapter 1" padded to 20 spots using the '.' character would
     * look like this: "Chapter 1..........." If the original string is longer
     * than the desired pad length, the method will return a truncated version
     * of the string. For example, "Chapter 1" padded to 5 spots would look like
     * this: "Chapt".
     * 
     * @param s the String to pad
     * @param numToPad an int representing the total length of the padded String
     * @param padChar a char representing the character to use to pad the String
     * @return a String consisting of the original string plus the pad character
     * repeated enough times to pad the string to the desired length, OR the original
     * string truncated to the desired pad length.
     */
    public static String rpad(String s, int numToPad, char padChar) {
        int numPaddedChars = numToPad - s.length();
        if(numPaddedChars > 0) {
            for (int i = 0; i < numPaddedChars; i++) {
                s += padChar;
            }
        } else {
            s = s.substring(0, numToPad);
        }
        
        return s;
    }
    
    /**
     * Pads a string to the left utilizing the pad character to increase the
     * length of the string to the desired length. For example,
     * the word "Chapter 1" padded to 20 spots using the '.' character would
     * look like this: "...........Chapter 1" If the original string is longer
     * than the desired pad length, the method will return a truncated version
     * of the string. For example, "Chapter 1" padded to 5 spots would look like
     * this: "Chapt".
     * 
     * @param s the String to pad
     * @param numToPad an int representing the total length of the padded String
     * @param padChar a char representing the character to use to pad the String
     * @return a String consisting of the pad character repeated enough times 
     * to pad the string to the desired length plus the original string, OR the original
     * string truncated to the desired pad length.
     */
    public static String lpad(String s, int numToPad, char padChar) {
        String paddedString = "";
        
        int numPaddedChars = numToPad - s.length();
        if(numPaddedChars > 0) {
            for (int i = 0; i < numPaddedChars; i++) {
                paddedString += padChar;
            }
            paddedString += s;
        } else {
            paddedString = s.substring(0, numToPad);
        }
        
        return paddedString;
    }    
}
