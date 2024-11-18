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
import java.util.HashSet;
import java.util.Arrays;
public class InputUnnecessaryParenthesesCasts {
    public void valid1() {
        int x = 23;
        int y = 44;
        float k = 12f;

        int d = ((int) 100f) + 100 * 2 / ((int) 12.5f) + (int) 90f; // 2 violations

        y = (int) (22.2 * 2) / ((int) 8f + 5);

        double arg2 = 23.2;
        int i = (int) arg2;

        i = ((int) arg2); // violation 'Unnecessary parentheses around assignment right-hand side'

        x = (2 * 2 /((int) k)); // 2 violations

        int par = ((int)2f * 2) / 4;
    }

    public void fooConditionals() {
        int x = 12;
        float xy = 40f;
        int y = 0;
        double limit = 3.2;
        boolean finished = true;
        boolean result = false;

        if(x >= ((int)xy) // violation 'Unnecessary parentheses around expression.'
                | (y==1 | x>=1)) {
            xy--;
        }

        if (!((int) xy > y)
                && x < 20) {
            x++;
        }

        char letter = 'a';
        if (35 + (int) letter == 100) {
            x++;
        }

        boolean checkone = true;
        if (!((boolean) checkone)) {
            // violation above 'Unnecessary parentheses around expression.'
            checkone = false;
        }

        for (int j = 0; j >= ((int) limit); j++) {
            // violation above 'Unnecessary parentheses around expression.'
            y+=1;
        }

        for(int j = 10; !finished && !((boolean) (j > 5)) ; j++){
            // violation above 'Unnecessary parentheses around expression.'
            break;
        }

        String filevalue = "FILEVALUE";
        if (!finished
                || !((boolean) filevalue.contains("O"))) {
            // violation above 'Unnecessary parentheses around expression.'
            filevalue += "F";
        }

        if (result && finished
                || ((int)23.1 + 21) == 32) {
            y--;
        }

        // violation below 'Unnecessary parentheses around expression.'
        if(!((boolean) filevalue.contains("G"))
                || finished) {
            x++;
        }
        String[] a = { "s", "a", "1", "2", "3" };
        Arrays.stream(a)
            .filter(s -> !((boolean) s.isEmpty()))
                // violation above 'Unnecessary parentheses around expression.'
                .toArray(String[]::new);

        Arrays.stream(a) // violation below 'Unnecessary parentheses around expression.'
            .filter(s -> ((boolean) s.isEmpty()))
                .toArray(String[]::new);

        new HashSet<Integer>()
        .stream()
        .filter(f -> f > ((int) 1.1 + 200));

        y = ((Integer) x).hashCode();
    }
}
