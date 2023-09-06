/*
DescendantToken
limitedTokens = (default)
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = (default)0
maximumNumber = (default)2147483647
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = (default)null
tokens = (default)empty


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens // ok
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
