/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
minimumDepth = (default)0
maximumDepth = 2
minimumNumber = 1
maximumNumber = (default)2147483647
sumTokenCounts = (default)false
minimumMessage = switch without "default" clause.
maximumMessage = (default)null
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenMissingSwitchDefault {
    public void foo() {
        int i = 1;
        switch (i) {
        case 1: i++; break;
        case 2: i--; break;
        default: return;
        }
    }
}

class bad_test {
    public void foo() {
        int i = 1;
        switch (i) { // violation
        case 1: i++; break;
        case 2: i--; break;
        }
    }
}
