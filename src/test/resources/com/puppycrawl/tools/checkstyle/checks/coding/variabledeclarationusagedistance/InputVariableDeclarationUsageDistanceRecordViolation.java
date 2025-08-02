/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceRecordViolation {

    public void method() {
        int a = 5;

        record MyRecord(int x) {
            public int get() {
                return x;
            }
        }

        System.out.println(a);
    }
}
