package org.checkstyle.suppressionxpathfilter.whitespace.parenpad;

public class InputXpathParenPadRightNotPreceded{
    void method() {
        if ( false) {//warn
        }
        if ( true ) {
        }
    }
}
