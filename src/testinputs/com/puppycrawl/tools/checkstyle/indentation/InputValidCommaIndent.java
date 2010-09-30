/*
 * InputValidCommaIndent.java
 *
 * Created on December 1, 2002, 2:20 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidCommaIndent {
    
    /** Creates a new instance of InputValidCommaIndent */
    public InputValidCommaIndent() {
    }
   
    public void method1(int x, int y, int z) {
        boolean test = true;        
        int i, j = 2, 
            k = 4,
            l,
            m = 4;

        boolean longVarName = true;
        boolean myotherLongVariableName = false;
        if (j == 2 || longVarName == true || myotherLongVariableName == true || myotherLongVariableName == false || longVarName == true) {
        }
        
        if ((j == 2 && k == 3)
              || test) {
            System.out.println("test");
        }
        
        
    }
    
    public void method1(int a, int x, 
        int y, int z) {
    }
}
