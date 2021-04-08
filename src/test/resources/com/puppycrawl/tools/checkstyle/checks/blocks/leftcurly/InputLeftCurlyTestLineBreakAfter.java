package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/*
 * Config:
 * option = eol
 */
class InputLeftCurlyTestLineBreakAfter
{ // violation
    /** @see test method **/
    int foo() throws InterruptedException
    { // violation
        int x = 1;
        int a = 2;
        while (true)
        { // violation
            try
            { // violation
                if (x > 0)
                { // violation
                    break;
                }
                else if (x < 0) {
                    ;
                }
                else { break; } // violation
                switch (a)
                { // violation
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e) { break; } // violation
            finally { break; } // violation
        }

        synchronized (this) { do { x = 2; } while (x == 2); } // violation

        synchronized (this) {
            do {} while (x == 2);
        }

        for (int k = 0; k < 1; k++) { String innerBlockVariable = ""; } // violation

        for (int k = 0; k < 1; k++) {}
                return a;
    }

    static { int x = 1; } // violation

    void method2()
    { // violation
        boolean flag = false;
        if (flag) { String.valueOf("foo"); } // violation
    }
}

class Absent_CustomFieldSerializer1 {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer2
{ // violation
    public Absent_CustomFieldSerializer2() {}
}

class EmptyClass1 {}

interface EmptyInterface1 {}

enum KnownOrder { KNOWN_ORDER, UNKNOWN_ORDER }
