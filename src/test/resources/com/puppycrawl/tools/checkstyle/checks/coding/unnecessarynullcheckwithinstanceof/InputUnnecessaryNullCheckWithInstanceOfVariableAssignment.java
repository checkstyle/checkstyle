/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfVariableAssignment {
    
    public void variableAssignments(Object obj) {
        boolean isValid = obj != null && obj instanceof String; // violation, 'Unnecessary nullity check'
        if (isValid) {
            String str = (String) obj;
        }
        
        boolean isString = obj instanceof String;
        
        boolean isValidMultiLine = obj != null   // violation, 'Unnecessary nullity check'
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