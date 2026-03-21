/*
DescendantToken
limitedTokens = LITERAL_RETURN
maximumDepth = (default)2147483647
maximumMessage = Return from finally is not allowed.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally {
    public void foo() {
        try {
            System.currentTimeMillis();
        } finally { // violation 'Return from finally is not allowed'
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } finally { // violation 'Return from finally is not allowed'
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
