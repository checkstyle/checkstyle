package org.checkstyle.suppressionxpathfilter.nestedifdepth;

public class SuppressionXpathRegressionNestedIfDepthMax {
    public void test() {
        int a = 1;
        int b = 2;
        int c = 3;
        if (a > b) {
            if (c > b) {
                if (c > a) {
                    if (1 == 2) {
                        if (2 == 3) { // warn

                        }
                    }
                }
            }
        }
    }
}
