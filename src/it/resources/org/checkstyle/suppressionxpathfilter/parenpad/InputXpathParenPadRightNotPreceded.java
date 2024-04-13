package org.checkstyle.suppressionxpathfilter.parenpad;

public class InputXpathParenPadRightNotPreceded{
    void method() {
        if ( false) {//warn
        }
        if ( true ) {
        }
    }
}
