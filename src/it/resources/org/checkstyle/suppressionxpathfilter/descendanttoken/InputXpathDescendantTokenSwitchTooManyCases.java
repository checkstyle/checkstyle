/*
DescendantToken
limitedTokens = LITERAL_CASE
maximumDepth = 2
maximumMessage = (default)null
maximumNumber = 1
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = LITERAL_SWITCH


*/

package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenSwitchTooManyCases {

    void testMethod1() {
        int x = 1;
        Runnable switchLogic = () -> {
            switch(x) { // violation 'Count of 2 for 'LITERAL_SWITCH' descendant 'LITERAL_CASE' exceeds maximum count 1.'
                case 1:
                    System.out.println("hi");
                    break;
                case 2:
                    System.out.println("hello");
                    break;
            }
        };
        switchLogic.run();
    }
}
