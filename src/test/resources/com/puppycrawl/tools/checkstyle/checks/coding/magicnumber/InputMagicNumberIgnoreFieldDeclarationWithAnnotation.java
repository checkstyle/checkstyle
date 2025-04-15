/*
MagicNumber
ignoreNumbers = 0.99
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = true
ignoreAnnotationElementDefaults = false
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

@InputMagicNumberIntMethodAnnotation(3) // violation ''3' is a magic number'
public class InputMagicNumberIgnoreFieldDeclarationWithAnnotation {
    public void createEvents(Double d, String s) {
        if ((d != null)  && (s != null && s.equalsIgnoreCase("Fiit"))) {
            double anotherDouble = d / 60; // violation ''60' is a magic number'
            if (anotherDouble >= 20) { // violation ''20' is a magic number'
                // do something
            }
        }
    }
}
