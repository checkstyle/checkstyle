/*
DescendantToken
limitedTokens = LITERAL_DEFAULT
maximumDepth = 2
maximumMessage = (default)null
maximumNumber = (default)2147483647
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = 1
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH


*/

package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenSwitchNoDefault {

    void testMethod1() {
        int x = 1;
        switch (x) { // violation 'Count of 0 for 'LITERAL_SWITCH' descendant 'LITERAL_DEFAULT''
            case 1:
                System.out.println("hi");
                break;
        }
    }
}
