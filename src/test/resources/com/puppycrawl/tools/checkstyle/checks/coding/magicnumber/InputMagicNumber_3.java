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

/* Config:
 *
 * ignoreNumbers ={}
 * ignoreAnnotation =  true
 */

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumber_3 {
    public void magicMethod() {

        final int INT_CONST = 101_000; // ok
        final long LONG_CONST1 = 100_000L; // ok
        final long LONG_CONST2 = 100l; // ok
        final float FLOAT_CONST1 = 1.500_0F; // ok
        final float FLOAT_CONST2 = 1.5f; // ok
        final double DOUBLE_CONST1 = 1.500_0D; // ok
        final double DOUBLE_CONST2 = 1.5d; // ok
        final double DOUBLE_CONST3 = 1.5; // ok


        int int_var1 = 1; // violation
        int int_var2 = (2); // violation
        long long_var1 = 0L; // violation
        long long_var2 = 0l; // violation
        double double_var1 = 0D; // ok // violation
        double double_var2 = 0d; // ok // violation

        int[] int_array = new int[2]; // violation

        int_var1 = 1 + 2; // 2 violations
        int_var1 += 1;  // violation
        double_var1 = 1.0 + 2.0; // 2 violations

        for (int i = 0; i < 2; i++); // 2 violations

        if (1 < 2); // 2 violations

        if (1.0 < 2.0); // 2 violations


        int int_magic1 = 3_000; // violation
        double double_magic1 = 1.5_0; // violation
        int int_magic2 = (3 + 4); // 2 violations

        int_array = new int[3]; // violation

        int_magic1 += 3; // violation
        double_magic1 *= 1.5; // violation

        for (int j = 3; j < 5; j += 3) { // 3 violations
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation
            int_magic1 = int_magic1 + 3; // violation
        }


        int octalVar0 = 00;  // violation
        int octalVar8 = 010; // violation
        int octalVar9 = 011; // violation

        long longOctalVar8 = 0_10L; // violation
        long longOctalVar9 = 011l; // violation


        int hexVar0 = 0x0; // violation
        int hexVar16 = 0x10; // violation
        int hexVar17 = 0X011;  // violation
        long longHexVar0 = 0x0L; // violation
        long longHexVar16 = 0x10L; // violation
        long longHexVar17 = 0X11l; // violation
    }
}

interface Blah2_3
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTest_3
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3}; // violation
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}

/** test long hex */
class LongHex_3
{
    long l = 0xffffffffL; // violation
}

/** test signed values */
class Signed_3
{
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3; // violation
    private int mMinusTwo = -2; // violation
    private double mPlusDecimal = +3.5; // violation
    private double mMinusDecimal = -2.5; // violation
}

/** test octal and hex negative values */
class NegativeOctalHex_3
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

class Cast_3
{
    public static final int TESTINTVAL = (byte) 0x80;
}

class ComplexAndFlagged_3
{
    public static final java.util.List MYLIST = new java.util.ArrayList()
    {
        public int size()
        {

            return 378; // violation
        }
    };
}

class InputComplexButNotFlagged_3
{

    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
        javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);
}

enum MyEnum2_3
{
    A_3(3),
    B_3(54);

    private MyEnum2_3(int value)
    {

    }
}

class TestHashCodeMethod_3 {

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

    public TestHashCodeMethod_3() {
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

class TestMethodCall_3 {

        public TestMethodCall_3(int x){

    }

        public void method2() {
        final TestMethodCall_3 dummyObject = new TestMethodCall_3(62);
        }
}

class Binary_3 {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValue_3 {
    int value() default 101;
    int[] ar() default {102};
}
class A_3 {
    {
        switch (Blah2_3.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
