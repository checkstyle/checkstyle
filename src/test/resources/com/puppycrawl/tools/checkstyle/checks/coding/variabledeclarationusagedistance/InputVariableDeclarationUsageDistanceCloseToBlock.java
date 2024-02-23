/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = false
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceCloseToBlock {
    void method() {
        int a = 3; // violation 'Distance between .* declaration and its first usage is 13.'
        int b = 2; // violation 'Distance between .* declaration and its first usage is 13.'

        run();
        run();
        run();
        run();
        run();
        run();
        run();
        run();
        run();
        run();
        run();
        run();

        for (int i = 0; i < 10; i++) {
            a = a + b;
        }
    }

    public void run() {};
}
