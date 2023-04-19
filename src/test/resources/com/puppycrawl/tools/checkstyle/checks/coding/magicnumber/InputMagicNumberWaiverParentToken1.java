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

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumberWaiverParentToken1 {
    public void magicMethod() {

        final int INT_CONST = 101_000;
        final long LONG_CONST1 = 100_000L;
        final long LONG_CONST2 = 100l;
        final float FLOAT_CONST1 = 1.500_0F;
        final float FLOAT_CONST2 = 1.5f;
        final double DOUBLE_CONST1 = 1.500_0D;
        final double DOUBLE_CONST2 = 1.5d;
        final double DOUBLE_CONST3 = 1.5;


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


        int int_magic1 = 3_000; // violation
        double double_magic1 = 1.5_0; // violation
        int int_magic2 = (3 + 4);  // 2 violations

        int_array = new int[3];  // violation

        int_magic1 += 3;   // violation
        double_magic1 *= 1.5; // violation

        for (int j = 3; j < 5; j += 3) {  // 3 violations
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation
            int_magic1 = int_magic1 + 3; // violation
        }


        int octalVar0 = 00;
        int octalVar8 = 010;  // violation
        int octalVar9 = 011;  // violation

        long longOctalVar8 = 0_10L; // violation
        long longOctalVar9 = 011l;  // violation


        int hexVar0 = 0x0;
        int hexVar16 = 0x10;  // violation
        int hexVar17 = 0X011; // violation
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L; // violation
        long longHexVar17 = 0X11l; // violation
    }
}

interface Blah2WaiverParentToken1
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTestWaiverParentToken1
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3};  // violation
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}
