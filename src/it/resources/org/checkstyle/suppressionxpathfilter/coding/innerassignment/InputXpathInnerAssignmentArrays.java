package org.checkstyle.suppressionxpathfilter.coding.innerassignment;

public class InputXpathInnerAssignmentArrays {
    public void testMethod() {
        double myDouble;
        double[] doubleArray = new double[] {myDouble = 4.5, 15.5}; // warn
    }
}
