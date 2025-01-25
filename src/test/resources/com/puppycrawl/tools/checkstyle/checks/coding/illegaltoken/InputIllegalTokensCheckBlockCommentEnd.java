/*
IllegalToken
tokens = BLOCK_COMMENT_END


*/ // violation, 'Using '\*\/' is not allowed'

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */ // violation, 'Using '\*\/' is not allowed'
public class InputIllegalTokensCheckBlockCommentEnd
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
            } while (false);
            break label; // some a href
        }
    }
}
