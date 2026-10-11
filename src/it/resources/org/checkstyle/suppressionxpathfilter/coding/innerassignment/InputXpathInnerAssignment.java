package org.checkstyle.suppressionxpathfilter.coding.innerassignment;

public class InputXpathInnerAssignment {

    public void testMethod() {
        int a, b;
        a = b = 5; // warn
    }
}
