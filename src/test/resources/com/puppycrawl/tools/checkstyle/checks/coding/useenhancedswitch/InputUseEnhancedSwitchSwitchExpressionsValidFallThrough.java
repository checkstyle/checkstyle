/*
UseEnhancedSwitch

*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

public class InputUseEnhancedSwitchSwitchExpressionsValidFallThrough {
    void testValidFallThrough(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        String y = switch (x) {
            case 1:
            case 2:
                yield "One or Two";
            case 3:
                yield "Three";
            default:
                yield "Other";
        };

        // violation below, 'Switch can be replaced with enhanced switch.'
        String yy = switch (x) {
            case 1, 2:
                yield "One or Two";
            case 3:
                yield "Three";
            default:
                yield "Other";
        };

        String yyy = switch (x) {
            case 1, 2 -> "One or Two";
            case 3 -> "Three";
            default -> "Other";
        };

        // ok, has fall-through
        String z = switch (x) {
            case 1:
                System.out.println("One");
            case 2:
                System.out.println("Two");
                yield "One or Two";
            case 3:
                System.out.println("Three");
                yield "Three";
            default:
                yield "Other";
        };

        // ok, has fall-through
        String zz = switch (x) {
            case 1:
                System.out.println("One");
                if (x == Math.random() * 10) {
                    yield "Random One";
                }
            case 2:
                System.out.println("Two");
                yield "One or Two";
            case 3, 4:
                System.out.println("Three or Four");
                yield "Three or Four";
            default:
                yield "Other";
        };
    }
}
