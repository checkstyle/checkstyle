/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

import java.util.Objects;

public class InputUnnecessaryNullCheckWithInstanceOfComplexCases {

    public void testOrWithInstanceOf(Object data, String s, String b) {
        // violation below, 'Unnecessary nullity check'
        if (data != null && (!Objects.equals(s, b) || data instanceof String)) {
            System.out.println("data is a String");
        }

        // violation below, 'Unnecessary nullity check'
        boolean isValid = b != null && (s != null || b instanceof String);

        // violation below, 'Unnecessary nullity check'
        if (data != null && (true || data instanceof String)) {
            System.out.println("condition");
        }

        // violation below, 'Unnecessary nullity check'
        if (data != null && (s.isEmpty() || b.isEmpty() || data instanceof String)) {
            System.out.println("multiple or conditions");
        }
    }

    public void testNestedOrWithInstanceOf(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && ((true) || obj instanceof String)) {
            System.out.println("nested parens");
        }

        // violation below, 'Unnecessary nullity check'
        if (obj != null && (false || (true || obj instanceof String))) {
            System.out.println("deeply nested");
        }
    }

    public void testNoViolation(Object obj1, Object obj2) {
        // no violation - different variables
        if (obj1 != null && (true || obj2 instanceof String)) {
            System.out.println("different vars");
        }

        if (obj1 != null || obj1 instanceof String) {
            System.out.println("only or");
        }

        if (obj1 != null && (obj1.toString().isEmpty() || obj1 instanceof String)) {
            System.out.println("dereferenced");
        }
    }

    public void testWhileAndFor(Object data) {
        // violation below, 'Unnecessary nullity check'
        while (data != null && (true || data instanceof String)) {
            break;
        }

        // violation below, 'Unnecessary nullity check'
        for (int i = 0; data != null && (true || data instanceof String); i++) {
            break;
        }
    }
}
