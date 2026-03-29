/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfMutationKiller {

    public void testEqualOperatorNotViolation(Object obj) {
        if (obj == null && obj instanceof String) {
            System.out.println("Not flagged");
        }
        if (obj == null && true && obj instanceof String) {
            System.out.println("Not flagged");
        }
        if (obj == null || obj instanceof String) {
            System.out.println("LOR only");
        }
    }

    public void testMultipleChildrenIteration(Object obj, Object data, String s) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && data != null && s != null && obj instanceof String) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (data != null && obj != null && obj instanceof String && s != null) {
            System.out.println("ok");
        }
    }

    public void testDeepNesting(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && ((obj instanceof String))) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (obj != null && (true && (obj instanceof String))) {
            System.out.println("ok");
        }
    }

    public void testRecursiveLandProcessing(Object obj, Object data) {
        // violation below, 'Unnecessary nullity check'
        if ((obj != null && obj instanceof String) && data != null) {
            System.out.println("ok");
        }
        if ((obj == null && obj instanceof String) && data != null) {
            System.out.println("ok");
        }
    }

    public void testMinimalCase(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && obj instanceof String) {
            System.out.println("ok");
        }
    }

    public void testEqualVariousPositions(Object a, Object b, Object c) {
        if (a == null && a instanceof String && b != null) {
            System.out.println("ok");
        }
        if (b != null && a == null && a instanceof String) {
            System.out.println("ok");
        }
        if (c instanceof String && c == null) {
            System.out.println("ok");
        }
    }

    public void testDeepAncestorSearch(Object obj, Object x, Object y, Object z) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && (((((obj instanceof String)))))) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (obj != null && x != null && y != null && z != null && obj instanceof String) {
            System.out.println("ok");
        }
    }

    public void testFindDirectChildMultipleSiblings(Object a, Object b, Object c, Object d) {
        // violation below, 'Unnecessary nullity check'
        if (a != null && (a instanceof String || b != null) && c != null && d != null) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (a != null && ((((b != null && (a instanceof String)))))) {
            System.out.println("ok");
        }
    }

    public void testAncestorTraversalBoundary(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj != null && obj instanceof String && true && true) {
            System.out.println("ok");
        }
    }
}
