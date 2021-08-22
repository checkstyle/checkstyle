/*
DescendantToken
limitedTokens = LITERAL_RETURN
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = Return from finally is not allowed.
tokens = LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally {
    public void foo() {
        try {
            System.currentTimeMillis();
        } finally { // violation
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } finally { // violation
            if (System.currentTimeMillis() == 0) {
                return; // return from if statement
            }
        }
    }
    public void thisNull() {
        boolean result = (this == null) || (null == this);
        boolean result2 = (this != null) && (null != this);
        boolean result3 = (this.getClass().getName()
            == String.valueOf(null == System.getProperty("abc")));
    }
}
