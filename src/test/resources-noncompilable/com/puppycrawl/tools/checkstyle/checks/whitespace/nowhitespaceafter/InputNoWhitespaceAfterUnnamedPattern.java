/*
NoWhitespaceAfter
tokens = (default)ARRAY_INIT,AT,INC,DEC,UNARY_MINUS,UNARY_PLUS,BNOT,LNOT,DOT, \
          ARRAY_DECLARATOR,INDEX_OP
allowLineBreaks = (default)true

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterUnnamedPattern {

    public void test(Object o) {
        if (o instanceof Point(int x, _  , String color)) { }
        switch (o) {
            case Point(int x, _ , String color) -> { }
            default -> {}
        }
    }

    record Point(int x, int y   , String color) { }
}
