/*
DescendantToken
limitedTokens = LITERAL_THIS, LITERAL_NULL
maximumDepth = 1
maximumMessage = (default)null
maximumNumber = 1
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = true
tokens = NOT_EQUAL, EQUAL


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenReturnFromFinally4 {
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
        // 2 violations above:
        // 'Total count of 2 exceeds maximum count 1 under 'EQUAL'.'
        // 'Total count of 2 exceeds maximum count 1 under 'EQUAL'.'
        boolean result2 = (this != null) && (null != this);
        // 2 violations above:
        // 'Total count of 2 exceeds maximum count 1 under 'NOT_EQUAL'.'
        // 'Total count of 2 exceeds maximum count 1 under 'NOT_EQUAL'.'
        boolean result3 = (this.getClass().getName()
            == String.valueOf(null == System.getProperty("abc")));
    }
}
