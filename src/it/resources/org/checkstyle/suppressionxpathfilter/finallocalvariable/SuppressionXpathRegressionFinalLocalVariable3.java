package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable3 {
    class InnerClass {
        public void method(final int i) {
            switch (i) {
                case 1:
                    int foo = 1; // warn
                    break;
                default:
            }
            switch (i) {
                case 1:
                    int foo = 1;
                    break;
                case 2:
                    foo = 2;
                    break;
                default:
            }
        }
    }
}
