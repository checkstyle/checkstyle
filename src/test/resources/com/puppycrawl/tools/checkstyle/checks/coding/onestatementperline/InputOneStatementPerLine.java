/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

public class InputOneStatementPerLine {
    int e = 1, f = 2, g = 5; // 2 violations

    private void foo2() throws IOException {
        try (OutputStream s12 = new PipedOutputStream();
             OutputStream s3 = new PipedOutputStream()) {
        }
    }

    private void foo() {
        toString(); toString(); // violation
    }
}
