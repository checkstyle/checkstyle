/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineExpressions {

    void method() {
        // violation below 'Only one statement per line allowed.'
        int a = 1; a
                =
                2 + 3;

        // violation below 'Only one statement per line allowed.'
        int b = 3; b
                =
                a + a;
    }

    void methodCorrect() {
        int a = 1;
        a = 2 + 3;
        int b = 3;
        b = a + a;
    }

    void methodIncrement() {
        // violation below 'Only one statement per line allowed.'
        int c = 0; c
                ++;

        // violation below 'Only one statement per line allowed.'
        int d = 0; d
                --;
    }

    void methodCompoundAssign() {
        // violation below 'Only one statement per line allowed.'
        int e = 1; e
                +=
                5;

        // violation below 'Only one statement per line allowed.'
        int f = 10; f
                -=
                3;
    }

    void methodMethodCall() {
        // violation below 'Only one statement per line allowed.'
        StringBuilder sb = new StringBuilder(); sb
                .append("a")
                .append("b");

        // violation below 'Only one statement per line allowed.'
        int g = 1; System
                .out
                .println(g);
    }

    void methodTernary() {
        // violation below 'Only one statement per line allowed.'
        int h = 1; int i = h > 0
                ? 1
                : -1;
    }

}
