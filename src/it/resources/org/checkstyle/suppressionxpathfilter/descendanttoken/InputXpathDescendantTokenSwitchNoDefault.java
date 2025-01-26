package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenSwitchNoDefault {

    void testMethod1() {
        int x = 1;
        switch (x) { // warn
            case 1:
                System.out.println("hi");
                break;
        }
    }
 }
