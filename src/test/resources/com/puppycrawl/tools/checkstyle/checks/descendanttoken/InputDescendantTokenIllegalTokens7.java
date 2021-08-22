/*
DescendantToken
limitedTokens = LITERAL_SWITCH, POST_INC, POST_DEC
minimumDepth = (default)0
maximumDepth = 0
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = Using ''{2}'' is not allowed.
tokens = LITERAL_SWITCH, POST_INC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens7
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i) // violation
        {
            default:
                i--; // violation
                i++; // violation
                break;
        }
    }

    public native void nativeMethod();
}
