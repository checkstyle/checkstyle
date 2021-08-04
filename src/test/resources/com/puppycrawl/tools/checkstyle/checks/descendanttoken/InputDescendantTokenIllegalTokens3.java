/*
DescendantToken
limitedTokens = LITERAL_NATIVE
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = Using ''native'' is not allowed.
tokens = LITERAL_NATIVE


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens3
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

    public native void nativeMethod(); // violation
}
