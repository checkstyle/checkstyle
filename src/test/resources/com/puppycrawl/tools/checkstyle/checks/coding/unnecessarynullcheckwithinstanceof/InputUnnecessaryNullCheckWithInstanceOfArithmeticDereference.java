/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfArithmeticDereference {

    void noViolation(Object obj) {
        if (obj != null && obj.hashCode() + 1 > 0 && obj instanceof String) {
            System.out.println(obj);
        }

        if (obj != null && obj.hashCode() - 1 > 0 && obj instanceof String) {
            System.out.println(obj);
        }

        if (obj != null && obj.hashCode() > 0 && obj instanceof String) {
            System.out.println(obj);
        }

        if (obj != null && obj.hashCode() < 5 && obj instanceof String) {
            System.out.println(obj);
        }

        if (obj != null && (obj.hashCode() & 0xFF) == 0
                && obj instanceof String) {
            System.out.println(obj);
        }

        if (obj != null && obj.toString().length() > 0
                && obj instanceof String) {
            System.out.println(obj);
        }
    }

    void violation(Object obj) {
        // violation below 'Unnecessary nullity check with instanceof.*'
        if (obj != null && obj instanceof String) {
            System.out.println(obj);
        }

        // violation below 'Unnecessary nullity check with instanceof.*'
        if (obj != null && (1 + 2 > 0) && obj instanceof String) {
            System.out.println(obj);
        }
    }

    void fieldNamedSameAsVariable(Object obj) {
        // violation below 'Unnecessary nullity check with instanceof.*'
        if (obj != null && this.obj > 0 && obj instanceof String) {
            System.out.println(obj);
        }
    }

    int obj;
}
