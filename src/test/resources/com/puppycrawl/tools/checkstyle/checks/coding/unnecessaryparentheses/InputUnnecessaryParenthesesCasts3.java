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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputUnnecessaryParenthesesCasts3 {
    private static final double MDP_MULT = 2.2;

    public void fooUnnecessaryParentheses(String hexa) {
        Object b = new Object();
        int unm = 30;
        boolean Eval = false;
        List<Object> siZ = new ArrayList<>();
        Map<Object, Object> record = new HashMap<>();
        if (hexa.contains("Z")) {
            throw new IllegalArgumentException(((char) b) + " is not a hexadecimal digit");
            // violation above 'Unnecessary parentheses around expression.'
        }
        if (hexa.contains("Y")) {
            throw new IllegalArgumentException((char) b + " is not a hexadecimal digit");
        }

        Attrs le = new Attrs();
        String fileName = "log.txt";
        setFileLastModified(fileName,
                ((long) le.getMTime()) * 1000);
        // violation above 'Unnecessary parentheses around expression.'
        setFileLastModified(fileName,
                (long) le.getMTime() * 1000);

        if (!Eval && unm > 0 && ((int) (12f * unm - 1)) > unm+20) {}
        // violation above 'Unnecessary parentheses around expression.'
        if (!Eval && unm > 0 && (int) (12f * unm - 1) > unm+20) {}

        final long l8 = ((long) record.get(unm)) & 0xffL;
        // violation above 'Unnecessary parentheses around expression.'
        final long l9 = (long) record.get(unm) & 0xffL;

        int maxSize = 21;
        double used = maxSize == 0 ? 0 : 100 * (((double) unm) / maxSize);
        // violation above 'Unnecessary parentheses around expression.'
        double used1 = maxSize == 0 ? 0 : 100 * ((double) unm / maxSize);

        int dX = 90;
        int dY = 2;
        int stepx = (int) (((double)dX) * MDP_MULT) / dY;
        // violation above 'Unnecessary parentheses around expression.'
        int stepy = (int) ((double)dX * MDP_MULT) / dY;

        Object layerOffset;
        unm += ((int) (Math.sqrt(unm) * 1.5));
        // violation above 'Unnecessary parentheses around assignment right-hand side'
        unm += (int) (Math.sqrt(unm) * 1.5);
    }

    public static void setFileLastModified(String fileName, long lastModified) {}
    public static class Attrs {
        int getMTime() {
            return 123;
        }
    }
}
