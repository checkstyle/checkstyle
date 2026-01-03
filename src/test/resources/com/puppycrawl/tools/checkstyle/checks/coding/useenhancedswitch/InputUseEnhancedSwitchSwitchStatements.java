/*
UseEnhancedSwitch

*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

public class InputUseEnhancedSwitchSwitchStatements {
    void test(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        switch (x) {
            case 1:
                System.out.println("One");
                break;
            case 2:
                System.out.println("Two");
        }

        switch (x) {
            case 1 -> {
                System.out.println("One");
            }
            case 2 -> System.out.println("Two");
        }
    }

    void testDefault(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        switch (x) {
            case 1: {
                System.out.println("One");
                break;
            }
            case 2:
                System.out.println("Two");
                break;
            default:
        }

        switch (x) {
            case 1 -> {
                System.out.println("One");
            }
            case 2 -> System.out.println("Two");
            default -> {
            }
        }
    }

    void testReturnLabels(int x) {
        // violation below, 'Switch can be replaced with enhanced switch.'
        switch (x) {
            case 1:
                return;
            case 2:
                return;
            default:
                return;
        }
    }

    void testReturnRules(int x) {
        switch (x) {
            case 1 -> {
                return;
            }
            case 2 -> {
                return;
            }
            default -> {
                return;
            }
        }
    }
}
