package org.checkstyle.suppressionxpathfilter.avoidnestedblocks;

public class SuppressionXpathRegressionAvoidNestedBlocksSwitch1 {

    int s(int a) {
        int x;
        int y;
        switch (a) {
            case 0: { // warn: break outside block
                x = 0;
                y = 0;
            }
            break;
            case 1:
                x = 1;
            { // warn: statement outside block
                y = -1;
                break;
            }
            case 2: { // warn: allowInSwitchCase=false
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
