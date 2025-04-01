/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = ASSIGN, ARRAY_INIT, EXPR, UNARY_PLUS, UNARY_MINUS, TYPECAST, \
                            ELIST, STAR, DIV, PLUS, MINUS
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;
public class InputMagicNumberWaiverParentToken2 {
    /**
     * test long hex
     */
    long l = 0xffffffffL; // violation 'magic number'


    /**
     * test signed values
     */
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3; // violation 'magic number'
    private int mMinusTwo = -2;  // violation 'magic number'
    private double mPlusDecimal = +3.5; // violation 'magic number'
    private double mMinusDecimal = -2.5; // violation 'magic number'

    /**
     * test octal and hex negative values
     */
    private int hexIntMinusOne = 0xffffffff;
    private long hexLongMinusOne = 0xffffffffffffffffL;
    private long hexIntMinValue = 0x80000000; // violation 'magic number'
    private long hexLongMinValue = 0x8000000000000000L; // violation 'magic number'
    private int octalIntMinusOne = 037777777777;
    private long octalLongMinusOne = 01777777777777777777777L;
    private long octalIntMinValue = 020000000000; // violation 'magic number'
    private long octalLongMinValue = 01000000000000000000000L;  // violation 'magic number'
    public static final int TESTINTVAL = (byte) 0x80;

    public static final java.util.List MYLIST = new java.util.ArrayList() {
        public int size() {

            return 378; // violation 'magic number'
        }
    };

    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27); // violation 'magic number'
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
            javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3); // 4 violation 'magic number's

    enum MyEnum2WaiverParentToken2 {
        A(3),
        B(54);

        private MyEnum2WaiverParentToken2(int value) {

        }
    }
}
