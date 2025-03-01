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

        int int_var1 = 1; // violation ''1' is a magic number'
        int int_var2 = (2); // violation ''2' is a magic number'
        long long_var1 = 0L; // violation ''0L' is a magic number'
        long long_var2 = 0l; // violation ''0l' is a magic number'
        double double_var1 = 0D; // violation ''0D' is a magic number'
        double double_var2 = 0d; // violation ''0d' is a magic number'

        int[] int_array = new int[2]; // violation ''2' is a magic number'

        int_var1 = 1 + 2;
        int_var1 += 1; // violation ''1' is a magic number'
        double_var1 = 1.0 + 2.0;

        for (int i = 0; i < 2; i++);

        if (1 < 2);

        if (1.0 < 2.0);

        int int_magic1 = 3_000; // violation ''3_000' is a magic number'
        double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
        int int_magic2 = (3 + 4);

        int_array = new int[3]; // violation ''3' is a magic number'

        int_magic1 += 3; // violation ''3' is a magic number'
        double_magic1 *= 1.5; // violation ''1.5' is a magic number'

        for (int j = 3; j < 5; j += 3) {
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation ''3' is a magic number'
            int_magic1 = int_magic1 + 3; // violation ''3' is a magic number'
        }

        int octalVar0 = 00;
        int octalVar8 = 010;
        int octalVar9 = 011;

        long longOctalVar8 = 0_10L;
        long longOctalVar9 = 011l;

        int hexVar0 = 0x0;
        int hexVar16 = 0x10;
        int hexVar17 = 0X011;
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L;
        long longHexVar17 = 0X11l;
    }
}

interface Blah2IgnoreNone1 {
    int LOW = 5;
    int HIGH = 78;
}

class ArrayMagicTestIgnoreNone1 {
    private static final int[] NONMAGIC = { 3 };
    private int[] magic = { 3 };
    private static final int[][] NONMAGIC2 = { { 1 }, { 2 }, { 3 } };
}
