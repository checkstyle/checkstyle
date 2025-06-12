/*
DescendantToken
limitedTokens = LITERAL_THIS, LITERAL_NULL
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = 3
sumTokenCounts = true
tokens = NOT_EQUAL, EQUAL


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally5 {
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
            // violation below 'Total count of 0 is less than minimum count 3 under 'EQUAL'.'
            if (System.currentTimeMillis() == 0) {
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
