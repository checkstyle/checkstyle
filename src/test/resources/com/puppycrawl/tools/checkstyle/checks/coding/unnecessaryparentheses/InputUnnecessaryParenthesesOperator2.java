/*
UnnecessaryParentheses
tokens = EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, BOR, BAND, BXOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesOperator2 {

    void method() {
        boolean a = true;
        boolean b = false;

        if ((b || a) & // ok
                (b || a)) {
        }
        if ((b && a) & // ok
                (b && a)) {
        }
        if ((b || a) | // ok
                (b && a)) {
        }

        int c = 1;
        int d = 12;
        if(a || (c ^ d) >> 1 == 1 || b) { // ok
        }

        if ((b || a) & // ok
                (b || a)) {
        }
        if ((b || a) | // ok
                (b || a)) {
        }
        if ((b || a) ^ // ok
                (b || a)) {
        }
        if ((b && a) & // ok
                (b && a)) {
        }
        if ((b && a) | // ok
                (b && a)) {
        }
        if ((b && a) ^ // ok
                (b && a)) {
        }
        if ((b || a) & // ok
                (b && a)) {
        }
    }

    public void test2() {
        int a = 9;
        int b = 10;
        if (a > b) {
            System.out.println("a is greater then b");
        }
        else if ((b < a) & ( // violation 'Unnecessary parentheses around expression'
                (8>9) | // violation 'Unnecessary parentheses around expression'
                        (!m3()))) { // violation 'Unnecessary parentheses around expression'
             System.out.println();
        }

    }

    boolean m3() {
        return true;
    }

    void method3() {
        int x = 10, y = 5, z = 5;

        if (x == ((y<z) ? y : z) &
            ((x>y & y>z) // violation 'Unnecessary parentheses around expression'
                | (!(x<z) & y>z))) { // violation 'Unnecessary parentheses around expression'
                return;
        }
        if(x>= 0 & (x<=8 | y<=11) & y>=8) { // ok
            return;
        }
        if(x>= 0 ^ (x<=8 | y<=11) ^ y>=8) { // ok
            return;
        }
        if(x>= 0 || (x<=8 & y<=11) && y>=8) {
            return; // violation above 'Unnecessary parentheses around expression'
        }
        if(x> 0 & (x<=8 & y<=11) & y>=8) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if(x>= 0 ^ (x<=8 & y<=11) ^ y>=8) {
            return; // violation above 'Unnecessary parentheses around expression'
        }
        if(x>= 0 || (x<=8 & y<=11) && y>=8) {
            return; // violation above 'Unnecessary parentheses around expression'
        }
        if(x>= 0 & (x<=8 ^ y<=11) & y>=8) { // ok
            return;
        }
        if(x>= 0 ^ (x<=8 ^ y<=11) ^ y>=8) {
            return; // violation above 'Unnecessary parentheses around expression'
        }
        if(x>= 0 || (x<=8 ^ y<=11) && y>=8) {
            return; // violation above 'Unnecessary parentheses around expression'
        }
        if(x>= 0 != (x<=8 ^ y<=11) && y>=8) {
            return; // ok
        }
        if(true || (4 | 4) >> 1 == 1 || false) { // ok
        }
    }
}
