/*
IllegalToken
tokens = LITERAL_NATIVE


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokensCheckTokenNative
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

    public native void nativeMethod(); // violation, 'Using 'native' is not allowed'

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
