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

public class InputUnnecessaryParenthesesIfStatement {

    void method(String sectionName) {
        if ("Content".equals(sectionName) || "Overview".equals(sectionName)
                || (!"A".equals(sectionName) // violation 'Unnecessary paren.* around expression'
                && !"AbstractClassName".equals(sectionName)
        )) {
            System.out.println("sd");
        }
    }

    private void method() {
        int x, y, z;
        x = 0;
        y = 0;

        z = (x < y) ? x : y;

        if ((x < y)           // violation 'Unnecessary parentheses around expression'
                && (x > z)) { // violation 'Unnecessary parentheses around expression'
            return;
        }

        if (((x < y)           // 2 violations
                && (x > z))) { // violation 'Unnecessary parentheses around expression'
            return;
        }

        if (!(x <= y)
                || (x >= z)) { // violation 'Unnecessary parentheses around expression'
            return;
        }

        if ((x == y)           // violation 'Unnecessary parentheses around expression'
                || (x != z)) { // violation 'Unnecessary parentheses around expression'
            return;
        }

        if ((                       // violation 'Unnecessary parentheses around expression'
                (x == y)            // violation 'Unnecessary parentheses around expression'
                        || (x != z) // violation 'Unnecessary parentheses around expression'
        )) {
            return;
        }

        if ((Integer.valueOf(x) instanceof Integer) // violation 'parentheses around expression'
                || Integer.valueOf(y) instanceof Integer) {
            return;
        }
        if (x == ((y<z) ? y : z) &&
            ((x>y && y>z)                  // violation 'Unnecessary parentheses around expression'
                    || (!(x<z) && y>z))) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if ((x >= 0 && y <= 9)            // violation 'Unnecessary parentheses around expression'
                 || (z >= 5 && y <= 5)    // violation 'Unnecessary parentheses around expression'
                 || (z >= 3 && x <= 7)) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if(x>= 0 && (x<=8 || y<=11) && y>=8) {
            return;
        }
        if((y>=11 && x<=5)            // violation 'Unnecessary parentheses around expression'
                || (x<=12 && y>=8)) { // violation 'Unnecessary parentheses around expression'
            return;
        }
    }
    private void check() {
        String sectionName = "Some String";
        if ("Some content".equals(sectionName) || "Some overview".equals(sectionName)
                || (!"A".equals(sectionName) // violation 'Unnecessary paren.* around expression'
                && !"AbstractClassName".equals(sectionName)
        )) {
            return;
        }

        if (sectionName instanceof String && "Other Overview".equals(sectionName)
                && (!"AbbreviationAsWordInName".equals(sectionName)
                || !"AbstractClassName".equals(sectionName)
        )) {
            return;
        }
    }
    private void UnaryAndPostfix() {
        boolean x = true;
        boolean y = true;
        int a = 25;
        if ((++a) >= 54 && x) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if ((~a) > -27            // violation 'Unnecessary parentheses around expression'
                 && (a-- < 30)) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if ((-a) != -27 // violation 'Unnecessary parentheses around expression'
                 && x) {
            return;
        }
    }
}
