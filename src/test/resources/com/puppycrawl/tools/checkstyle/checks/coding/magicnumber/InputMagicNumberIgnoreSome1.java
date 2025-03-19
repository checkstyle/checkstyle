/*
MagicNumber
ignoreNumbers = 0, 1, 3.0, 8, 16, 3000
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

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumberIgnoreSome1 {
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
        int int_var2 = (2); // violation 'Magic number: 2'
        long long_var1 = 0L;
        long long_var2 = 0l;
        double double_var1 = 0D;
        double double_var2 = 0d;

        int[] int_array = new int[2]; // violation 'Magic number: 2'

        int_var1 = 1 + 2; // violation 'Magic number: 1', 'Magic number: 2'
        int_var1 += 1;
        double_var1 = 1.0 + 2.0; // violation 'Magic number: 1.0', 'Magic number: 2.0'

        for (int i = 0; i < 2; i++); // violation 'Magic number: 0', 'Magic number: 2'

        if (1 < 2); // violation 'Magic number: 1', 'Magic number: 2'

        if (1.0 < 2.0); // violation 'Magic number: 1.0', 'Magic number: 2.0'

        //magic numbers
        int int_magic1 = 3_000;
        double double_magic1 = 1.5_0; // violation 'Magic number: 1.5_0'
        int int_magic2 = (3 + 4); // violation 'Magic number: 3', 'Magic number: 4'

        int_array = new int[3];

        int_magic1 += 3;
        double_magic1 *= 1.5; // violation 'Magic number: 1.5'

        for (int j = 3; j < 5; j += 3) { // violation 'Magic number: 3', 'Magic number: 5', 'Magic number: 3'
            int_magic1++;
        }

        if (int_magic1 < 3) {
            int_magic1 = int_magic1 + 3;
        }


        int octalVar0 = 00;
        int octalVar8 = 010;
        int octalVar9 = 011; // violation 'Magic number: 011'

        long longOctalVar8 = 0_10L;
        long longOctalVar9 = 011l; // violation 'Magic number: 011l'


        int hexVar0 = 0x0;
        int hexVar16 = 0x10;
        int hexVar17 = 0X011;  // violation 'Magic number: 0X011'
        long longHexVar0 = 0x0L;
        long longHexVar16 = 0x10L;
        long longHexVar17 = 0X11l; // violation 'Magic number: 0X11l'
    }
}

interface Blah2IgnoreSome1
{
  int LOW = 5;
  int HIGH = 78;
}

class ArrayMagicTestIgnoreSome1
{
    private static final int[] NONMAGIC = {3};
    private int[] magic = {3};
    private static final int[][] NONMAGIC2 = {{1}, {2}, {3}};
}
