/*
IllegalToken
tokens = COMMENT_CONTENT


*/

// violation 7 lines above, 'Using 'IllegalToken tokens = COMMENT_CONTENT' is not allowed'

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

// violation below, 'Using 'Illegal tokens' is not allowed'
/**
 * Test for illegal tokens
 */
public class InputIllegalTokens4
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
        {   // some comment href // violation
            anotherLabel:
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href // violation
        }
    }
}
