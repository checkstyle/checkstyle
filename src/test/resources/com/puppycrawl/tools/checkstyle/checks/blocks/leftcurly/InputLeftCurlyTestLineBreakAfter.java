/*
LeftCurly
option = (default)EOL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

class InputLeftCurlyTestLineBreakAfter
{ // violation ''{' at column 1 should be on the previous line'
    /** @see test method **/
    int foo() throws InterruptedException
    { // violation ''{' at column 5 should be on the previous line'
        int x = 1;
        int a = 2;
        while (true)
        { // violation ''{' at column 9 should be on the previous line'
            try
            { // violation ''{' at column 13 should be on the previous line'
                if (x > 0)
                { // violation ''{' at column 17 should be on the previous line'
                    break;
                }
                else if (x < 0) {
                    ;
                }
                else { break; } // violation ''{' at column 22 should have line break after'
                switch (a)
                { // violation ''{' at column 17 should be on the previous line'
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e) { ; } // violation ''{' at column 33 should have line break after'
            finally { break; } // violation ''{' at column 21 should have line break after'
        }

        synchronized (this) { do { x = 2; } while (x == 2); } // 2 violations

        synchronized (this) {
            do {} while (x == 2);
        }
        // violation below ''{' at column 37 should have line break after'
        for (int k = 0; k < 1; k++) { String innerBlockVariable = ""; }

        for (int k = 0; k < 1; k++) {}
                return a;
    }

    static { int x = 1; } // violation ''{' at column 12 should have line break after'

    void method2()
    { // violation ''{' at column 5 should be on the previous line'
        boolean flag = false;
        if (flag) { int k = 0; } // violation ''{' at column 19 should have line break after'
    }
}

class Absent_CustomFieldSerializer1 {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer2
{ // violation ''{' at column 1 should be on the previous line'
    public Absent_CustomFieldSerializer2() {}
}

class EmptyClass1 {}

interface EmptyInterface1 {}

enum KnownOrder { KNOWN_ORDER, UNKNOWN_ORDER }
