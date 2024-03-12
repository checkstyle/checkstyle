package org.checkstyle.suppressionxpathfilter.innerassignment;

public class SuppressionXpathRegressionInnerAssignment1 {

    public void testMethod() {
        int a, b;
        a = b = 5; // warn
    }
}
