package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodyLoop {
    void test() {
        while (true){} // warn, 'Whitespace is not present before the empty body of 'while'.'
    }
}
