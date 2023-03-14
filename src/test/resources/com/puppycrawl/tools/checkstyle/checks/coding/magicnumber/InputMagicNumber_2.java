/*
MagicNumber
ignoreNumbers = 0, 1, 3.0, 8, 16, 3000
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumber_2 {
    public void magicMethod() {
        //constants, ignore
        final int INT_CONST = 101_000;
        final long LONG_CONST1 = 100_000L;
        final long LONG_CONST2 = 100l;
        final float FLOAT_CONST1 = 1.500_0F;
        final float FLOAT_CONST2 = 1.5f;
        final double DOUBLE_CONST1 = 1.500_0D;
        final double DOUBLE_CONST2 = 1.5d;
        final double DOUBLE_CONST3 = 1.5;

        //ignore by default
        int int_var1 = 1;
        int int_var2 = (2); // violation
        long long_var1 = 0L;
        long long_var2 = 0l;
        double double_var1 = 0D;
        double double_var2 = 0d;

        int[] int_array = new int[2]; // violation

        int_var1 = 1 + 2; // violation
        int_var1 += 1;
        double_var1 = 1.0 + 2.0; // violation

        for (int i = 0; i < 2; i++); // violation

        if (1 < 2); // violation

        if (1.0 < 2.0); // violation

        //magic numbers
        int int_magic1 = 3_000;
        double double_magic1 = 1.5_0; // violation
        int int_magic2 = (3 + 4); // violation

        int_array = new int[3];

        int_magic1 += 3;
        double_magic1 *= 1.5; // violation

        for (int j = 3; j < 5; j += 3) { // violation
            int_magic1++;
        }

        if (int_magic1 < 3) {
            int_magic1 = int_magic1 + 3;
        }


        int octalVar0 = 00;
        int octalVar8 = 010;
        int octalVar9 = 011; // violation

        long longOctalVar8 = 0_10L;
        long longOctalVar9 = 011l; // violation


        int hexVar0 = 0x0;
        int hexVar16 = 0x10;
        int hexVar17 = 0X011;  // violation
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L;
        long longHexVar17 = 0X11l; // violation
    }
}

interface Blah2_2
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTest_2
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3};
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}

/** test long hex */
class LongHex_2
{
    long l = 0xffffffffL; // violation
}

/** test signed values */
class Signed_2
{
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3;
    private int mMinusTwo = -2; // violation
    private double mPlusDecimal = +3.5; // violation
    private double mMinusDecimal = -2.5; // violation
}

/** test octal and hex negative values */
class NegativeOctalHex_2
{
    private int hexIntMinusOne = 0xffffffff; // violation
    private long hexLongMinusOne = 0xffffffffffffffffL; // violation
    private long hexIntMinValue = 0x80000000; // violation
    private long hexLongMinValue = 0x8000000000000000L; // violation
    private int octalIntMinusOne = 037777777777;  // violation
    private long octalLongMinusOne = 01777777777777777777777L;  // violation
    private long octalIntMinValue = 020000000000;  // violation
    private long octalLongMinValue = 01000000000000000000000L; // violation
}

class Cast_2
{
    public static final int TESTINTVAL = (byte) 0x80;
}

class ComplexAndFlagged_2
{
    public static final java.util.List MYLIST = new java.util.ArrayList()
    {
        public int size()
        {

            return 378; // violation
        }
    };
}

class InputComplexButNotFlagged_2
{

    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
        javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);
}

enum MyEnum2_2
{
    A_2(3),
    B_2(54);

    private MyEnum2_2(int value)
    {

    }
}

class TestHashCodeMethod_2 {

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

    public TestHashCodeMethod_2() {
        int z=101; // violation
    }

    @InputMagicNumberIntMethodAnnotation(42)
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value=43)
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44)
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value=-45)
    public void anotherNegative2() {
    }
}

class TestMethodCall_2 {

        public TestMethodCall_2(int x){

    }

        public void method2() {
        final TestMethodCall_2 dummyObject = new TestMethodCall_2(62);
        }
}

class Binary_2 {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValue_2 {
    int value() default 101;
    int[] ar() default {102};
}
class A_2 {
    {
        switch (Blah2_2.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
