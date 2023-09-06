package org.checkstyle.suppressionxpathfilter.nestedfordepth;

public class SuppressionXpathRegressionNestedForDepth {
    public void test() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) { //warn

                }
            }
        }
    }
}
