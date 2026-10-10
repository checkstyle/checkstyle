/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfBooleanCondition {

    public void testBooleanCondition(Boolean a, Boolean b) {
        if (a != null && a && a instanceof Boolean) {
            System.out.println("ok");
        }
        if (a != null && (b || a) && a instanceof Boolean) {
            System.out.println("ok");
        }
        if (a != null && !a && a instanceof Boolean) {
            System.out.println("ok");
        }
    }
}
