/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfVariableAssignment {
    public void variableAssignments(Object obj) {

        // violation below, 'Unnecessary nullity check'
        boolean isValid = obj != null && obj instanceof String;
        if (isValid) {
            String str = (String) obj;
        }
        boolean isString = obj instanceof String;

        // violation below, 'Unnecessary nullity check'
        boolean isValidMultiLine = obj != null
                                && obj instanceof String;
    }
    public void multipleAssignments(Object obj) {
        boolean notNull = obj == null;
        boolean isString = obj instanceof String;
        if (!notNull && isString) {
            String str = (String) obj;
        }
    }
}
