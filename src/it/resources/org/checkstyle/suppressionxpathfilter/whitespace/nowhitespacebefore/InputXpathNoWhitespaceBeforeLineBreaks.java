package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebefore;

public class InputXpathNoWhitespaceBeforeLineBreaks {
    public void test() {
        int[][] array = { { 1, 2 }
            , { 3, 4 } }; // warn
    }
}
