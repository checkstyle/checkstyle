/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyConciseBlockNotAloneOnLine {
    // violation below ''}' at column 13 should have line break after.'
    static {} int b;
    // violation below ''}' at column 20 should have line break after.'
    void method() {} void method2() {}

    void method3() {
        int a = 2;
        // violation below ''}' at column 22 should have line break after.'
        if (a == 1) {} if (a == 2) {
            a++;
        } else if (a == 3) {
            a++;
        } else {
            a = 1;
        }
    }

    // violation below ''}' at column 25 should have line break after.'
    static class Inner {} static class Inner2 {}
}
