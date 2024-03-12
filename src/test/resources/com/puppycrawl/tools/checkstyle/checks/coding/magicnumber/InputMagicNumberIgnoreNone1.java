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


        int int_var1 = 1; // violation
        int int_var2 = (2); // violation
        long long_var1 = 0L; // violation
        long long_var2 = 0l; // violation
        double double_var1 = 0D; // violation
        double double_var2 = 0d; // violation

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

interface Blah2IgnoreNone1
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTestIgnoreNone1
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3}; // violation
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}
