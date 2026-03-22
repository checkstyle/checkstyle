/*
DescendantToken
limitedTokens = LITERAL_NATIVE
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_NATIVE


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens2
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
    // violation below 'Count of 1 for 'LITERAL_NATIVE' descendant 'LITERAL_NATIVE' exceeds .* 0.'
    public native void nativeMethod();
}
