/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisSwitchCaseGroupScope {
    private int x;

    void localFromPreviousCaseGroup(int k) {
        switch (k) {
            case 1:
                int x = 1;
                System.out.println(x);
                break;
            case 2:
                x = 5;
                this.x = x;
                break;
            default:
                break;
        }
    }

    void localOutOfScopeAfterSwitch(int k) {
        switch (k) {
            case 1:
                int x = 1;
                System.out.println(x);
                break;
            default:
                break;
        }
        System.out.println(k);
        this.x = 2;
        // violation above, 'Redundant "this", field 'x' can be accessed directly.'
    }

    void switchRuleBlocksHaveOwnScope(int k) {
        switch (k) {
            case 1 -> {
                int x = 1;
                System.out.println(x);
            }
            default -> {
                this.x = 3;
                // violation above, 'Redundant "this", field 'x' can be accessed directly.'
            }
        }
    }

    int switchExpressionCaseGroups(int k) {
        return switch (k) {
            case 1:
                int x = 1;
                yield x;
            default:
                x = 4;
                this.x = x;
                yield x;
        };
    }
}
