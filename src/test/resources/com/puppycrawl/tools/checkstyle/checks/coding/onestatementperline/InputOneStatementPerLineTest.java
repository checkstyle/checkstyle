/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

public class InputOneStatementPerLineTest {
    void testNestedInLambda() {
        Runnable r = () -> {
            try (OutputStream s1 = new PipedOutputStream()) {
            }
            catch (IOException e) {
            }
        };
        System.out.println(r);
    }

}
