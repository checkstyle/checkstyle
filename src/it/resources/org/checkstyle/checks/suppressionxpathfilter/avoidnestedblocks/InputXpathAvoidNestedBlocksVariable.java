package org.checkstyle.checks.suppressionxpathfilter.avoidnestedblocks;

public class InputXpathAvoidNestedBlocksVariable {

    void varAssign() {
        int whichIsWhich = 0;
        { // warn
            whichIsWhich = 2;
        }
    }
}
