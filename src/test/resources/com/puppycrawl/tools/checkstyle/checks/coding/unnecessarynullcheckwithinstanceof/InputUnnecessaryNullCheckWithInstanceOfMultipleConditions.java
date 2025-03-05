/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfMultipleConditions {

    public void multipleConditions(Object obj, String str) {
        if (obj != null && obj instanceof String && !str.isEmpty()) { // violation, 'Unnecessary nullity check'
            String s = (String) obj;
        }

        if (!str.isEmpty() && obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
            String s = (String) obj;
        }

        if (obj != null && obj instanceof String && str != null && !str.isEmpty()) { // violation, 'Unnecessary nullity check'
            String s = (String) obj;
        }
    }

    public void multipleInstanceOfChecks(Object obj1, Object obj2) {
        // 2 violations 3 lines below:
        //                            'Unnecessary nullity check'
        //                            'Unnecessary nullity check'
        if (obj1 != null && obj1 instanceof String && obj2 != null && obj2 instanceof Integer) {
            String s = (String) obj1;
            Integer i = (Integer) obj2;
        }
    }
}