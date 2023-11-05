package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable1 {
    public void testMethod() {
        int x; // warn
        x = 3;
    }
}
