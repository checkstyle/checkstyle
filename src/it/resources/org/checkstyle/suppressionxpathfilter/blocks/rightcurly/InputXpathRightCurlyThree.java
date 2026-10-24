/*
RightCurly
option = alone
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package org.checkstyle.suppressionxpathfilter.blocks.rightcurly;

public class InputXpathRightCurlyThree {
    public void sample(boolean flag) {
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); } // violation ''}''
    }
}
