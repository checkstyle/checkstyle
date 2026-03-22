/*
DescendantToken
limitedTokens = LITERAL_NATIVE
maximumDepth = 0
maximumMessage = Using ''{2}'' is not allowed.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_NATIVE


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens8
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

    public native void nativeMethod(); // violation 'Using 'LITERAL_NATIVE' is not allowed'
}
