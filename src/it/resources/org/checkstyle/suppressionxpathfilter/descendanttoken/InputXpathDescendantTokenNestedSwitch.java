/*
DescendantToken
limitedTokens = LITERAL_SWITCH
maximumDepth = (default)2147483647
maximumMessage = (default)null
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = CASE_GROUP


*/

package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenNestedSwitch {

    void testMethod1() {
        int x = 1;
        int y = 2;
        switch (x) {
            case 1:
                System.out.println("xyz");
                break;
            case 2: // violation 'Count of 1 for 'CASE_GROUP' descendant 'LITERAL_SWITCH''
                switch (y) {
                    case 1:
                        System.out.println("nested");
                        break;
                }
                break;
        }
    }
}
