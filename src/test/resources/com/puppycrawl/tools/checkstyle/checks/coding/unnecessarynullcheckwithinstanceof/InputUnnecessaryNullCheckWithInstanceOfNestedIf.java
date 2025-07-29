/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfNestedIf {
    public void nestedIfStatements(Object obj) {
        if (obj.hashCode() > 0) {
            if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
                String str = (String) obj;
            }
        }
        if (true) {
            if (obj.hashCode() > 0) {
                // violation below, 'Unnecessary nullity check'
                if (obj != null && obj instanceof String) {
                    String str = (String) obj;
                }
            }
        }
    }
    public void nestedIfWithMultipleObjects(Object obj1, Object obj2) {
        if (obj1 != null) {
            if (obj2 != null && obj2 instanceof String) { // violation, 'Unnecessary nullity check'
                String str = (String) obj2;
            }
        }
    }
}
