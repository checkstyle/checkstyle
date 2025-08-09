/*
VariableDeclarationUsageDistance
allowedDistance = 3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceMultipleDeclarations {
    public void testMultipleVars() {
        int a = 1, b = 2;

        class Dummy {
            void use() {
                System.out.println(a);
            }
        }
        // b is unused — not flagged by this check
    }
}
