/*
DescendantToken
limitedTokens = LITERAL_SWITCH, POST_INC, POST_DEC
maximumDepth = 0
maximumMessage = Using ''{2}'' is not allowed.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH, POST_INC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens7
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i) // violation 'Using 'LITERAL_SWITCH' is not allowed.'
        {
            default:
                i--; // violation 'Using 'POST_DEC' is not allowed.'
                i++; // violation 'Using 'POST_INC' is not allowed.'
                break;
        }
    }

    public native void nativeMethod();
}
