package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable9 {
    private void checkCodeBlock() {
        try {

            final int from;
            if (true) {
                from = 0;
            } else if (true) {
                boolean body = false; // warn
                if (body) return;
                from = 0;
            } else {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

