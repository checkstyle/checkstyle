package org.checkstyle.suppressionxpathfilter.blocks.avoidnestedblocks;

public class InputXpathAvoidNestedBlocksVariable {

    void varAssign() {
        int whichIsWhich = 0;
        { // warn
            whichIsWhich = 2;
        }
    }
}
