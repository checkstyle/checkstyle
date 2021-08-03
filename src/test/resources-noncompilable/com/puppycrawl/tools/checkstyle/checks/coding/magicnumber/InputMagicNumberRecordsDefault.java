/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberRecordsDefault {
    @anno(6) // violation
    public record MyRecord() {
        private static int myInt = 7; // violation

        public MyRecord{
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation
        }
        void foo() {
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation
        }

        public int hashCode() {
            return 10;    // violation
        }
    }

    @interface anno {
        int value() default 10; // no violation
    }

}

