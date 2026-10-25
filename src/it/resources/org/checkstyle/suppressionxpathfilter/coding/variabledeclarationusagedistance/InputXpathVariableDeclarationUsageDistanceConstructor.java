/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreFinal = false
ignoreVariablePattern = (default)
validateBetweenScopes = true


*/

package org.checkstyle.suppressionxpathfilter.coding.variabledeclarationusagedistance;

public class InputXpathVariableDeclarationUsageDistanceConstructor {
    private int field;

    InputXpathVariableDeclarationUsageDistanceConstructor(int val) {
        // violation below 'Distance .* is 2, but allowed 1.'
        int temp = -1;
        this.field = val;
        temp = val;
    }
}
