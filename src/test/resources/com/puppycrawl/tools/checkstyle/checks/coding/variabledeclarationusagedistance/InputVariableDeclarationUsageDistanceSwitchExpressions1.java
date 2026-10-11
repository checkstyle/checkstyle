/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceSwitchExpressions1 {

    int testSwitchRegularCase3(int k) {
        return switch (k) {
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
                yield k;
            }
        };
    }

    int testSwitchArrowCase3(int k) {
        return switch (k) {
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
                yield k;
            }
        };
    }
}

