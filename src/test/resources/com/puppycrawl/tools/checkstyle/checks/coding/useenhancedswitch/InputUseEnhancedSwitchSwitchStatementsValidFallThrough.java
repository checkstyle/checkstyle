/*
UseEnhancedSwitch

*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

public class InputUseEnhancedSwitchSwitchStatementsValidFallThrough {
    void testValidFallthrough(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        switch (x) {
            case 1:
            case 2:
                System.out.println("One or Two");
                break;
            case 3:
                System.out.println("Three");
                break;
        }
        // violation below, 'Switch can be replaced with enhanced switch.'
        switch (x) {
            case 1, 2:
                System.out.println("One or Two");
                break;
            case 3:
                System.out.println("Three");
                break;
        }

        switch (x) {
            case 1, 2 -> System.out.println("One or Two");
            case 3 -> System.out.println("Three");
        }

        // ok, has fall-through
        switch (x) {
            case 1:
                System.out.println("One");
            case 2:
                System.out.println("Two");
                break;
            case 3:
                System.out.println("Three");
                break;
        }

        // ok, has fall-through
        switch (x) {
            case 1:
                System.out.println("One");
            case 2:
                System.out.println("Two");
                break;
            case 3, 4:
                System.out.println("Three or Four");
                break;
        }

        // ok, has fall-through
        switch (x) {
            case 1:
                System.out.println("One");
                if (x == Math.random() * 10) {
                    break;
                }
            case 2:
                System.out.println("Two");
                break;
            case 3, 4:
                System.out.println("Three or Four");
                break;
        }
    }
}
