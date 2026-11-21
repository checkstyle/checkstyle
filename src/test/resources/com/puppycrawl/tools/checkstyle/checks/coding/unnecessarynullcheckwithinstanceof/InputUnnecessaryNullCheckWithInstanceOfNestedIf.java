/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfNestedIf {
    public void nestedIfStatements(Object obj) {
        if (obj.hashCode() > 0) {
            if (obj != null && obj instanceof String) { // violation 'Unnecessary nullity check'
                String str = (String) obj;
            }
        }
        if (true) {
            if (obj.hashCode() > 0) {
                // violation below 'Unnecessary nullity check'
                if (obj != null && obj instanceof String) {
                    String str = (String) obj;
                }
            }
        }
    }
    public void nestedIfWithMultipleObjects(Object obj1, Object obj2) {
        if (obj1 != null) {
            if (obj2 != null && obj2 instanceof String) { // violation 'Unnecessary nullity check'
                String str = (String) obj2;
            }
        }
    }
    public void nestedIfForms(Object obj) {
        if (obj != null) { // violation 'Unnecessary nullity check'
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj != null) // violation 'Unnecessary nullity check'
            if (obj instanceof String) {
                String str = (String) obj;
            }
        if (obj instanceof String) {
            if (obj != null) { // violation 'Unnecessary nullity check'
                String str = (String) obj;
            }
        }
        if (obj instanceof String)
            if (obj != null) { // violation 'Unnecessary nullity check'
                String str = (String) obj;
            }
        if ((obj != null)) { // violation 'Unnecessary nullity check'
            if ((obj instanceof String)) {
                String str = (String) obj;
            }
        }
        if (null != obj) { // violation 'Unnecessary nullity check'
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
    }
    public void nestedIfNegativeCases(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        } else {
            System.out.println("null");
        }
        if (obj != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            } else {
                System.out.println("not a String");
            }
        }
        if (obj != null) {
            System.out.println(obj.hashCode());
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj != null || true) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj != null) {
            if (obj instanceof String || true) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String) {
            if (obj != null) {
                String str = (String) obj;
            } else {
                System.out.println("null");
            }
        }
        if (obj instanceof String) {
            if (obj != null) {
                String str = (String) obj;
            }
        } else {
            System.out.println("not a String");
        }
    }
}
