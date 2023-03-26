/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, BOR, BAND, BXOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesOperator {
        boolean a = true;
        boolean b = false;
    void method() {
        if(a || (b ^ a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a || (b | a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a || (b & a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b ^ a) && b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b | a) && b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b & a) && b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b ^ a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b | a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a && (b & a) || b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a || (b ^ a) && b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a || (b | a) && b) { // violation 'Unnecessary parentheses around expression'
        }
        if(a || (b & a) && b) { // violation 'Unnecessary parentheses around expression'
        }
    }
}
