package org.checkstyle.suppressionxpathfilter.coding.innerassignment;

public class InputXpathInnerAssignmentMethodCall {
    public void testMethod() {
        int value;
        String result = String.valueOf(value = 42); // warn
    }
}
