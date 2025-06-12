/*
DescendantToken
limitedTokens = LITERAL_THIS, LITERAL_NULL
maximumDepth = (default)2147483647
maximumMessage = What are you doing?
maximumNumber = 1
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = NOT_EQUAL, EQUAL


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally2 {
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
