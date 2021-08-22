/*
DescendantToken
limitedTokens = LITERAL_RETURN
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = Return from catch is not allowed.
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromCatch {
    public void foo() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) { // violation
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) { // violation
            if (System.currentTimeMillis() == 0) {
                return; // return from if statement
            }
        }
    }
}
