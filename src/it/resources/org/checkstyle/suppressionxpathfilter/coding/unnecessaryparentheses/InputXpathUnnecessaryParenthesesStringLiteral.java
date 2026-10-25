/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, \
         STAR_ASSIGN, LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LOR, \
         LITERAL_INSTANCEOF, GT, LT, GE, LE, EQUAL, NOT_EQUAL, UNARY_MINUS, \
         UNARY_PLUS, INC, DEC, LNOT, BNOT, POST_INC, POST_DEC, INDEX_OP, DOT, \
         TYPECAST


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesStringLiteral {
    void foo () {
        // violation below 'Unnecessary parentheses around string '"Checkstyle"'.'
        String str = ("Checkstyle") + "is cool";
    }
}
