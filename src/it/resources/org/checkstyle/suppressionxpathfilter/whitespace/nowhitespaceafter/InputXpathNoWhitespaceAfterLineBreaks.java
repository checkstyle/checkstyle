package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespaceafter;

public class InputXpathNoWhitespaceAfterLineBreaks {
    public void test() {
        java.lang
            . String s = "Test"; // warn
    }
}
