/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebefore;

public class InputXpathNoWhitespaceBeforeLineBreaks {
    public void test() {
        int[][] array = { { 1, 2 }
            , { 3, 4 } }; // violation "',' is preceded with whitespace."
    }
}
