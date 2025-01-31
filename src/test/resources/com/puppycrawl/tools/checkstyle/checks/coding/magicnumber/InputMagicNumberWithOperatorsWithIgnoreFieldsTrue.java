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

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberWithOperatorsWithIgnoreFieldsTrue {

    public static final int BIT0 = 1 << 3; //constant field
    public static final int BIT1 = 1 >> 3;
    public static final int BIT2 = 1 >>> 3;
    public static final int BIT3 = 1 | 3;
    public static final int BIT4 = 1 & 3;
    public static final int BIT5 = 1 ^ 3;
    public static final int BIT6 = 1 | 3;
    public static final int BIT7 = ~3;
    public static final boolean BIT8 = 1 != 3;
    public static final boolean BIT9 = 1 > 3;
    public static final int BIT10 = 1 % 3;
    public static final int BIT11 = 1 > 3 ? 1 : 3;
    public static final int BIT12 = 1 % 3;

    public static int bit0 = 1 << 3; // violation, ''3' is a magic number'
    public static int bit1 = 1 >> 3; // violation, ''3' is a magic number'
    public static int bit2 = 1 >>> 3; // violation, ''3' is a magic number'
    public static int bit3 = 1 | 3; // violation, ''3' is a magic number'
    public static int bit4 = 1 & 3; // violation, ''3' is a magic number'
    public static int bit5 = 1 ^ 3; // violation, ''3' is a magic number'
    public static int bit6 = 1 | 3; // violation, ''3' is a magic number'
    public static int bit7 = ~3; // violation, ''3' is a magic number'

    void m1() {
        final int BIT0 = 1 << 3; //constant variable
        final int BIT1 = 1 >> 3;
        final int BIT2 = 1 >>> 3;
        final int BIT3 = 1 | 3;
        final int BIT4 = 1 & 3;
        final int BIT5 = 1 ^ 3;
        final int BIT6 = 1 | 3;
        final int BIT7 = ~3;

        int bit0 = 1 << 3; // violation, ''3' is a magic number'
        int bit1 = 1 >> 3; // violation, ''3' is a magic number'
        int bit2 = 1 >>> 3; // violation, ''3' is a magic number'
        int bit3 = 1 | 3; // violation, ''3' is a magic number'
        int bit4 = 1 & 3; // violation, ''3' is a magic number'
        int bit5 = 1 ^ 3; // violation, ''3' is a magic number'
        int bit6 = 1 | 3; // violation, ''3' is a magic number'
        int bit7 = ~3; // violation, ''3' is a magic number'
    }
}
