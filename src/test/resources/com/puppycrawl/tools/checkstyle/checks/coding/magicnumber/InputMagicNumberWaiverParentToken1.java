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


        int int_magic1 = 3_000; // violation ''3000' is a magic number'
        double double_magic1 = 1.5_0; // violation ''1.5' is a magic number'
        int int_magic2 = (3 + 4);  // 2 violations ''3' is a magic number' and ''4' is a magic number'

        int_array = new int[3];  // violation ''3' is a magic number'

        int_magic1 += 3;   // violation ''3' is a magic number'
        double_magic1 *= 1.5; // violation ''1.5' is a magic number'

        for (int j = 3; j < 5; j += 3) {  // 3 violations  ''3' is a magic number' and ''5' is a magic number' and ''3' is a magic number'
            int_magic1++;
        }

        if (int_magic1 < 3) { // violation ''3' is a magic number'
            int_magic1 = int_magic1 + 3; // violation ''3' is a magic number'
        }


        int octalVar0 = 00;
        int octalVar8 = 010;  // violation ''010' is a magic number'
        int octalVar9 = 011;  // violation ''011' is a magic number'

        long longOctalVar8 = 0_10L; // violation ''010L' is a magic number'
        long longOctalVar9 = 011l;  // violation ''011L' is a magic number'


        int hexVar0 = 0x0;
        int hexVar16 = 0x10;  // violation ''0x10' is a magic number'
        int hexVar17 = 0X011; // violation ''0X11' is a magic number'
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L; // violation ''0x10L' is a magic number'
        long longHexVar17 = 0X11l; // violation ''0X11L' is a magic number'
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
    private int[] magic = {3};  // violation ''3' is a magic number'
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}
