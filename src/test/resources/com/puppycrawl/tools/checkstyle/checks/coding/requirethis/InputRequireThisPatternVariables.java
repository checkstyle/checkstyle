/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false
*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

class InputRequireThisPatternVariables {

    private String s = "field";

    void usePatternVariable(Object o) {
        
        if (o instanceof String s) {
            s.length(); 
        }
    }

    void usePatternVariableInCondition(Object o) {
        if (o instanceof String s && s.length() > 0) { 
            s.isEmpty(); 
        }
    }

    void fieldStillRequiresThis() {
        s = "other"; 
    }
}
