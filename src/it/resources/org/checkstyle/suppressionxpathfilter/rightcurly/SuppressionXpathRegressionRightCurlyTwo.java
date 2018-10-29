package org.checkstyle.suppressionxpathfilter.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

public class SuppressionXpathRegressionRightCurlyTwo {
    public void fooMethod() throws IOException {
        try (BufferedReader br1 = new BufferedReader(null)) {
            ; } //warn
    }
}
