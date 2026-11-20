/*
InnerAssignment

*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

public class InputInnerAssignmentSwitchRuleNoBraces {

    void test(boolean cond, int x) {
        int a = 0;
        int b = 0;

        if (cond)
            switch (x) {
                case 1 -> a = 1;
            }
        else
            switch (x) {
                case 1 -> a = 2;
            }

        while (cond)
            switch (x) {
                case 1 -> a = 3;
            }

        for (int i = 0; i < 1; i++)
            switch (x) {
                case 1 -> a = 4;
            }

        do
            switch (x) {
                case 1 -> a = 5;
            }
        while (cond);

        label:
            switch (x) {
                case 1 -> a = 6;
            }

        // Inner assignment should still be reported
        b = (a = 7); // violation 'Inner assignments should be avoided'

        // Switch expression assignment should still be reported
        b = switch (x) {
            case 1 -> a = 8; // violation 'Inner assignments should be avoided'
            default -> 0;
        };
    }
}

