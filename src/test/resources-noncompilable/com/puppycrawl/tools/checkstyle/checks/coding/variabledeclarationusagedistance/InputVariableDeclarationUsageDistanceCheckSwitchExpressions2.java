/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceCheckSwitchExpressions2 {
    void issue11973() {
        int i = -1; // violation 'Distance between.*'i'.*and.*first usage is 2, but allowed 1.'
        int x = -1;
        switch (i) {
            case 1 -> {
                x++;
                i++;
            }
        };

        int a = -1;
        int a123 = switch (a) {
            case 1 -> {
                yield 2;
            }
            default -> {
                yield a;
            }
        };

        int b = -1;
        int b123 = switch (b) {
            case 1 -> {
                yield b;
            }
            default -> {
                yield 1;
            }
        };

        int day = 1;
        int c = -1;
        int c123 = switch (c) {
            case 1:
            case 2:
            default:
                c++;
                c--;
                yield day++;
        };

        int day1 = 1;
        int cc = -1;
        int d123 = switch (cc) {
            case 1 -> {
                cc++;
                cc--;
                yield day1;
            }
            default -> {
                yield 0;
            }
        };

        int day2 = 1;
        int ccc = -1;
        int e123 = switch (ccc) {
            case 1 -> {
                ccc++;
                ccc--;
                yield ccc;
            }
            case 2 -> {
                yield 2;
            }
            default -> {
                ccc++;
                ccc--;
                ccc++;
                day2++;
                yield 3;
            }
        };

        int u = 0;
        switch (u) { };
    }
}
