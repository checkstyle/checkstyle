/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLabels {
    public void method() {
        boolean eol = false; // violation 'Distance .* is 5.'

        nothing();
        nothing();
        nothing();
        nothing();
        myLoop: // ok
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
