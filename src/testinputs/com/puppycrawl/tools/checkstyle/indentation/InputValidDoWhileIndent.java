/*
 * InputvalidDoWhileIndent.java
 *
 * Created on December 8, 2002, 9:05 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidDoWhileIndent {
    
    public InputValidDoWhileIndent() {
    }

    
    public void method1() {
        boolean test = true;
        
        do System.getProperty("foo"); while (test) ;

        do System.getProperty("foo");
        while (test);
        
        do {
        } while (test);

        do 
        {
        }
        while (test);

        do 
        {
            System.getProperty("foo");
        }
        while (test);

        do 
        { System.getProperty("foo"); }
        while (test);

        do {
            System.getProperty("foo");
        } while (test);
        
        do {
            System.getProperty("foo");
            System.getProperty("foo");
        } while (test);
        
        do  
        {
            System.getProperty("foo");
            System.getProperty("foo");
        }
        while (test);
        
        do {
            if (test) {
                System.getProperty("foo");
            }
            System.getProperty("foo");
        } while (test);
        
        do
            System.getProperty("foo");
        while (test);

        if (test) {
            do
                System.getProperty("foo");
            while (test);
        }
        
        do 
        {
        }
        while (test
            && 7 < 8 && 5 < 6
            && 9 < 10);
    }
}
