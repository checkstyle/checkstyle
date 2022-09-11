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
        int x = -1; // ok
        switch (i) {
            case 1 -> {
                x++;
                i++;
            }
        };

        int a = -1; // ok
        switch (a) {
            case 1 -> 2;
            case 2 -> a;
        };

        int b = -1; // ok
        switch (b) {
            case 1 -> b;
            case 2 -> 1;
        };

        int day = 1; // violation 'Distance between.*'day'.*and.*first usage is 3, but allowed 1.'
        int c = -1;
        switch (c) {
            case 1:
            case 2:
            case 3:
                c++;
                c--;
                day++;
        };

        int day1 = 1; // violation 'Distance between.*'day1'.*and.*first usage is 3, but allowed 1.'
        int cc = -1;
        switch (cc) {
            case 1 -> {
                cc++;
                cc--;
                yield day1;
            }
        };

        int day2 = 1; // violation 'Distance between.*'day2'.*and.*first usage is 4, but allowed 1.'
        int ccc = -1;
        switch (ccc) {
            case 1 -> {
                ccc++;
                ccc--;
                yield ccc;
            }
            case 2 -> {
                yield 2;
            }
            case 3 -> {
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
