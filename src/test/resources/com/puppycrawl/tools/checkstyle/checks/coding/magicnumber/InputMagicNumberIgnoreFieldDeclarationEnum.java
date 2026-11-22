/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = true
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreFieldDeclarationEnum {
    private int classField = 42;

    enum Size {
        SMALL(3),
        LARGE(9) {
            private int constantBodyField = 42;
        };

        private int enumField = 42;
        private static int staticField = 43;
        private final int[] arrayField = {44, 45};

        Size(int value) {
            int local = value + 46; // violation ''46' is a magic number'
        }

        int size() {
            return enumField * 47; // violation ''47' is a magic number'
        }
    }
}
