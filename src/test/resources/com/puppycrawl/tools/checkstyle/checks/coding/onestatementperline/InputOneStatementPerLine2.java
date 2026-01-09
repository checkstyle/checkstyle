/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

public class InputOneStatementPerLine2 {
    void testNestedInLambda() {
        Runnable r = () -> {
            try (OutputStream s1 = new PipedOutputStream()) {
            }
            catch (IOException e) {
            }
        };
        System.out.println(r);
    }

    void testAssert(int x) {
        assert x > 0; assert x < 100;
        // violation above 'Only one statement per line allowed.'

        assert x != 50
                : "x should not be 50";

        int y = 5; assert y > 0 : "y should be positive";
        // violation above 'Only one statement per line allowed.'
    }

    interface Inner {
        int a = 1; public int method();
        // violation above 'Only one statement per line allowed.'
    }

    enum Type {
        A, B, C; int i;
        // violation above 'Only one statement per line allowed.'
    }

    @interface MultiAnno { float f(); double d(); }
    // violation above 'Only one statement per line allowed.'

}
