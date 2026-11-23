/*
CovariantEquals


*/

package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

public enum InputCovariantEqualsEnumConstantBody {
    FIRST {
        boolean equals(InputCovariantEqualsEnumConstantBody other) { // violation 'covariant equals'
            return other == this;
        }
    },
    SECOND {
        int size() {
            return 2;
        }
    },
    THIRD
}
