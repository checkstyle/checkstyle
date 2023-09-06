package org.checkstyle.suppressionxpathfilter.equalsavoidnull;

public class SuppressionXpathRegressionEqualsAvoidNullIgnoreCase {
    public void test() {
        String nullString = null;
        nullString.equalsIgnoreCase("Another string"); //warn
    }
}
