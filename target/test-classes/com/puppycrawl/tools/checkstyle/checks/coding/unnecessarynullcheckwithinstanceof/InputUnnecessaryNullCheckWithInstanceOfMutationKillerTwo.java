/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfMutationKillerTwo {

    public void testIsAncestorOfReturnsFalse(Object a, Object b, Object c) {
        // violation below, 'Unnecessary nullity check'
        if (b != null && c != null && a != null && a instanceof String) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (b != null && c != null && c.hashCode() > 0 && a != null && a instanceof String) {
            System.out.println("ok");
        }
    }

    public void testDirectChildIterationExhaustsNonMatching(Object x, Object y, Object z) {
        // violation below, 'Unnecessary nullity check'
        if (y != null && z != null && x != null && x instanceof Integer) {
            System.out.println("ok");
        }
        // violation below, 'Unnecessary nullity check'
        if (x != null && (y != null && z != null && x instanceof Number)) {
            System.out.println("ok");
        }
    }
}
