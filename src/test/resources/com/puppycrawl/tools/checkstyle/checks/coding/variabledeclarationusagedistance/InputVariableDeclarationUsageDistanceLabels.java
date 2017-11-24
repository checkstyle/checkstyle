package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLabels {
    public void method() {
        boolean eol = false;

        nothing();
        nothing();
        nothing();
        nothing();
        myLoop:
        for (int i = 0; i < 5; i++) {
            if (i == 5) {
                eol = true;
                break myLoop;
            }
        }
    }

    public void nothing() {
    }
}
