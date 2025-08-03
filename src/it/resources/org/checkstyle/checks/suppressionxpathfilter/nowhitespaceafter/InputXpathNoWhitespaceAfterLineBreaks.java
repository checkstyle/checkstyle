package org.checkstyle.checks.suppressionxpathfilter.nowhitespaceafter;

public class InputXpathNoWhitespaceAfterLineBreaks {
    public void test() {
        java.lang
            . String s = "Test"; // warn
    }
}
