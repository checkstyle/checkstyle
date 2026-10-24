/*
AvoidNestedBlocks
allowInSwitchCase = true

*/

package org.checkstyle.suppressionxpathfilter.blocks.avoidnestedblocks;

public class InputXpathAvoidNestedBlocksAllowedInSwitchCase {

    int s(int a) {
        int x;
        int y;
        switch (a) {
            case 0:
                x = 1;
            { // violation 'Avoid using nested blocks.'
                y = -1;
                break;
            }
            case 1: {
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
