/*
IllegalToken
tokens = (default)LABELED_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokens
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
        label: // violation
        {
            anotherLabel: // violation
            do {
                continue anotherLabel;
            } while (false);
            break label; // some a href
        }
    }
}
