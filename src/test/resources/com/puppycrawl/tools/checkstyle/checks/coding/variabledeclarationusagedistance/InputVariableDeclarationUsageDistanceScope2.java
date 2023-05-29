/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceScope2 {

    void f() {
        int i; // violation 'Distance between variable .* and its first usage is 5, but allowed 1'
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        System.lineSeparator();
        for (i = 0; i < 10; i++) {
            i = i + i;
        }
        int a = i;
    }
}
