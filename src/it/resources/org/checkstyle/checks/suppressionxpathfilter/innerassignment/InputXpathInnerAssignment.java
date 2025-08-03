package org.checkstyle.checks.suppressionxpathfilter.innerassignment;

public class InputXpathInnerAssignment {

    public void testMethod() {
        int a, b;
        a = b = 5; // warn
    }
}
