package org.checkstyle.suppressionxpathfilter.nestedfordepth;

public class SuppressionXpathRegressionNestedForDepthMax {
    public void test() {
        for (int i1 = 0; i1 < 10; i1++) {
            for (int i2 = 0; i2 < 10; i2++) {
                for (int i3 = 0; i3 < 10; i3++) {
                    for (int i4 = 0; i4 < 10; i4++) { // warn
                    }
                }
            }
        }
    }
}
