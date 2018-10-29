package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumber {
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
        int int_var2 = (2);
        long long_var1 = 0L;
        long long_var2 = 0l;
        double double_var1 = 0D;
        double double_var2 = 0d;

        int[] int_array = new int[2];

        int_var1 = 1 + 2;
        int_var1 += 1;
        double_var1 = 1.0 + 2.0;

        for (int i = 0; i < 2; i++);

        if (1 < 2);

        if (1.0 < 2.0);

        //magic numbers
        int int_magic1 = 3_000;
        double double_magic1 = 1.5_0;
        int int_magic2 = (3 + 4);

        int_array = new int[3];

        int_magic1 += 3;
        double_magic1 *= 1.5;

        for (int j = 3; j < 5; j += 3) {
            int_magic1++;
        }

        if (int_magic1 < 3) {
            int_magic1 = int_magic1 + 3;
        }

        //octal
        int octalVar0 = 00;
        int octalVar8 = 010;
        int octalVar9 = 011;

        long longOctalVar8 = 0_10L;
        long longOctalVar9 = 011l;

        //hex
        int hexVar0 = 0x0;
        int hexVar16 = 0x10;
        int hexVar17 = 0X011;
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L;
        long longHexVar17 = 0X11l;
    }
}

interface Blah2
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTest
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3};
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}

/** test long hex */
class LongHex
{
    long l = 0xffffffffL;
}

/** test signed values */
class Signed
{
    public static final int CONST_PLUS_THREE = +3;
    public static final int CONST_MINUS_TWO = -2;
    private int mPlusThree = +3;
    private int mMinusTwo = -2;
    private double mPlusDecimal = +3.5;
    private double mMinusDecimal = -2.5;
}

/** test octal and hex negative values */
class NegativeOctalHex
{
    private int hexIntMinusOne = 0xffffffff;
    private long hexLongMinusOne = 0xffffffffffffffffL;
    private long hexIntMinValue = 0x80000000;
    private long hexLongMinValue = 0x8000000000000000L;
    private int octalIntMinusOne = 037777777777;
    private long octalLongMinusOne = 01777777777777777777777L;
    private long octalIntMinValue = 020000000000;
    private long octalLongMinValue = 01000000000000000000000L;
}

class Cast
{
    public static final int TESTINTVAL = (byte) 0x80;
}

class ComplexAndFlagged
{
    public static final java.util.List MYLIST = new java.util.ArrayList()
    {
        public int size()
        {
            // should be flagged although technically inside const definition
            return 378;
        }
    };
}

class ComplexButNotFlagged
{
    // according to user feedback this is typical code that should not be flagged
    public final double SpecialSum = 2 + 1e10, SpecialDifference = 4 - java.lang.Math.PI;
    public final Integer DefaultInit = new Integer(27);
    public final int SpecsPerDay = 24 * 60 * 60, SpecialRatio = 4 / 3;
    public final javax.swing.border.Border StdBorder =
        javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3);
}

enum MyEnum2
{
    A(3),
    B(54);

    private MyEnum2(int value)
    {

    }
}

class TestHashCodeMethod {
    // valid hash code method
    public int hashCode() {
        return 31;
    }

    // invalid hash code method: has parameters
    public int hashCode(int val) {
        return 42;
    }

    // invalid hash code method: misspelled
    public int hashcode() {
        return 13;
    }

    static {
        int x=21;
    }

    {
        int y=37;
    }

    public TestHashCodeMethod() {
        int z=101;
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

class TestMethodCall {

	public TestMethodCall(int x){

    }

	public void method2() {
        final TestMethodCall dummyObject = new TestMethodCall(62);
	}
}

class Binary {
    int intValue = 0b101;
    long longValue = 0b1010000101000101101000010100010110100001010001011010000101000101L;
}
