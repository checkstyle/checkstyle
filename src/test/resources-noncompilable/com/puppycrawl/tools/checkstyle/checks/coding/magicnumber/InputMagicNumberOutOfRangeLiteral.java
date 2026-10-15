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

// non-compiled with javac: integer number too large

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberOutOfRangeLiteral {
    void method() {
        int a = 99999999999; // violation ''99999999999' is a magic number'
        int b = 0x1ffffffff; // violation ''0x1ffffffff' is a magic number'
        long c = 18446744073709551616L;
        // violation above ''18446744073709551616L' is a magic number'
    }
}
