/*
GoogleRightCurly
tokens = METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyMethodsAndConstructors {

    private int value;

    InputGoogleRightCurlyMethodsAndConstructors() {
        value = 0;}
    // violation above ''}' at column 19 should be alone on a line'

    InputGoogleRightCurlyMethodsAndConstructors(int value) {
        this.value = value;
    }

    void doNothing() {}

    int getValue() { return value; }
    // violation above ''}' at column 36 should be alone on a line'

    void doSomething() {
        if (value > 0) {
            value++;
        } else {
            value--;
        }}
    // violation above ''}' at column 10 should be alone on a line'

    void loop() {
        do {
            value++;
        } while (value < 10);
    }

    record Point(int x, int y) {
        Point {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException();
            }
        }

        Point(int x) {
            this(x, 0);}
        // violation above ''}' at column 24 should be alone on a line'

        int sum() { return x + y; }
        // violation above ''}' at column 35 should be alone on a line'
    }
}
