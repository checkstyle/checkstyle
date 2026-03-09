/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceCheckSwitchExpressions {

    int testSwitchRegular(int k) {
        return switch (k) {
            case 1: {
                int c = 0;
                int a = 3;
                int b = 2;
                {
                    a = a + b;
                    c = b;
                }
                {
                    c--;
                }
                a = 7;
                yield 2;
            }
            case 2: {
                int arg = 9; // violation 'Distance .* is 2.'
                int d = 0;
                for (int i = 0; i < 10; i++) {
                    d++;
                    if (i > 5) {
                        d += arg;
                    }
                }
                String ar[] = {"1", "2"};
                for (String st : ar) {
                    System.identityHashCode(st);
                }
                yield 4;
            }
            default: {
                int b = 0;
                int c = 0;
                int m = 0; // violation 'Distance between .* declaration and its first usage is 3.'
                int n = 0; // violation 'Distance between .* declaration and its first usage is 2.'
                {
                    c++;
                    b++;
                }
                {
                    n++;
                    m++;
                    b++;
                }
                yield k;
            }
        };
    }

    int testSwitchArrow(int k) {
        return switch (k) {
            case 1 -> {
                int c = 0;
                int a = 3;
                int b = 2;
                {
                    a = a + b;
                    c = b;
                }
                {
                    c--;
                }
                a = 7;
                yield 2;
            }
            case 2 -> {
                int arg = 9; // violation 'Distance .* is 2.'
                int d = 0;
                for (int i = 0; i < 10; i++) {
                    d++;
                    if (i > 5) {
                        d += arg;
                    }
                }
                String ar[] = {"1", "2"};
                for (String st : ar) {
                    System.identityHashCode(st);
                }
                yield 3;
            }
            default -> {
                int b = 0;
                int c = 0;
                int m = 0; // violation 'Distance between .* declaration and its first usage is 3.'
                int n = 0; // violation 'Distance between .* declaration and its first usage is 2.'
                {
                    c++;
                    b++;
                }
                {
                    n++;
                    m++;
                    b++;
                }
                yield k;
            }
        };
    }
}

