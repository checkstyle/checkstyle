/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineSwitch {

    void foo() {
        getValue(1);
        getValue2(2);
    }

    int getValue(int a) {
        return switch (a) {
            case 1 -> {
                a++; yield a;  // violation 'Only one statement per line allowed.'
            }
            case 2 -> {
                a++; yield a;  // violation 'Only one statement per line allowed.'
            }
            default -> a;
        };
    }

    int getValue2(int a) {
        return switch (a) {
            case 1 -> {
                a--;
                yield a;
            }
            case 2 -> {
                a += 2;
                yield a;
            }
            default -> a;
        };
    }
}
