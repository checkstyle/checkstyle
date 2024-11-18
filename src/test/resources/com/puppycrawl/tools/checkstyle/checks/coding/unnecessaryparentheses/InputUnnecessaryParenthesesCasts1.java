/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, TYPECAST


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;
public class InputUnnecessaryParenthesesCasts1 {
    public void valid1() {
        int x = 23;
        int y = 44;
        float k = 12f;

        int d = ((int) 100f) + 100 * 2 / ((int) 12.5) + (int) 90f;
        // 2 violations above:
        //  'Unnecessary parentheses around expression'
        //  'Unnecessary parentheses around expression'
        int p = (int) 110f + 10 * 2 / (int) 10f + (int) 32.2;

        y = (int) (22.2 * 2) / ((int) 8f + 5);

        double arg2 = 23.2;
        int i = (int) arg2;
        i = ((int) arg2); // violation 'Unnecessary parentheses around assignment right-hand side'
        p = (int) arg2;

        x = (2 * 2 /((int) k));
        // 2 violations above:
        //  'Unnecessary parentheses around assignment right-hand side'
        //  'Unnecessary parentheses around expression'
        x = 2 * 2 / (int) k;

        int par = ((int)2f * 2) / 4;
        y = ((Integer) x).hashCode();

        int py = 12;
        float xy = 40f;
        int yp = 0;
        boolean finished = true;
        boolean result = false;

        if(py >= ((int)xy) // violation 'Unnecessary parentheses around expression.'
                | (yp ==1 | py >=1)) {
            xy--;
        }
        if(yp >= (int)xy
                || (py ==1 | py >=1)) {
            xy++;
        }

        if (!((int) xy > yp)
                && py < 20) {
            py++;
        }

        if (35 + (int) 'a' == 100) {
            py++;
        }

        boolean checkone = true;
        if (!((boolean) checkone)) {
            // violation above 'Unnecessary parentheses around expression.'
            checkone = false;
        }
        else if ((boolean) checkone) {
            checkone = !checkone;
        }

        double limit = 3.2;
        for (int j = 0; j >= ((int) limit); j++) {
            // violation above 'Unnecessary parentheses around expression.'
            yp +=1;
        }
        for (int j =0; j >= (int) limit; j++) {
            py--;
            break;
        }

        for(int j = 10; !finished && !((boolean) (j > 5)) ; j++){
            // violation above 'Unnecessary parentheses around expression.'
            break;
        }
        for(int jp = 9; !finished || !(boolean) (jp >5); jp++){
            checkone = false;
            break;
        }
    }
}
