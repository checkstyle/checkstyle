/*
DefaultComesLast
skipIfLastAndSharedWithCase = true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public class InputDefaultComesLastSwitchExpressionsSkipIfLast {
    public int method1(int i) {
        int x = 7;
        switch (i) {
            case 1:
            default: // ok
                x = 9;
                break;
            case 2:
                x = 8;
                break;
        }
        return x;
    }

    // This should still be a violation, since switch rules are not subject to fall through
    public int method2(int i) {
        int x = 7;
        switch (i) {
            case 1 -> {
                x = 8;
            }
            default -> { // violation 'Default should be last label in the switch.'
                x = 9;
            }
            case 2 -> {
                x = 7;
            }
        }

        return x;
    }

    // This should still be a violation, since switch rules are not subject to fall through
    public int method3(int i) {
        return switch (i) {
            case 1 -> 8;
            default -> 9; // violation 'Default should be last label in the switch.'
            case 2 -> 7;
        };
    }

    public int method4(int i) {
        return switch (i) {
            case 1 -> 8;
            case 2 -> 7;
            default -> 9; // ok
        };
    }
}
