package org.checkstyle.suppressionxpathfilter.variabledeclarationusagedistance;

public class SuppressionXpathRegressionVariableDeclarationUsageDistance1 {
    private int test1;

    public void test(int test1) {
        int temp = -1; // warn
        this.test1 = test1;
        temp = test1; // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
    }
}
