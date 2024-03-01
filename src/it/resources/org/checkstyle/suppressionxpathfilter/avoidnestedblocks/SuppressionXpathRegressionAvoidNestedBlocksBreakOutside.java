package org.checkstyle.suppressionxpathfilter.avoidnestedblocks;

public class SuppressionXpathRegressionAvoidNestedBlocksBreakOutside {
    int s(int a) {
        int x;
        int y;
        switch (a) {
            case 0: { // warn: break outside block
                x = 2;
                y = -2;
            }
            break;
            default:
                x = 3;
                y = -3;
        }
        return x + y;
    }
}
