package org.checkstyle.suppressionxpathfilter.nowhitespacebefore;

public class InputXpathNoWhitespaceBeforeLineBreaks {
    public void test() {
        int[][] array = { { 1, 2 }
            , { 3, 4 } }; // warn
    }
}
