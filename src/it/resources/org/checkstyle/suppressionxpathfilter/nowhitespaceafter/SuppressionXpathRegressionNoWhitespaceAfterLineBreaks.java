package org.checkstyle.suppressionxpathfilter.nowhitespaceafter;

public class SuppressionXpathRegressionNoWhitespaceAfterLineBreaks {
    public void test() {
        java.lang
            . String s = "Test"; // warn
    }
}
