/*
IllegalToken
tokens = LITERAL_SWITCH,POST_INC,POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokensCheckSwitchAndPostIncDec
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i) // violation, 'Using 'switch' is not allowed'
        {
            default:
                i--; // violation, 'Using '--' is not allowed'
                i++; // violation, 'Using '\+\+' is not allowed'
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
