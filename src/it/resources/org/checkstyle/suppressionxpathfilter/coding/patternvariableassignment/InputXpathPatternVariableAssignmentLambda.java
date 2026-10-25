/*
PatternVariableAssignment


*/

// Java16
package org.checkstyle.suppressionxpathfilter.coding.patternvariableassignment;

import java.util.List;

public class InputXpathPatternVariableAssignmentLambda {
    public void foo() {
        List<Object> items = List.of("Hello", 123, 45.67, "World");

        items.forEach(item -> {
            if (item instanceof Integer x) {
                // violation below 'Assignment of pattern variable 'x' is not allowed.'
                x = 3;
            }
        });
    }
}
