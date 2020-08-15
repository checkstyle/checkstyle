//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

/* Config:
 *
 * skipIfLastAndSharedWithCase = false
 */
public class InputDefaultComesLastSwitchExpressions {
    public int method1(int i) {
        int x = 7;
        switch (i) {
            case 1:
            default: // violation
                x = 9;
                break;
            case 2:
                x = 8;
                break;
        }
        return x;
    }

    public int method2(int i) {
        int x = 7;
        switch (i) {
            case 1 -> {
                x = 8;
            }
            default -> { // violation
                x = 9;
            }
            case 2 -> {
                x = 7;
            }
        }

        return x;
    }

    public int method3(int i) {
        return switch (i) {
            case 1 -> 8;
            default -> 9; // violation
            case 2 -> 7;
        };
    }
}
