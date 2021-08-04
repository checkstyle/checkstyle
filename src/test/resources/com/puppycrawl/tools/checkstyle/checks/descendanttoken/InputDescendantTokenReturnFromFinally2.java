/*
DescendantToken
limitedTokens = LITERAL_THIS, LITERAL_NULL
minimumDepth = (default)0
maximumDepth = (default)2147483647
minimumNumber = (default)0
maximumNumber = 1
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = What are you doing?
tokens = NOT_EQUAL, EQUAL


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally2 { // ok
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
