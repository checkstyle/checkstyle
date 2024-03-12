/* // violation
IllegalTokenText
format = a href
ignoreCase = (default)false
message = (null)
tokens = COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokenTextCheckCommentToken
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i)
        {
            default:
                i--;
                i++;
                break;
        }
    }

    public native void nativeMethod();

    public void methodWithLiterals()
    {
        final String ref = "<a href=\"";
        final String refCase = "<A hReF=\"";
    }

    public void methodWithLabels() {
        label:
        {
            anotherLabel: // some comment href
            do {
                continue anotherLabel;
            } while (false); // violation below
            break label; // a href
        }
    }
}
