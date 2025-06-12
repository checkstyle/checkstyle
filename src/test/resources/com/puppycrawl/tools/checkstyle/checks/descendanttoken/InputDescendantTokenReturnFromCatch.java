/*
DescendantToken
limitedTokens = LITERAL_RETURN
maximumDepth = (default)2147483647
maximumMessage = Return from catch is not allowed.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromCatch {
    public void foo() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) { // violation 'Return from catch is not allowed'
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) { // violation 'Return from catch is not allowed'
            if (System.currentTimeMillis() == 0) {
                return; // return from if statement
            }
        }
    }
}
