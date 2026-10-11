/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = 2
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenIllegalTokens4
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        // violation below 'Count of 1 for '.*_SWITCH' descendant '.*_DEFAULT' is less than .* 2.'
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
