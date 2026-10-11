/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceGeneralNew1 {

    public boolean equals1(Object obj) {
        int i = 1;
        int a = 5;
        int b = 6;
        switch (i) {
            case 1:
                int count; // violation 'Distance between .* declaration and its first usage is 3.'
                a = a + b;
                b = a + a;
                count = b;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
        }
        return false;
    }
}

