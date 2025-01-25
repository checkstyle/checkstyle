package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenSwitchTooManyCases {

    void testMethod1() {
        int x = 1;
        switch (x) { // warn
            case 1:
                System.out.println("hi");
                break;
            case 2:
                System.out.println("hello");
                break;
        }
    }
}

