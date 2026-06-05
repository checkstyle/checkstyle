package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodyLoop {

    void method() {
        for (int i = 0; i < 1; i++){ // warn
           // comment
        }
    }
}
