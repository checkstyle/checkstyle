/*
MagicNumber
ignoreNumbers =
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

/* Config:
 *
 * ignoreNumbers ={}
 * ignoreAnnotation =  true
 */

/**
 * Describe class InputMagicNumber
 * 
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumberIgnoreNone1 {
    public void magicMethod() {

        final int INT_CONST = 101_000;
        final long LONG_CONST1 = 100_000L;
        final long LONG_CONST2 = 100l;
        final float FLOAT_CONST1 = 1.500_0F;
        final float FLOAT_CONST2 = 1.5f;
        final double DOUBLE_CONST1 = 1.500_0D;
        final double DOUBLE_CONST2 = 1.5d;
        final double DOUBLE_CONST3 = 1.5;

        int int_var1 = 1 + 2; // violations ''1' and '2' are magic numbers'
        int int_var2 = (2); // violation ''2' is a magic number'
        long long_var1 = 0L; // violation ''0L' is a magic number'
        long long_var2 = 0l; // violation ''0l' is a magic number'
        double double_var1 = 1.0 + 2.0; // violations ''1.0' and '2.0' are magic numbers'
        double double_var2 = 0d; // violation ''0d' is a magic number'

        int[] int_array = new int[2]; // violation ''2' is a magic number'

        int_var1 += 1; // violation ''1' is a magic number'
        double_var1 = 1.0 + 2.0; // 2 violation '1.0' and '2.0' are magic numbers

        for (int i = 0; i < 2; i++)
            ; // 2 violation '1' and '2' are magic numbers

        if (1 < 2)
            ; // 2 violation '1' and '2' are magic numbers

        if (1.0 < 2.0)
            ; // 2 violation '1.0' and '2.0' are magic numbers

        int int_magic1 = 3_000; // violation ''3_000' is a magic number'
        double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
        int int_magic2 = (3 + 4); // violations ''3' and '4' are magic numbers'

        int_array = new int[3]; // violation ''3' is a magic number'

        int_magic1 += 3; // violation ''3' is a magic number'
        double_magic1 *= 1.5; // violation ''1.5' is a magic number'

        for (int j = 3; j < 5; j += 3) { // violations ''3', '5', and '3' are magic numbers'
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation ''3' is a magic number'
            int_magic1 = int_magic1 + 3; // violation ''3' is a magic number'
        }

        int octalVar0 = 00; // violation '00' is a magic number
        int octalVar8 = 010; // violation '010' is a magic number
        int octalVar9 = 011; // violation '011' is a magic number

        long longOctalVar8 = 0_10L; // violation '0_10L' is a magic number
        long longOctalVar9 = 011l; // violation '011l' is a magic number

        int hexVar0 = 0x0; // violation '0x0' is a magic number
        int hexVar16 = 0x10; // violation '0x10' is a magic number
        int hexVar17 = 0X011; // violation '0X011' is a magic number
        long longHexVar0 = 0x0L; // violation '0x0L' is a magic number
        long longHexVar16 = 0x10L; // violation '0x10L' is a magic number
        long longHexVar17 = 0X11l; // violation '0X11l' is a magic number
    }
}

interface Blah2IgnoreNone1 {
    int LOW = 5;
    int HIGH = 78;
}

class ArrayMagicTestIgnoreNone1 {
    private static final int[] NONMAGIC = { 3 };
    private int[] magic = { 3 }; // violation '3' is a magic number
    private static final int[][] NONMAGIC2 = { { 1 }, { 2 }, { 3 } };
}
