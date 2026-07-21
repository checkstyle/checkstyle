/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestSingleLineCaseDefault3 {

    private void testElse(int k) {
        if (k == 4) System.identityHashCode("yes");
        else System.identityHashCode("no");
        for (;;);
    }

    private int testMissingWarnings() {
        if (true)
            throw new RuntimeException();
        if (true) {
            return 1;
        } else
            return 2;
    }

    int[] sourceLocators;

    private class StateInfo {
        public boolean isInitial() {
            for (int locator: sourceLocators) if (locator != 0) return false;
            return true;
        }
    }
}
