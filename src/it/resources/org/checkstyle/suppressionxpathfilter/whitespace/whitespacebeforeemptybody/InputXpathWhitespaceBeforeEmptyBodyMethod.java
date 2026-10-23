/*
WhitespaceBeforeEmptyBody


*/

package org.checkstyle.suppressionxpathfilter.whitespace.whitespacebeforeemptybody;

public class InputXpathWhitespaceBeforeEmptyBodyMethod {

    void foo(){} // violation "'{' is not preceded with whitespace."

    void bar() {
    }
}
