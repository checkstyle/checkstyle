package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable3 {
    public void testMethod() {
        final boolean b = true;
        int shouldBeFinal; // warn

        if (b) {
            shouldBeFinal = 1;
        }
        else {
            shouldBeFinal = 2;
        }
    }
}
