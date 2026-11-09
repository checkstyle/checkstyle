/*
WhitespaceAround
allowEmptyCatches = (default)false
allowEmptyConstructors = (default)false
allowEmptyLambdas = (default)false
allowEmptyLoops = (default)false
allowEmptyMethods = (default)false
allowEmptySwitchBlockStatements = (default)false
allowEmptyTypes = (default)false
ignoreEnhancedForColon = (default)true
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND, LITERAL_WHEN
enforceWhitespaceAroundArrayInit = (default)false

*/

package org.checkstyle.suppressionxpathfilter.whitespace.whitespacearound;

public class InputXpathWhitespaceAroundNotFollowed {
    int bad =0; // violation "'=' is not followed by whitespace."
    int good = 0;
}
