/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = 2
maximumNumber = (default)2147483647
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = (default)null
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
