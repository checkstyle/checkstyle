/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptySwitchBlockStatements = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
tokens = LITERAL_WHEN

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundLiteralWhen {

    void test(Object o) {
        switch (o) {
            case Integer i when(i == 0) -> {} // violation, ''when' is not followed by whitespace'
            // violation below, ''when' is not followed by whitespace'
            case String s when(s.equals("a")) -> {}
            // violation below, ''when' is not followed by whitespace'
            case Point(int x, int y ) when(x>=0 && y >=0) -> {}
            default -> {}
        }

        switch (o) {
            case Point(int x, int y )when(x>=0 && y >=0) -> {}
            // 2 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not preceded with whitespace'
            case Point(int x, int y )when!(x>=0 && y >=0) -> {}
            // 2 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not preceded with whitespace'
            default -> {}
        }

        switch (o) {
            case Integer i when (i == 0) -> {}
            case String s when (s.equals("a")) -> {}
            case Point(int x, int y ) when (x>=0 && y >=0) -> {}
            default -> {}
        }
    }

    void test2(Object o) {
        switch (o) {
            case Integer i when
                    (i == 0) -> {}
            case String s when(  // violation, ''when' is not followed by whitespace'
                    s.equals("a")) -> {}

            case Point(int x, int y )
                    when
                    (x>=0 && y >=0) -> {}
            default -> {}
        }

        switch (o) {
            case Point(int x, int y )
                    when(x>=0 && y >=0) -> {}
            // violation above, ''when' is not followed by whitespace'
            // violation below,  ''when' is not preceded with whitespace'
            case Point(int x, int y )when
                    !(x>=0 && y >=0) -> {}
            default -> {}
        }
    }
    record Point(int x, int y) {}
}
