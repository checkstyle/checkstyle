package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableEnhancedForLoopVariable {

    public void method2()
    {
        final String[] lines = {"line1", "line2", "line3"};
        for (String line: lines) {
            line = line.trim();
        }
    }
}
