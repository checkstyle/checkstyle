/*
 * InputValidBinaryOpIndent.java
 *
 * Created on December 2, 2002, 10:24 PM
 */

package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

/**
 *
 * @author jrichard
 */
public class InputValidBinaryOpIndent { //indent:0 exp:0
    
    /** Creates a new instance of InputValidBinaryOpIndent */
    public InputValidBinaryOpIndent() {
    }
    
    private void method1() {
        boolean test = true;
        int i, j, k = 4;
        int x = 4;
        int y = 7;
        
        i = x + k;
        
        j = y + x
            + k;
        
        int one = 1, two = 2, three = 3, four = 4;
        int answer;
        
        answer = (one + one) / 2;
        
        answer = (one
            + one) / 2;
        
        answer = (one + one) 
            / 2;
        
        answer = 
            (one + one)
            / 2;
        
        answer = 
            ((one + one)
                / 2) + 4;
        
        
        if (one + one == two) {
            System.out.println("true");
        }
        
        if (one + one
            == two) {
        }
        
        if (one + one == two && 1 + 1 == two) {
        }

        if (one + one == two 
            && 1 + 1 == two) {
        }
        
        if ((one 
            + one) == two && (1 + 1) == two) {
        }

        if ((one + one) 
            == two 
            && (1 + 1) == two) {
        }

        if ((one + one) 
            == two 
            && 
            (1 + 1) == two) {
        }
        
        
    }
    
}
