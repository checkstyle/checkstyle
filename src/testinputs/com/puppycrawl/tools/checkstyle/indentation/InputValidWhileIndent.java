/*
 * InputValidWhileIndent.java
 *
 * Created on November 10, 2002, 9:16 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidWhileIndent {
    
    /** Creates a new instance of InputValidWhileIndent */
    public InputValidWhileIndent() {
    }
    
    private void method1()
    {
        boolean test = true;
        
        while (test) System.getProperty("foo");

        while (test) 
            System.getProperty("foo");
        
        while (test) {
        }

        while (test) 
        {
        }

        while (test) 
        {
            System.getProperty("foo");
        }

        while (test)  {
            System.getProperty("foo");
        }
        
        while (test)  {
            System.getProperty("foo");
            System.getProperty("foo");
        }
        
        while (test)  
        {
            System.getProperty("foo");
            System.getProperty("foo");
        }

        while (test)  {
            if (test) {
                System.getProperty("foo");
            }
            System.getProperty("foo");
        }
        
        while (test)
            System.getProperty("foo");

        if (test) {
            while (test)
                System.getProperty("foo");
        }
        
        while (test 
            && 4 < 7 && 8 < 9
            && 3 < 4) {
        }
        
    }
    
}
