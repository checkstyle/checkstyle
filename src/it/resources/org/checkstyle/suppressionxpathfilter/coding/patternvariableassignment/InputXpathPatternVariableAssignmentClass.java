// Java16
package org.checkstyle.suppressionxpathfilter.coding.patternvariableassignment;

public class InputXpathPatternVariableAssignmentClass {
    public void foo() {
        AnonymousClass annClass = new AnonymousClass() {
            @Override
            public void test(Object obj) {
                if (obj instanceof Integer x) {
                    x = 3; // warn
                }
            }
        };
    }
}

class AnonymousClass {
    public void test(Object obj) {
    }
}
