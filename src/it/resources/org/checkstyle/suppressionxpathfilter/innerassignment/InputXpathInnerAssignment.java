package org.checkstyle.suppressionxpathfilter.innerassignment;

public class InputXpathInnerAssignment {

    public void testMethod() {
        int a, b;
        a = b = 5; // warn
    }
}
