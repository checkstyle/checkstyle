/*
WhitespaceAfter
tokens = LITERAL_WHEN

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralWhen {

    void test(Object o) {
        switch (o) {
            case Integer i when(i == 0) -> {} // violation, ''when' is not followed by whitespace'
            // violation below, ''when' is not followed by whitespace'
            case String s when(s.equals("a")) -> {}
            // violation below, ''when' is not followed by whitespace'
            case Point(int x, int y ) when(x>=0 && y >=0) -> {}
            // violation below, ''when' is not followed by whitespace'
            case Point(int x, int y ) when!(x>=0 && y >=0) -> {}
            default -> {}
        }

        switch (o) {
            case Integer i when (i == 0) -> {}
            case String s when (s.equals("a")) -> {}
            case Point(int x, int y ) when (x>=0 && y >=0) -> {}
            default -> {}
        }
    }

    void testLineBreaks(Object o) {
        switch (o) {
            // violation below, ''when' is not followed by whitespace'
            case Integer i when(
                    i == 0) -> {}
            case String s
                    when
                    (s.equals("a")) -> {}
            case Point(int x, int y )
                    when
                    (x>=0 && y >=0) -> {}
            case Point(int x, int y )
                    // violation below, ''when' is not followed by whitespace'
                    when!(x>=0 && y >=0) -> {}
            default -> {}
        }
    }
    record Point(int x, int y) {}
}
