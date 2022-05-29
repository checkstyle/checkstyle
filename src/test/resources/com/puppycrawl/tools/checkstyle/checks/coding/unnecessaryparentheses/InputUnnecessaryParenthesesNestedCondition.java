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

public class InputUnnecessaryParenthesesNestedCondition {
    public void foo() {
        int a, b, c, d;
        int i = 0;
        int x = 0;
        int y = 0;

        a = 0;
        b = 0;
        c = 0;
        d = c - 1;
        c = (a + b); // violation 'Unnecessary parentheses around assignment right-hand side'
        d = c - 1;

        b = ((((a + b) * (c + d)))); // 2 violations

        i = ((a + b) * (c + d)); // violation 'parentheses around assignment right.*side'
        i = (a + b) * (c + d);

        int n = 10;
        if ((n = n % 2) == 0) {
            System.out.println("correct 1");
        }

        if (n % 2 == 0) {
            System.out.println("correct 2");
        }

        n = (1 + 2) * 3;

        if (((n % 2)) == 0) { // violation 'Unnecessary parentheses around expression'
            System.out.println("violation 1");
        }

        if (((n = n % 2)) == 0) { // violation 'Unnecessary parentheses around expression'
            System.out.println("violation 3");
        }

        if ((x > y)) { // violation 'Unnecessary parentheses around expression'
            x += y;
        }

        for (i = (0+1); (i) < ((6+6)); i += (1+0)) { // 4 violations
            System.identityHashCode("hi");
        }

        n = ((((a + b) * (c + d)))); // 2 violations
    }
}