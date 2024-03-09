package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class InputXpathFinalLocalVariable8 {
    private void checkCodeBlock() {
        try {
            int start = 0; // warn

            final int from;
            if (true) {
                from = 0;
            } else {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
