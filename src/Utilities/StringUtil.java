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
public class StringUtil {
    
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
