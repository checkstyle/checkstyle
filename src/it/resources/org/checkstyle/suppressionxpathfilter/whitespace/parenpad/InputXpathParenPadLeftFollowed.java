package org.checkstyle.suppressionxpathfilter.whitespace.parenpad;

public class InputXpathParenPadLeftFollowed {
    void method() {
        if ( false) {//warn
        }
        if (true) {
        }
    }
}
