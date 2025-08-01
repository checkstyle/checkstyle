package org.checkstyle.checks.suppressionxpathfilter.parenpad;

public class InputXpathParenPadRightPreceded {
    void method() {
        if (false ) {//warn
        }
        if (true) {
        }
    }
}
