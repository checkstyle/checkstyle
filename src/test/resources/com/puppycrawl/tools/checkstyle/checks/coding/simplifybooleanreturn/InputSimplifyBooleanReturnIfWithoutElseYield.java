/*
SimplifyBooleanReturn


*/

package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanreturn;

public class InputSimplifyBooleanReturnIfWithoutElseYield {

    boolean yieldInSwitchRuleBlock(int value, boolean condition) {
        return switch (value) {
            case 1 -> {
                if (condition) { // violation 'Conditional logic can be removed.'
                    yield true;
                }
                yield false;
            }
            default -> false;
        };
    }

    boolean yieldInBracedCaseGroup(int value, boolean condition) {
        return switch (value) {
            case 1: {
                if (condition) { // violation 'Conditional logic can be removed.'
                    yield true;
                }
                yield false;
            }
            case 2: {
                yield false;
            }
            default: {
                yield false;
            }
        };
    }

    boolean yieldFollowedByNonLiteralYield(int value, boolean condition) {
        return switch (value) {
            case 1 -> {
                if (condition) {
                    yield true;
                }
                yield condition;
            }
            default -> false;
        };
    }
}
