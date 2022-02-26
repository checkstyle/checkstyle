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

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesCheckSwitchExpression {
    MathOperation2 tooManyParens(int k) {
        return switch (k) {
            case 1 -> {
                MathOperation2 case5 = (a, b) -> (a + b); // violation '.* right-hand side'
                yield case5;
            }
            case (2) -> { // violation 'Unnecessary parentheses around literal '2''
                MathOperation2 case6 = (int a, int b) -> (a + b); // violation '.* right-hand side'
                yield case6;
            }
            case 3 -> {
                MathOperation2 case7 = (int a, int b) -> {
                    return (a + b); // violation '.* around assignment right-hand side'
                };
                yield (case7); // violation 'Unnecessary parentheses around identifier 'case7''
            }
            default -> {
                MathOperation2 case8 = (int x, int y) -> {
                    return (x + y); // violation '.* around assignment right-hand side'
                };
                yield case8;
            }
        };
    }

    MathOperation2 tooManyParens2(int k) {
       switch (k) {
            case 1 -> {
                MathOperation2 case5 = (a, b) -> (a + b); // violation '.* right-hand side'
            }
            case (2) -> { // violation 'Unnecessary parentheses around literal '2''
                MathOperation2 case6 = (int a, int b) -> (a + b); // violation '.* right-hand side'
            }
            case 3 -> {
                MathOperation2 case7 = (int a, int b) -> {
                    return (a + b + 2 ); // violation '.* around assignment right-hand side'
                };
            }
            default -> {
                MathOperation2 case8 = (int x, int y) -> {
                    return (x + y); // violation '.* around assignment right-hand side'
                };
            }
        };
       return (a, b) -> 0;
    }

    interface MathOperation2 {
        int operation(int a, int b);
    }
}
