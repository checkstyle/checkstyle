/*
DescendantToken
limitedTokens = LITERAL_NATIVE
maximumDepth = (default)2147483647
maximumMessage =  Using ''native'' is not allowed.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
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

    public native void nativeMethod(); // violation 'Using 'native' is not allowed'
}
