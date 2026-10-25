/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreFinal = false
ignoreVariablePattern = (default)
validateBetweenScopes = true


*/

package org.checkstyle.suppressionxpathfilter.coding.variabledeclarationusagedistance;


public class InputXpathVariableDeclarationUsageDistanceTwo {
    public void testMethod2() {
        // violation below 'Distance .* is 2, but allowed 1.'
        int count;
        int a = 3;
        int b = 2;
        {
            a = a
                    + b
                    - 5
                    + 2
                    * a;
            count = b; // DECLARATION OF VARIABLE 'count' SHOULD BE HERE (distance = 2)
        }
    }
}
