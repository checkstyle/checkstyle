/*
AvoidNestedBlocks
allowInSwitchCase = (default)false

*/

package org.checkstyle.suppressionxpathfilter.blocks.avoidnestedblocks;

public class InputXpathAvoidNestedBlocksBreakOutside {
    int s(int a) {
        int x;
        int y;
        switch (a) {
            case 0: { // violation 'Avoid using nested blocks.'
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
