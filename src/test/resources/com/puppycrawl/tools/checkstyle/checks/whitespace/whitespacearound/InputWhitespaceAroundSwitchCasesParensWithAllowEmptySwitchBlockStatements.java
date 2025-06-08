/*
WhitespaceAround
allowEmptyConstructors = true
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = true
ignoreEnhancedForColon = (default)true
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundSwitchCasesParensWithAllowEmptySwitchBlockStatements {

    void m(int k) {
        switch (k) {
            case 1 :
                System.out.println("1");
            case 2: { }
            case 3:
            case 4: {}
            default: {}
        }
    }

    void m2(int k) {
        switch (k) {
            case 1 -> System.out.println("1");
            case 2 -> { }
            case 3 -> {}
            default -> {}
        }
    }

    void m3(int k) {
        switch (k) {
            case 1 -> {System.out.println("1");}
            // 2 violations above:
            //                  ''{' is not followed by whitespace.'
            //                  ''}' is not preceded with whitespace.'
            default -> { }
        }
    }

    void m4(int k) {
        switch (k) {
            case 1 : {System.out.println("1");}
            // 2 violations above:
            //                  ''{' is not followed by whitespace.'
            //                  ''}' is not preceded with whitespace.'
            default : { }
        }
    }

    void m5(int k) {
        {}
        // 2 violations above:
        //                  ''{' is not followed by whitespace.'
        //                  ''}' is not preceded with whitespace.'

        {System.out.println("1");}
        // 2 violations above:
        //                  ''{' is not followed by whitespace.'
        //                  ''}' is not preceded with whitespace.'

    }

    void m6(int k) {
        switch (k) {
            case 1: {} {}
            case 2: { } { }
            case 3: {;}
            // 2 violations above:
            //                  ''{' is not followed by whitespace.'
            //                  ''}' is not preceded with whitespace.'
            case 4: {{}}
            // 6 violations above:
            //                  ''{' is not followed by whitespace.'
            //                  ''{' is not followed by whitespace.'
            //                  ''{' is not preceded with whitespace.'
            //                  ''}' is not followed by whitespace.'
            //                  ''}' is not preceded with whitespace.'
            //                  ''}' is not preceded with whitespace.'
            case 5: { {} }
            // 2 violations above:
            //                  ''{' is not followed by whitespace.'
            //                  ''}' is not preceded with whitespace.'
            case 6: { { } }
            case 7:  {

            }break;
        }
    }
}
