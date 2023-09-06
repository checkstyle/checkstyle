package org.checkstyle.suppressionxpathfilter.fallthrough;

public class SuppressionXpathRegressionFallThroughOne {
    public void test() {
        int id = 0;
        switch (id) {
            case 0: break;
            case 1: if (1 == 0) {
                break;
            };
            case 2: break; //warn
        }
    }
}
