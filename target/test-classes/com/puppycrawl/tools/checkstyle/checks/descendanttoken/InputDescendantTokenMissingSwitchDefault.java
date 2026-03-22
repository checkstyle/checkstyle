/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
maximumDepth = 2
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = switch without "default" clause.
minimumNumber = 1
sumTokenCounts = (default)false
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
        switch (i) { // violation 'switch without "default" clause.'
        case 1: i++; break;
        case 2: i--; break;
        }
    }
}
