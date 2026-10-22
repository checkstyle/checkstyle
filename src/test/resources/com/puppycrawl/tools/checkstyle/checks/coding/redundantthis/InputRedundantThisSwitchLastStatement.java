/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisSwitchLastStatement {
    int value;

    int lastReturn(int choice) {
        switch (choice) {
            case 1:
                return this.value; // violation 'Redundant "this", field'
            default:
                return 0;
        }
    }

    int adjacentRequired(int choice, int value) {
        switch (choice) {
            case 1:
                this.value = value;
                return this.value;
            default:
                return 0;
        }
    }

    int precedingUnrelated(int choice) {
        switch (choice) {
            case 1:
                System.out.println(choice);
                return this.value; // violation 'Redundant "this", field'
            default:
                return 0;
        }
    }
}
