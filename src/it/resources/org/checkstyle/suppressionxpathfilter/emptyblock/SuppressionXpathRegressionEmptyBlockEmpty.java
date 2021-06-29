package org.checkstyle.suppressionxpathfilter.emptyblock;

public class SuppressionXpathRegressionEmptyBlockEmpty {
    private void emptyLoop() {
        for (int i = 0; i < 10; i++) {} // warn

    }
}
