/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = 2
maximumNumber = (default)2147483647
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = (default)null
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens4
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i) // violation
        {
            default:
                i--;
                i++;
                break;
        }
    }

    public native void nativeMethod();
}
