package org.checkstyle.suppressionxpathfilter.variabledeclarationusagedistance;


public class SuppressionXpathRegressionVariableDeclarationUsageDistance2 {
    public void testMethod2() {
        int count; // warn
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
