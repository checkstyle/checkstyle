/*
VariableDeclarationUsageDistance
allowedDistance = 2
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceSwitchRecord {
    public void test() {
        int a = 42;
        record R(int x) {}
        switch (a) {
            case 1 -> System.out.println("one");
            default -> System.out.println(a); // violation 'Distance .* is 3.'
        }
    }
}
