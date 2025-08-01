package org.checkstyle.checks.suppressionxpathfilter.parenpad;

public class InputXpathParenPadRightNotPreceded{
    void method() {
        if ( false) {//warn
        }
        if ( true ) {
        }
    }
}
