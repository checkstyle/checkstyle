package org.checkstyle.suppressionxpathfilter.avoidnestedblocks;

public class SuppressionXpathRegressionAvoidNestedBlocksVariable {

    void varAssign() {
        int whichIsWhich = 0;
        { // warn
            whichIsWhich = 2;
        }
    }
}
