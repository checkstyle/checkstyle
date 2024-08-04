/*
WhitespaceAfter
tokens = LITERAL_WHEN

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterUnnamedPattern {

    void test(Object o) {
        if (o instanceof Point(int x,_,String color)) { }
        switch (o) {
            case Point(int x,_,String color) -> { }
            default -> { }
        }
    }

    record Point(int x,int y,String color) {
    }
}
