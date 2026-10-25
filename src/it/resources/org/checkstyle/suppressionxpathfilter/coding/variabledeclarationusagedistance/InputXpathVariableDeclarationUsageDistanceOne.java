/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreFinal = false
ignoreVariablePattern = (default)
validateBetweenScopes = true


*/

package org.checkstyle.suppressionxpathfilter.coding.variabledeclarationusagedistance;

public class InputXpathVariableDeclarationUsageDistanceOne {
    private int test1;

    public void test(int test1) {
        // violation below 'Distance .* is 2, but allowed 1.'
        int temp = -1;
        this.test1 = test1;
        temp = test1; // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
    }
}
