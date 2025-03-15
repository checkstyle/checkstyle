/*
SimplifyBooleanExpression


*/

package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanexpression;

/**
   Contains boolean logic that can be simplified.

   @author lkuehne
 */
public class InputSimplifyBooleanExpression
{

    public static boolean isOddMillis()
    {
        boolean even = System.currentTimeMillis() % 2 == 0;

        // can be simplified to "if (even)"
        if (even == true) { // violation, can be simplified to even
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
        boolean tt = isOddMillis() || true; // violation, can be simplified to true
        boolean ff = isOddMillis() && false; // violation, can be simplified to false
        return !false || (true != false); // 2 violations
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

    void testTernaryExpressions() {
        boolean a = false;
        boolean b = true;
        int c = 13;
        boolean m = c > 1 ? true : false; // violation, can be simplified to c > 1
        boolean e = (a == true) // violation, can be simplified to a
                ? c > 1 : false;
        boolean h = false ? c > 13 : c < 21; // violation, can be simplified to c < 21
        boolean f = a == b ? false : c > 1;
        boolean q = c > 1 ? (c < 15
                ? false : b)
                : a != b;
        boolean v = c > 0 ? true :
                c < 0 ? false : true; // violation, can be simplified to true
        boolean g = (c > 0 ? true : c < 0)
                ? false : false; // violation, can be simplified to false
        Boolean value = null;
        boolean temp = value != null ? value : false;
        temp = true ? a() : b(); // violation, can be simplified to a()
        int d = false ? 1 : 2; // violation, can be simplified to 2
        temp = a() ? true : true; // violation, can be simplified to true
        temp = value != null ? value : (false);
    }
}
