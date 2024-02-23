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

    void testMethod() {
        int wh, k;

        for (int i = 0;i < 10;i++) {
            if (i > 0) {
                k = 6;    // distance = 1
            } else {   // distance = 1
                wh = 6;
            }
        }
    }

    public void run() {};
}
