package org.checkstyle.checks.suppressionxpathfilter.parenpad;

public class InputXpathParenPadLeftFollowed {
    void method() {
        if ( false) {//warn
        }
        if (true) {
        }
    }
}
