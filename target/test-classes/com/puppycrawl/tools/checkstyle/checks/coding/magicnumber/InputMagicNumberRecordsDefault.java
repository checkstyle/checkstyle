/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberRecordsDefault {
    @anno(6) // violation ''6' is a magic number'
    public record MyRecord() {
        private static int myInt = 7; // violation ''7' is a magic number'

        public MyRecord{
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation ''8' is a magic number'
        }
        void foo() {
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation ''8' is a magic number'
        }

        public int hashCode() {
            return 10;    // violation ''10' is a magic number'
        }
    }

    @interface anno {
        int value() default 10; // no violation
    }

}

