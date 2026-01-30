/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfMutationKiller {

    public void testEqualOperatorNotViolation(Object obj) {
        if (obj == null && obj instanceof String) {
            System.out.println("This should NOT be flagged");
        }

        if (obj == null && true && obj instanceof String) {
            System.out.println("This should NOT be flagged either");
        }

        if (obj == null || obj instanceof String) {
            System.out.println("LOR only - no violation");
        }
    }

    public void testMultipleChildrenIteration(Object obj, Object data, String s) {

        // violation below, 'Unnecessary nullity check'
        if (obj != null && data != null && s != null && obj instanceof String) {
            System.out.println("Multiple conditions before instanceof");
        }

        // violation below, 'Unnecessary nullity check'
        if (data != null && obj != null && obj instanceof String && s != null) {
            System.out.println("instanceof in middle");
        }
    }

    public void testDeepNesting(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && ((obj instanceof String))) {
            System.out.println("Deeply parenthesized");
        }

        // violation below, 'Unnecessary nullity check'
        if (obj != null && (true && (obj instanceof String))) {
            System.out.println("Nested LAND with instanceof");
        }
    }

    public void testRecursiveLandProcessing(Object obj, Object data) {
        // violation below, 'Unnecessary nullity check'
        if ((obj != null && obj instanceof String) && data != null) {
            System.out.println("Nested LAND as first child");
        }

        if ((obj == null && obj instanceof String) && data != null) {
            System.out.println("EQUAL should not trigger");
        }
    }

    public void testMinimalCase(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && obj instanceof String) {
            System.out.println("Simple case");
        }
    }

    public void testEqualVariousPositions(Object a, Object b, Object c) {

        if (a == null && a instanceof String && b != null) {
            System.out.println("EQUAL first");
        }

        if (b != null && a == null && a instanceof String) {
            System.out.println("EQUAL in middle");
        }

        if (c instanceof String && c == null) {
            System.out.println("instanceof first, EQUAL after");
        }
    }
}
