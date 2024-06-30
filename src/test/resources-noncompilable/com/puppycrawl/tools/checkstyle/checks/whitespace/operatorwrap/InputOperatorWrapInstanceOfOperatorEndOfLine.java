/*
OperatorWrap
option = eol
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapInstanceOfOperatorEndOfLine {
    void test(Object o) {
        if (o instanceof
                Integer) {}

        if (o instanceof Integer) {}

        if (o instanceof
                Integer i) {}

        if (o instanceof Integer i) {}

        if (o
                // violation below, ''instanceof' should be on the previous line.'
                instanceof Integer) {
        }
    }

    void test2(Object o) {

        boolean a = o instanceof
                Integer;
        boolean b = o instanceof Integer;

        boolean c = o instanceof
                Integer i;
        boolean d = o instanceof Integer i;
        boolean e = o
                // violation below, ''instanceof' should be on the previous line.'
                instanceof Integer i;
    }

    void test3(Object o) {
       switch (o) {
           case Number n when n instanceof
                   Integer: { } break;
           default: { }
       }
       switch (o) {
           case Number n when n instanceof Integer: { } break;
           default: { }
       }
       switch (o) {

           case Number i when i instanceof
                   Integer _ : { } break;
           default: { }
       }
        switch (o) {
           case Number n when n
                  // violation below, ''instanceof' should be on the previous line.'
                   instanceof Integer: { } break;
           default: { }
       }
    }

    void test4(Object o) {
        if (o instanceof
                Point(int x, int y)) {}

        if (o instanceof Point(int x, int y)) {}

        if (o
                // violation below, ''instanceof' should be on the previous line.'
                instanceof Point(int x, int y)) {}

        switch (o) {
            case Object obj when obj instanceof
                    Point(int _, int _): {} break;
            default: {}
        }
        switch (o) {
            case Object obj when obj
                   // violation below, ''instanceof' should be on the previous line.'
                    instanceof Point(int _, int _): {} break;
            default: {}
        }
    }
    record Point(int x,int y) {}
}
