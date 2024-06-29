/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/
//non-compiled with javac: Compilable with Java21

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapInstanceOfOperator {
    void test(Object o) {
        // violation below, ''instanceof' should be on a new line.'
        if (o instanceof
                Integer) {}

        if (o instanceof Integer) {}

        // violation below, ''instanceof' should be on a new line.'
        if (o instanceof
                Integer i) {}

        if (o instanceof Integer i) {}

        if (o
                instanceof Integer) {
        }
    }

    void test2(Object o) {
        // violation below, ''instanceof' should be on a new line.'
        boolean a = o instanceof
                Integer;
        boolean b = o instanceof Integer;
        // violation below, ''instanceof' should be on a new line.'
        boolean c = o instanceof
                Integer i;
        boolean d = o instanceof Integer i;
        boolean e = o
                instanceof Integer i;
    }

    void test3(Object o) {
       switch (o) {
           // violation below, ''instanceof' should be on a new line.'
           case Number n when n instanceof
                   Integer: { } break;
           default: { }
       }
       switch (o) {
           case Number n when n instanceof Integer: { } break;
           default: { }
       }
       switch (o) {
           // violation below, ''instanceof' should be on a new line.'
           case Number i when i instanceof
                   Integer _ : { } break;
           default: { }
       }
        switch (o) {
           case Number n when n
                   instanceof Integer: { } break;
           default: { }
       }
    }

    void test4(Object o) {
        // violation below, ''instanceof' should be on a new line.'
        if (o instanceof
                Point(int x, int y)) {}

        if (o instanceof Point(int x, int y)) {}

        if (o
                instanceof Point(int x, int y)) {}

        switch (o) {
            // violation below, ''instanceof' should be on a new line.'
            case Object obj when obj instanceof
                    Point(int _, int _): {} break;
            default: {}
        }
        switch (o) {
            case Object obj when obj
                    instanceof Point(int _, int _): {} break;
            default: {}
        }
    }
    record Point(int x,int y) {}
}
