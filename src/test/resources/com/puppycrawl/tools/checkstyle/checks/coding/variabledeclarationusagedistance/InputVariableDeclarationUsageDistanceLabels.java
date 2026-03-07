/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreFinal = (default)true
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false



*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLabels {
    public void method() {
        boolean eol = false; // violation 'Distance .* is 5.'

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
