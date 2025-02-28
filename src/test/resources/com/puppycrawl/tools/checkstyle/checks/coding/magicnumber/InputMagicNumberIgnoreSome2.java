/*
MagicNumber
ignoreNumbers = 0, 1, 3.0, 8, 16, 3000
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreSome2 {
    /**
     * test long hex
     */
    long l = 0xffffffffL; // violation '0xffffffffL' is a magic number

    /**
     * test signed values
     */
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3;
    private int mMinusTwo = -2; // violation '-2' is a magic number
    private double mPlusDecimal = +3.5; // violation '3.5' is a magic number
    private double mMinusDecimal = -2.5; // violation '2.5' is a magic number

    /**
     * test octal and hex negative values
     */
    private int hexIntMinusOne = 0xffffffff; // violation '0xffffffff' is a magic number
    private long hexLongMinusOne = 0xffffffffffffffffL; // violation '0xffffffffffffffffL' is a magic number
    private long hexIntMinValue = 0x80000000; // violation '0x80000000' is a magic number
    private long hexLongMinValue = 0x8000000000000000L; // violation '0x8000000000000000L' is a magic number
    private int octalIntMinusOne = 037777777777; // violation '037777777777' is a magic number
    private long octalLongMinusOne = 01777777777777777777777L; // violation '01777777777777777777777L' is a magic number
    private long octalIntMinValue = 020000000000; // violation '020000000000' is a magic number
    private long octalLongMinValue = 01000000000000000000000L; // violation '01000000000000000000000L' is a magic number
    public static final int TESTINTVAL = (byte) 0x80;
    public static final java.util.List MYLIST = new java.util.ArrayList() {
        public int size() {

            return 378; // violation '378' is a magic number
        }
    };

    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder = javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);

    enum MyEnum2IgnoreSome2 {
        A_2(3),
        B_2(54);

        private MyEnum2IgnoreSome2(int value) {

        }
    }
}
