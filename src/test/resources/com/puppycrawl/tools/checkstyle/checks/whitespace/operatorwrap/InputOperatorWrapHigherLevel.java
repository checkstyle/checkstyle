/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF
higherLevelWrap = true


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapHigherLevel {

    void m() {
        int a = 1, b = 2, c = 3, d = 4;

        // 1. Higher-level operator wrapping accepted
        int x1 = a
                + b * c; // Valid: + is higher level than *

        // 2. Lower-level operator wrapping reported
        int x2 = a + b // violation 'should be on a new line.'
                * c;

        // 3. Same-precedence nested operators not reported
        int x3 = a + b
                + c; // Valid: same precedence

        // 4. Parenthesized nested expression (Issue 20716 variant)
        int x4 = a / (b // violation 'should be on a new line.'
                - c);

        // 5. No higher-level operator available
        myMethod(a + b, c
                * d); // Valid: * is the only operator in its expression

        // Another valid nested parenthesis case where higher level is wrapped
        int x5 = a
                / (b - c); // Valid: wrapped at /

        // 6. Same-precedence nested operator in parentheses
        int x6 = a + (b
                - c); // Valid: same precedence as +

        // 7. Parent operator already wrapped
        int x7 = a
                + b
                * c; // Valid: + is already wrapped

        // 8. Lambda expression where operator is wrapped
        java.util.function.Function<String, String>
            f8 = s -> "a" // violation ''->' should be on a new line.'
                + "b";
    }

    <T extends Comparable<T>
            & java.io.Serializable>
    void testGenerics() {}

    void myMethod(int a, int b) {}
}
