/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceCheckSwitchExpressions {


    int howMany1(int k) {
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
                int arg = 9; // violation
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

            case 3: {
                int blockNumWithSimilarVar = 3;
                int dist = 0;
                int index = 0;
                int block = 0;

                if (blockNumWithSimilarVar <= 1) {
                    do {
                        dist++;
                        if (block > 4) {
                            break;
                        }
                        index++;
                        block++;
                    } while (index < 7);
                } else {
                    while (index < 8) {
                        dist += block;
                        index++;
                        block++;
                    }
                }
                yield 56;

            }
            default: {
                int b = 0;
                int c = 0;
                int m = 0; // violation
                int n = 0; // violation
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

    int howMany2(int k) {
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
                int arg = 9; // violation
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
            case 3 -> {
                int blockNumWithSimilarVar = 3;
                int dist = 0;
                int index = 0;
                int block = 0;

                if (blockNumWithSimilarVar <= 1) {
                    do {
                        dist++;
                        if (block > 4) {
                            break;
                        }
                        index++;
                        block++;
                    } while (index < 7);
                } else {
                    while (index < 8) {
                        dist += block;
                        index++;
                        block++;
                    }
                }
                yield 4;
            }
            default -> {
                int b = 0;
                int c = 0;
                int m = 0; // violation
                int n = 0; // violation
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

    public boolean equals1(Object obj) {
        int i = 1;
        int a = 5;
        int b = 6;
        switch (i) {
            case 1:
                int count; // violation
                a = a + b;
                b = a + a;
                count = b;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
        }
        return false;
    }

    public boolean equals2(Object obj) {
        int i = 1;
        int a = 5;
        int b = 6;
        switch (i) {
            case 1 -> {
                int count; // violation
                a = a + b;
                b = a + a;
                count = b;
            }
        }
        return false;
    }
}


