////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.coding;

/**
   Contains boolean logic that can be simplified.

   @author lkuehne
 */
public class InputSimplifyBoolean
{

    public static boolean isOddMillis()
    {
        boolean even = System.currentTimeMillis() % 2 == 0;

        // can be simplified to "if (even)"
        if (even == true) {
            return false;
        }
        else {
            return true;
        }
        // return can be simplified to "return !even"
    }

    public static boolean isOddMillis2()
    {
        boolean even = System.currentTimeMillis() % 2 == 0;
        // can be simplified to "return !even"
        if (!even)
            return true;
        else
            return false;
    }

    public static boolean giveMeTrue()
    {
        boolean tt = isOddMillis() || true;
        boolean ff = isOddMillis() && false;
        return !false || (true != false);
    }

    public void tryToProvokeNPE()
    {
        if (true) {
        }
        else {
        }

        if (true) {
            return;
        }
        else {
            return;
        }
    }

    public boolean ifNoElse()
    {
        if (isOddMillis()) {
            return true;
        }
        return false;
    }
    
    boolean a() {
        boolean asd = false;
        boolean dasa = true;
        
        if (asd) {
            return true;
        } else {
            return dasa;
        }
    }

    boolean b() {
        boolean asd = false;

        if(asd);
        else;
        
        return true;
    }
}
