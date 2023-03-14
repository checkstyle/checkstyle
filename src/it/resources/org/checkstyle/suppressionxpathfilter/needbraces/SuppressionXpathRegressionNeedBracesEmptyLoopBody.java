package org.checkstyle.suppressionxpathfilter.needbraces;

public class SuppressionXpathRegressionNeedBracesEmptyLoopBody {
    private int incrementValue() {
        return 1;
    }

    public void test() {
        while(incrementValue() < 5);; // warn
    }
}
