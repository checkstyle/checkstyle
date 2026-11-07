/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfParentheses {

    private Object obj;

    public void testParenthesizedVariables(Object obj, Object other) {
        // violation below 'Unnecessary nullity check'
        if ((obj) != null && obj instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if ((obj) != null && (obj) instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if (obj != null && (obj) instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if (((obj)) != null && obj instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if (null != (obj) && obj instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if ((null) != (obj) && obj instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if ((obj) != (null) && obj instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if (((obj)) != null && ((obj)) instanceof String) {
        }

        // violation below 'Unnecessary nullity check'
        if (obj instanceof String && (obj) != null) {
        }

        // violation below 'Unnecessary nullity check'
        if ((obj) instanceof String && (obj) != null) {
        }

        if ((obj) != null) {
        }

        if ((obj) instanceof String) {
        }

        if ((other) != null && obj instanceof String) {
        }

        if ((obj) != null || obj instanceof String) {
        }

        if ((obj) == null && obj instanceof String) {
        }

        if ((foo(obj)) != null && obj instanceof String) {
        }

        if ((this.obj) != null && obj instanceof String) {
        }
    }

    private Object foo(Object param) {
        return param;
    }
}
