/*
 * InputValidBlockIndent.java
 *
 * Created on December 8, 2002, 12:06 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidBlockIndent {
    
    /** Creates a new instance of InputValidBlockIndent */
    public InputValidBlockIndent() {
    }
    
    public void method1() {
        
        { }
        {
        }
        {
            int var = 3;
            
            var += 3;
        }

        
        {  int var = 5; }
    
        {
            int var = 3;
            
            var += 3;
            
            {
                int innerVar = 4;
                
                innerVar += var;
            }
        }
    
    }
    
    static { int var = 4; }

    
    static {
        int var = 4; 
    }
    
    static 
    {
        int var = 4; 
    }

    { int var = 4; }

    
    {
        int var = 4; 
    }
    
    {
        int var = 4; 
    }
    
    
}
