/*
DescendantToken
limitedTokens = LITERAL_THIS, LITERAL_NULL
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = custom message
minimumNumber = 3
sumTokenCounts = true
tokens = NOT_EQUAL, EQUAL


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally6 {
    public void foo() {
        try {
            System.currentTimeMillis();
        } finally {
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } finally {
            if (System.currentTimeMillis() == 0) { // violation 'custom message'
                return; // return from if statement
            }
        }
    }
    public void thisNull() {
        boolean result = (this == null) || (null == this); // 2 violations
        boolean result2 = (this != null) && (null != this); // 2 violations
        boolean result3 = (this.getClass().getName()
            == String.valueOf(null == System.getProperty("abc"))); // 2 violations
    }
}
