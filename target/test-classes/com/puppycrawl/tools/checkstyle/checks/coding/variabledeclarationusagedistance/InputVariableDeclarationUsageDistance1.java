/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistance1 {
    private void foo() {
        int i = 0;
        // violation above 'Distance between variable .* and its first usage is 2, but allowed 1'
        String[][] x = { {"foo"} };
        for (int first = 0; first < 5; first++) {}
        int j = 0;
        while (j == 1) {}
        do {} while (i == 1);
    }
}
