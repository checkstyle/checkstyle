/*
DescendantToken
limitedTokens = (default)
maximumDepth = 2
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = 1
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenTestLimitedToken {
}

class Test {
    public static void main(String[] args) {
        int x = 1;
        switch (x) {
            case 1:
                x--;
                break;
            default:
                x++;
                break;
        }

        int y = 1;
        switch (y) {
            case 1:
                y++;
                break;
        }
    }
}
