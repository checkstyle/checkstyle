package org.checkstyle.suppressionxpathfilter.innerassignment;

public class SuppressionXpathRegressionInnerAssignment2 {
    public void testMethod() {
        double myDouble;
        double[] doubleArray = new double[] {myDouble = 4.5, 15.5}; // warn
    }
}
