/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;
public class InputUnnecessaryParenthesesCasts1 {
    public void valid1() {
        int x = 23;
        int y = 44;
        float k = 12f;

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        int d = ((int) 100f) + 100 * 2 / ((int) 12.5) + (int) 90f;
        int p = (int) 110f + 10 * 2 / (int) 10f + (int) 32.2;

        y = (int) (22.2 * 2) / ((int) 8f + 5);

        double arg2 = 23.2;
        int i = (int) arg2;
        i = ((int) arg2); // violation 'Unnecessary parentheses around assignment right-hand side'
        p = (int) arg2;

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        x = (2 * 2 /((int) k));
        x = 2 * 2 / (int) k;

        int par = ((int)2f * 2) / 4;
        y = ((Integer) x).hashCode();

        int py = 12;
        float xy = 40f;
        int yp = 0;
        boolean finished = true;
        boolean result = false;

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        if(py >= ((int)xy) || (yp ==1 | py >=1)) {
            xy--;
        }
        else if(yp >= (int)xy || (py ==1 | py >=1)) {
            xy++;
        }

        if (!((int) xy > yp) && py < 20) {
            py++;
        }

        if (35 + (int)'a' == 100) {
            py++;
        }

        boolean checkone = true;
        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        if (!((boolean) checkone)) {
            checkone = false;
        }
        else if ((boolean) checkone) {
            checkone = !checkone;
        }

        double limit = 3.2;
        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        for (int j = 0; j >= ((int) limit); j++) {
            yp +=1;
        }
        for (int j =0; j >= (int) limit; j++) {
            py--;
            break;
        }

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        for(int j = 10; !finished && !((boolean) (j > 5)) ; j++){
            break;
        }
        for(int jp = 9; !finished || !(boolean) (jp >5); jp++){
            checkone = false;
            break;
        }

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        long p1 = ((long) ((20 >> 24 ) & 0xFF)) << 24;
        long p2 = (long) ((20 >> 24 ) & 0xFF) << 24;

        //until https://github.com/checkstyle/checkstyle/issues/14872
        // could be operator precedence issue
        float f4 = -((float) 42);
        float f5 = -(float) 90;

        //until https://github.com/checkstyle/checkstyle/issues/14872
        long shiftedbytwo = ((long)x) << 2;
        long shiftedbythree = (long)y << 3;

        //until https://github.com/checkstyle/checkstyle/issues/14872
        // could be operator precedence issue
        short complement = (short) ~((short) 87777);
        short bcomplement = (short) ~(short) 90122;

        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        int numSlices1 = (int) Math.max(Math.ceil(((double) 20) / 10), 1);
        int numSlices2 = (int) Math.max(Math.ceil((double) 20 / 10), 1);
    }
    private long getLong1(int start, int end) {
        //no violation below until https://github.com/checkstyle/checkstyle/issues/14872
        return (((long) start) << 32) | 0xFFFFFFFFL & end;
    }
    private long getLong2(int start, int end) {
        return ((long) start << 32) | 0xFFFFFFFFL & end;
    }
}
