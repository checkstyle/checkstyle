/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = (default)true


*/


package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLocalTypeCheck {

    public void testWithAnonymousBlock() {
        int b = 2; // violation 'Distance .* is 2.'

        {
            System.out.println("inside block");
        }

        System.out.println(b); // usage after anonymous block
    }

}
