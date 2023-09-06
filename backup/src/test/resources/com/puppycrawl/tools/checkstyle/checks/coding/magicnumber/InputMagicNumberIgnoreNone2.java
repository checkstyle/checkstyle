/*
MagicNumber
ignoreNumbers =
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreNone2 {
    /** test long hex */
    long l = 0xffffffffL; // violation

    /**
     * test signed values
     */
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3; // violation
    private int mMinusTwo = -2; // violation
    private double mPlusDecimal = +3.5; // violation
    private double mMinusDecimal = -2.5; // violation

    /**
     * test octal and hex negative values
     */
    private int hexIntMinusOne = 0xffffffff; // violation
    private long hexLongMinusOne = 0xffffffffffffffffL; // violation
    private long hexIntMinValue = 0x80000000; // violation
    private long hexLongMinValue = 0x8000000000000000L; // violation
    private int octalIntMinusOne = 037777777777;  // violation
    private long octalLongMinusOne = 01777777777777777777777L;  // violation
    private long octalIntMinValue = 020000000000;  // violation
    private long octalLongMinValue = 01000000000000000000000L; // violation
    public static final int TESTINTVAL = (byte) 0x80;

    public static final java.util.List MYLIST = new java.util.ArrayList() {
        public int size() {

            return 378; // violation
        }
    };

    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
            javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);

    enum MyEnum2IgnoreNone2 {
        A_3(3),
        B_3(54);

        private MyEnum2IgnoreNone2(int value) {

        }
    }
}
