package org.checkstyle.suppressionxpathfilter.equalsavoidnull;

public class SuppressionXpathRegressionEqualsAvoidNull {
    public void test() {
        String nullString = null;
        nullString.equals("Another string"); //warn
    }
}
