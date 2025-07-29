/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = 0
minimumDepth = 3
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens5
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
}
