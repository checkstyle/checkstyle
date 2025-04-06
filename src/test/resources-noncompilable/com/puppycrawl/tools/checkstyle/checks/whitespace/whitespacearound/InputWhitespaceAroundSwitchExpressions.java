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

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

import java.util.Optional;

public class InputWhitespaceAroundSwitchExpressions {

    Optional<String> someMethod(int arg) {
        return Optional.ofNullable(switch (arg) { // ok - switch expression is skipped by design
            default -> null;
        });
    }

    Optional<String> someMethod2(int a) {
        return Optional.ofNullable(((switch (a) { // ok - switch expression is skipped by design
            default -> null;
        })));
    }

    Object someMethod3(int a) {
        return switch(a) { // ok - switch expression is skipped by design
            default -> null;
        };
    }

    Object someMethod3(String b) {
        return (switch(b.trim()) { // ok - switch expression is skipped by design
            default -> null;
        });
    }
}
