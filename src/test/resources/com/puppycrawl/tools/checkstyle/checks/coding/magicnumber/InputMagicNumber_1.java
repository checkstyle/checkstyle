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

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

/**
 * Describe class InputMagicNumber_1
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumber_1 {
    public void magicMethod() {

        final int INT_CONST = 101_000; // ok
        final long LONG_CONST1 = 100_000L; // ok
        final long LONG_CONST2 = 100l;
        final float FLOAT_CONST1 = 1.500_0F;
        final float FLOAT_CONST2 = 1.5f;
        final double DOUBLE_CONST1 = 1.500_0D;
        final double DOUBLE_CONST2 = 1.5d;
        final double DOUBLE_CONST3 = 1.5;

        int int_var1 = 1; // ok
        int int_var2 = (2); // ok
        long long_var1 = 0L; // ok
        long long_var2 = 0l; // ok
        double double_var1 = 0D; // ok
        double double_var2 = 0d; // ok

        int[] int_array = new int[2];

        int_var1 = 1 + 2;
        int_var1 += 1;
        double_var1 = 1.0 + 2.0;

        for (int i = 0; i < 2; i++);

        if (1 < 2);

        if (1.0 < 2.0);


        int int_magic1 = 3_000; // violation
        double double_magic1 = 1.5_0; // violation
        int int_magic2 = (3 + 4); // 2 violations

        int_array = new int[3]; // violation

        int_magic1 += 3;  // violation
        double_magic1 *= 1.5; // violation

        for (int j = 3; j < 5; j += 3) { // 3 violations
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation
            int_magic1 = int_magic1 + 3; // violation
        }

        int octalVar0 = 00;
        int octalVar8 = 010; // violation
        int octalVar9 = 011; // violation

        long longOctalVar8 = 0_10L; // violation
        long longOctalVar9 = 011l;  // violation

        int hexVar0 = 0x0;
        int hexVar16 = 0x10; // violation
        int hexVar17 = 0X011; // violation
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L; // violation
        long longHexVar17 = 0X11l; // violation
    }
}

interface Blah2_1
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTest_1
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3}; // violation
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}

/** test long hex */
class LongHex_1
{
    long l = 0xffffffffL; // violation
}

/** test signed values */
class Signed_1
{
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3; // violation
    private int mMinusTwo = -2;  // violation
    private double mPlusDecimal = +3.5; // violation
    private double mMinusDecimal = -2.5; // violation
}

/** test octal and hex negative values */
class NegativeOctalHex_1
{
    private int hexIntMinusOne = 0xffffffff;
    private long hexLongMinusOne = 0xffffffffffffffffL;
    private long hexIntMinValue = 0x80000000; // violation
    private long hexLongMinValue = 0x8000000000000000L; // violation
    private int octalIntMinusOne = 037777777777;
    private long octalLongMinusOne = 01777777777777777777777L;
    private long octalIntMinValue = 020000000000; // violation
    private long octalLongMinValue = 01000000000000000000000L; // violation
}

class Cast_1
{
    public static final int TESTINTVAL = (byte) 0x80;
}

class ComplexAndFlagged_1
{
    public static final java.util.List MYLIST = new java.util.ArrayList()
    {
        public int size()
        {
            // should be flagged although technically inside const definition
            return 378; // violation
        }
    };
}

class InputComplexButNotFlagged_1
{
    // according to user feedback this is typical code that should not be flagged
    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
        javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);
}

enum MyEnum2_1
{
    A(3),
    B(54);

    private MyEnum2_1(int value)
    {

    }
}

class TestHashCodeMethod_1 {

    public int hashCode() {
        return 31; // violation
    }


    public int hashCode(int val) {
        return 42; // violation
    }


    public int hashcode() {
        return 13; // violation
    }

    static {
        int x=21; // violation
    }

    {
        int y=37; // violation
    }

    public TestHashCodeMethod_1() {
        int z=101; // violation
    }

    @InputMagicNumberIntMethodAnnotation(42) // violation
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value=43) // violation
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44) // violation
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value=-45) // violation
    public void anotherNegative2() {
    }
}

class TestMethodCall_1 {

        public TestMethodCall_1(int x){

    }

        public void method2() {
        final TestMethodCall_1 dummyObject = new TestMethodCall_1(62);
        }
}

class Binary_1 {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValue_1 {
    int value() default 101;
    int[] ar() default {102};
}
class A_1 {
    {
        switch (Blah2.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
