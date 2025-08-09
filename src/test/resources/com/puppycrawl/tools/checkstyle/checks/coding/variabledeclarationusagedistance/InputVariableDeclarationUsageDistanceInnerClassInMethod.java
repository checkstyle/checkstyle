/*
VariableDeclarationUsageDistance
allowedDistance = 3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceInnerClassInMethod {

    public void exampleMethod() {
        int value = 10;

        class LocalClass {
            void inner() {
                System.out.println("Inside local class");
            }
        }

        if (value > 5) {
            System.out.println(value); // use of 'value' after inner class
        }
    }
}
