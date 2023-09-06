package org.checkstyle.suppressionxpathfilter.indentation;

public class SuppressionXpathRegressionIndentationElseWithoutCurly {
    void test() {
        if (true)
            exp();
        else exp();

        if (true)
            exp();
        else
        exp(); // warn
    }
    void exp() {}
}
