package org.checkstyle.suppressionxpathfilter.catchparametername;

public class SuppressionXpathRegressionCatchParameterNameNested {
    public static class NestedClass {
        void method() {
            if (true) {
                try {
                    try {
                    } catch (Exception i) { // warn
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
