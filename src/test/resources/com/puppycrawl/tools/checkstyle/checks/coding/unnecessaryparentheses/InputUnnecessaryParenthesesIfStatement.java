/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesIfStatement {

    void method(String sectionName) {
        if ("Content".equals(sectionName) || "Overview".equals(sectionName)
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation
                                                                    // parenthesis
                && !"AbstractClassName".equals(sectionName) // ok
        )) {
            System.out.println("sd");
        }
    }

    private void method() {
        int x, y, z;
        x = 0;
        y = 0;

        z = (x < y) ? x : y; // ok

        if ((x < y)           // violation
                && (x > z)) { // violation
            return;
        }

        if (((x < y)           // 2 violations
                && (x > z))) { // violation
            return;
        }

        if (!(x <= y)          // ok
                || (x >= z)) { // violation
            return;
        }

        if ((x == y)           // violation
                || (x != z)) { // violation
            return;
        }

        if ((                       // violation
                (x == y)            // violation
                        || (x != z) // violation
        )) {
            return;
        }

        if ((Integer.valueOf(x) instanceof Integer) // violation
                || Integer.valueOf(y) instanceof Integer) { // ok
            return;
        }
        if (x == ((y<z) ? y : z) &&
            ((x>y && y>z)                  // violation
                    || (!(x<z) && y>z))) { // violation
            return;
        }
        if ((x >= 0 && y <= 9)            // violation
                 || (z >= 5 && y <= 5)    // violation
                 || (z >= 3 && x <= 7)) { // violation
            return;
        }
        if(x>= 0 && (x<=8 || y<=11) && y>=8) { // ok
            return;
        }
        if((y>=11 && x<=5)            // violation
                || (x<=12 && y>=8)) { // violation
            return;
        }
    }
    private void check() {
        String sectionName = "Some String";
        if ("Some content".equals(sectionName) || "Some overview".equals(sectionName) // ok
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation
                                                                    // parenthesis
                && !"AbstractClassName".equals(sectionName) // ok
        )) {
            return;
        }

        if (sectionName instanceof String && "Other Overview".equals(sectionName) // ok
                && (!"AbbreviationAsWordInName".equals(sectionName) // ok
                || !"AbstractClassName".equals(sectionName) // ok
        )) {
            return;
        }
    }
    private void UnaryAndPostfix() {
        boolean x = true;
        boolean y = true;
        int a = 25;
        if ((++a) >= 54 && x) { // violation
            return;
        }
        if ((~a) > -27            // violation
                 && (a-- < 30)) { // violation
            return;
        }
        if ((-a) != -27 // violation
                 && x) {
            return;
        }
    }
}
