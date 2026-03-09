/*
UseEnhancedSwitch

*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

public class InputUseEnhancedSwitchSwitchExpressions {
    enum Color {
        RED, GREEN, BLUE
    }

    void test(Color c) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        int y = switch (c) {
            case RED:
                yield 1;
            case GREEN:
                yield 2;
            case BLUE:
                yield 3;
        };

        int yEnhanced = switch (c) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testDefault(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        int y = switch (x) {
            case 1: {
                yield 10;
            }
            case 2:
                yield 20;
            default:
                yield 30;
        };
        int yEnhanced = switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 30;
        };
    }
}
