/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebefore;

public class InputXpathNoWhitespaceBeforeTokens {
    public java .lang.String test() { // violation "'.' is preceded with whitespace."
        return "";
    }
}
