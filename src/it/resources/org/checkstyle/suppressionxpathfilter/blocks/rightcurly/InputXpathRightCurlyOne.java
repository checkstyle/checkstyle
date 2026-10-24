/*
RightCurly
option = (default)same
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package org.checkstyle.suppressionxpathfilter.blocks.rightcurly;

public class InputXpathRightCurlyOne {
    public void test(int x) {
        if (x > 0)
        {
            return;
        } // violation ''}' at column 9 should be on the same line'
        else if (x < 0) {
            ;
        }
    }
}
