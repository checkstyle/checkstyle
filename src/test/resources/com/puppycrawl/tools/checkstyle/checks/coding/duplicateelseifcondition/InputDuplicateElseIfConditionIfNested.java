/*
DuplicateElseIfCondition


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateelseifcondition;

public class InputDuplicateElseIfConditionIfNested {
    void nestedInIfBody(int x, int y) {
        if (x > 0) {
            if (y > 0) {
            } else if (y > 0) {
                // violation above 'Duplicate condition, identical to the condition at line '12'.'
            }
        } else if (x < 0) {
        }
    }

    void nestedInElseIfBody(int x, int y) {
        if (x > 0) {
        } else if (x < 0) {
            if (y == 1) {
            } else if (y == 2) {
            } else if (y == 1) {
                // violation above 'Duplicate condition, identical to the condition at line '23'.'
            }
        }
    }

    void nestedInElseBody(int x, int y) {
        if (x > 0) {
        } else {
            if (y > 5) {
            } else if (y > 5) {
                // violation above 'Duplicate condition, identical to the condition at line '34'.'
            }
        }
    }

    void nestedMatchesOuterCondition(int x) {
        if (x > 0) {
            if (x > 0) {
            }
        } else if (x < 0) {
        }
    }

    void deeplyNested(int a, int b, int c) {
        if (a > 0) {
            if (b > 0) {
                if (c > 0) {
                } else if (c > 0) {
                    // violation above """Duplicate condition,
                    // identical to the condition at line '52'."""
                }
            }
        }
    }

    void outerHasDuplicateIndependentOfInner(int x, int y) {
        if (x > 0) {
            if (y > 0) {
            }
        } else if (x < 0) {
        } else if (x > 0) {
            // violation above 'Duplicate condition, identical to the condition at line '62'.'
        }
    }
}
