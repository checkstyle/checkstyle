package org.checkstyle.suppressionxpathfilter.nowhitespaceafter;

public class InputXpathNoWhitespaceAfterLineBreaks {
    public void test() {
        java.lang
            . String s = "Test"; // warn
    }
}
