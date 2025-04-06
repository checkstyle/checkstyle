/*
MagicNumber
ignoreNumbers = -2, -1, 0, 1, 2, 100
ignoreAnnotation = (default)false
ignoreFieldDeclaration = true
constantWaiverParentToken = ARRAY_INIT, ASSIGN, ELIST
tokens = (default)NUM_DOUBLE,NUM_FLOAT,NUM_INT,NUM_LONG
ignoreAnnotationElementDefaults = (default)true
ignoreHashCodeMethod = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreFieldDeclaration6 {

    @interface MyAnnotation {
        int value() default 5; // no violation

        public static int CONSTANT = 10; // violation ''10' is a magic number'
        public static int ANOTHER_CONSTANT = 15; // violation ''15' is a magic number'
    }

    static int regularField = 42; // no violation
}
