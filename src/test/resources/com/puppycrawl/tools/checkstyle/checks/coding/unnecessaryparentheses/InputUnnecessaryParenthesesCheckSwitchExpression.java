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

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesCheckSwitchExpression {
  MathOp2 tooManyParens(int k) {
    return switch (k) {
      case 1 -> {
        MathOp2 e = (a, b) -> (a + b); // violation 'parentheses around assignment right-hand side'
        yield e;
      }
      case (2) -> { // violation 'Unnecessary parentheses around literal '2''
        MathOp2 f = (int a, int b) -> (a + b); // violation 'paren.* around assignment right.*side'
        yield f;
      }
      case 3 -> {
        MathOp2 g = (int a, int b) -> {
          return (a + b); // violation 'Unnecessary parentheses around return value'
        };
        yield (g); // violation 'Unnecessary parentheses around identifier 'g''
      }
      default -> {
        MathOp2 h = (int x, int y) -> {
          return (x + y); // violation 'Unnecessary parentheses around return value'
        };
        yield h;
      }
    };
  }

  MathOp2 tooManyParens2(int k) {
    switch (k) {
      case 1 -> {
        MathOp2 e = (a, b) -> (a + b); // violation 'parentheses around assignment right-hand side'
      }
      case (2) -> { // violation 'Unnecessary parentheses around literal '2''
        MathOp2 f = (int a, int b) -> (a + b); // violation 'paren.* around assignment right.*side'
      }
      case 3 -> {
        MathOp2 g = (int a, int b) -> {
          return (a + b + 2); // violation 'Unnecessary parentheses around return value'
        };
      }
      default -> {
        MathOp2 h = (int x, int y) -> {
          return (x + y); // violation 'Unnecessary parentheses around return value'
        };
      }
    }
    ;
    return (a, b) -> 0;
  }

  interface MathOp2 {

    int operation(int a, int b);
  }
}
