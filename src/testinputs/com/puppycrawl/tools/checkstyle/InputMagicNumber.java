package com.puppycrawl.tools.checkstyle;

/**
 * Describe class InputMagicNumber
 * @author Rick Giles
 * @version 6-May-2003
 */
public class InputMagicNumber {
    public void magicMethod() {
        //constants, ignore
        final int INT_CONST = 101;
        final long LONG_CONST1 = 100L;
        final long LONG_CONST2 = 100l;
        final float FLOAT_CONST1 = 1.5F;
        final float FLOAT_CONST2 = 1.5f;
        final double DOUBLE_CONST1 = 1.5D;
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
        int int_magic1 = 3;
        double double_magic1 = 1.5;
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
        
        long longOctalVar8 = 010L;
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
