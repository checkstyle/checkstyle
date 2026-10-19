/*
AvoidNestedBlocks
allowInSwitchCase = (default)false

*/

package org.checkstyle.suppressionxpathfilter.blocks.avoidnestedblocks;

public class InputXpathAvoidNestedBlocksVariable {

    void varAssign() {
        int whichIsWhich = 0;
        { // violation 'Avoid using nested blocks.'
            whichIsWhich = 2;
        }
    }
}
