/*
VariableDeclarationUsageDistance
allowedDistance = 3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceAnonInner {
    public void testAnonymousInner() {
        int y = 5;
        Object o = new Object() {
            {
                System.out.println(y);
            }
        };
    }
}
