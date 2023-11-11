package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable2 {
    public void method2() {
        for (int i = 0; i < 5; i++) {
            int x; // warn
            x = 3;
        }
        int y;
        for (int i = 0; i < 5; i++) {
            y = 3;
        }
        for (int i = 0; i < 5; i++) {
            int z;
            for (int j = 0; j < 5; j++) {
                z = 3;
            }
        }
    }
}
