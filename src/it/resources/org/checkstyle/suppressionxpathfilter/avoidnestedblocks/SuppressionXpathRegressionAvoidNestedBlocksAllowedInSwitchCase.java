package org.checkstyle.suppressionxpathfilter.avoidnestedblocks;

public class SuppressionXpathRegressionAvoidNestedBlocksAllowedInSwitchCase {

    int s(int a) {
        int x;
        int y;
        switch (a) {
            case 0:
                x = 1;
            { // warn: statement outside block
                y = -1;
                break;
            }
            case 1: { // ok: allowInSwitchCase=true
                x = 2;
                y = -2;
                break;
            }
            default:
                x = 3;
                y = -3;
        }
        return x + y;
    }
}
