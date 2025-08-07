package org.checkstyle.suppressionxpathfilter.whitespace.parenpad;

public class InputXpathParenPadLeftNotFollowed {
    void method() {
        if (false ) {//warn
        }
        if ( true ) {
        }
    }
}
