/*
VariableDeclarationUsageDistance
allowedDistance = 3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLambda {
    public void testLambda() {
        int x = 10;
        Runnable r = () -> {
            System.out.println(x);
        };
    }
}
