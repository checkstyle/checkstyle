/*
PatternVariableAssignment


*/

// Java16
package org.checkstyle.suppressionxpathfilter.coding.patternvariableassignment;

public class InputXpathPatternVariableAssignmentClass {
    public void foo() {
        AnonymousClass annClass = new AnonymousClass() {
            @Override
            public void test(Object obj) {
                if (obj instanceof Integer x) {
                    // violation below 'Assignment of pattern variable 'x' is not allowed.'
                    x = 3;
                }
            }
        };
    }
}

class AnonymousClass {
    public void test(Object obj) {
    }
}
