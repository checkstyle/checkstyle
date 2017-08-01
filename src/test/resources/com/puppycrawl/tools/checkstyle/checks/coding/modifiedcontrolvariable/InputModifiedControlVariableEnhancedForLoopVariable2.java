package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableEnhancedForLoopVariable2 {
    void m(int[] a) {
        for (int i = 0, j = 1; ; i++, j++) {
            for (int k : a) {
            }
        }
    }

    void m2(int[] a) {
        for (int i = 0, j = 1; ; i++, j++) {
            for (int k : a) {
                i++;
            }
        }
    }
}
