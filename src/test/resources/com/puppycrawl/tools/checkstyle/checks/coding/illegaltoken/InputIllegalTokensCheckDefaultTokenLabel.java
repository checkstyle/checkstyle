/*
IllegalToken
tokens = (default)LABELED_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokensCheckDefaultTokenLabel
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
        label: // violation, 'Using 'label:' is not allowed'
        {
            anotherLabel: // violation, 'Using 'anotherLabel:' is not allowed'
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href
        }
    }
}
