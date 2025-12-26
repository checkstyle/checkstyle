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

public class InputUnnecessaryParenthesesCheckTextBlocks {
  void method() {
    String string1 = ("this") + ("that") + ("other"); // 3 violations
    String string2 = ("""
        this""") // violation above 'Unnecessary parentheses around string "\\n        this\"'
        + ("""
        that""") // violation above 'Unnecessary parentheses around string "\\n        that\"'
        + ("""
        other"""); // violation above 'Unnecessary parentheses around string "\\n        other\"'
    // violation below 'Unnecessary parentheses around string "\\n        this is a test...\"'
    String string3 = ("""
        this is a test.""") + ("""
        and another line"""); // violation above 'paren.* around string "\\n\s* and another li...\"'
  }
}
