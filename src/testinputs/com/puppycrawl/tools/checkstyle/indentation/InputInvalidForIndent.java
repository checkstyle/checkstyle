/*
 * InputValidForIndent.java
 *
 * Created on November 10, 2002, 10:04 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputInvalidForIndent {
    
    /** Creates a new instance of InputValidForIndent */
    public InputInvalidForIndent() {
    }
    
    
    private void method1()
    {
      for (int i=0; i<10; i++) {
          }

         for (int i=0; i<10; i++) 
      {
      }

        for (int i=0; i<10; i++) 
        {
          System.getProperty("foo");
          }

        for (int i=0; i<10; i++) 
          {
          boolean test = true;
            if (test) { // mixed styles are OK
                System.getProperty("foo");
            }
        }

        for (
            int i=0; 
          i<10; 
            i++) 
        {
            
        }
      
       for (
          int i=0; 
            i<10; 
            i++) 
        {
            
        }
      
        for (int i=0; 
            i<10; 
       i++) 
        {
            
        }

      for (int i=0; 
          i<10 && 4<5
              && 7<8; 
          i++) 
        {
        }
      
        for (int i=0; i<10; i++) {
            System.getProperty("foo"); }

        for (int i=0; 
            i<10; i++
            ) {
            System.getProperty("foo");
        }
    }
    
}
