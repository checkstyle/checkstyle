package org.checkstyle.suppressionxpathfilter.descendanttoken;

public class InputXpathDescendantTokenNestedSwitch {

    void testMethod1() {
        int x = 1;
        int y = 2;
        switch (x) { // warn
            case 1:
                System.out.println("xyz");
                break;
            case 2:
                switch (y) {
                    case 1:
                        System.out.println("nested");
                        break;
                }
                break;
        }
    }
}

