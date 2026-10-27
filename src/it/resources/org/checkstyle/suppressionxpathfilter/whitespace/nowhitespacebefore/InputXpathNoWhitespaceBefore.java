/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebefore;

public class InputXpathNoWhitespaceBefore {
    int bad ; // violation "';' is preceded with whitespace."
    int good;
}
