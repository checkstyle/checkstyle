/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceDefault2 {

    void method() {
        int VARIABLE_DEF = 12; // ok
        method();
        method();
        method();
        method();
        for (int i=0; i< 123; i++);
    }
}
