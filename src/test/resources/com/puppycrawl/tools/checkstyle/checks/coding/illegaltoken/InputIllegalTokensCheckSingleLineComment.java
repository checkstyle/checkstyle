/*
IllegalToken
tokens = SINGLE_LINE_COMMENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokensCheckSingleLineComment
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
            anotherLabel: // violation, 'Using '\/\/' is not allowed'
            do {
                continue anotherLabel;
            } while (false);
            break label; // violation, 'Using '\/\/' is not allowed'
        }
    }
}
