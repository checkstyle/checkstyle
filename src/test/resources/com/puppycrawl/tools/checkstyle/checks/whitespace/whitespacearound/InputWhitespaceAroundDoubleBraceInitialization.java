/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
allowEmptySwitchBlockStatements = (default)false
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

import java.util.Properties;

public class InputWhitespaceAroundDoubleBraceInitialization {
    public InputWhitespaceAroundDoubleBraceInitialization() {
        new Properties() {{
            setProperty("double curly braces", "are not a style error");
        }};
        new Properties() {{
            setProperty("", "");}}; // violation  ''}' is not preceded with whitespace'
        new Properties() {{setProperty("", ""); // violation ''{' is not followed by whitespace'
        }}; // 2 violations below
        new Properties() {{setProperty("double curly braces", "are not a style error");}};
        new Properties() {{
            setProperty("double curly braces", "are not a style error");
        }private int i;}; // 2 violations
    }
}
