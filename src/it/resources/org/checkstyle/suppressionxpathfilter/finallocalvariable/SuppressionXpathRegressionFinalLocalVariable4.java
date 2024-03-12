package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable4 {
    class InnerClass {
        public void test1() {
            final boolean b = true;
            int shouldBeFinal; // warn

            if (b) {
                shouldBeFinal = 1;
            } else {
                shouldBeFinal = 2;
            }
        }

    }
}
