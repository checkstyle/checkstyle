/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
minimumDepth = (default)0
maximumDepth = 1
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = (default)null
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens6 // ok
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
