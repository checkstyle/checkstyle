package org.checkstyle.suppressionxpathfilter.indentation;

public class SuppressionXpathRegressionIndentationIfWithoutCurly {
    void test() {
        if (true)
                e();
        if (true)
        e(); // warn
    }
    void e() {};
}

