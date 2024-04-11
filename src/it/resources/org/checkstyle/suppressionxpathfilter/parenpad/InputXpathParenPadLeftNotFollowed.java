package org.checkstyle.suppressionxpathfilter.parenpad;

public class InputXpathParenPadLeftNotFollowed {
    void method() {
        if (false ) {//warn
        }
        if ( true ) {
        }
    }
}
