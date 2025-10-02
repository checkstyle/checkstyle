/*
VariableDeclarationUsageDistance
allowedDistance = 3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceDeepNested {
    public void testNestedScopes() {
        int z = 1;
        class Local {}
        interface I {}
        enum E { A, B }
        System.out.println(z);
    }
}
