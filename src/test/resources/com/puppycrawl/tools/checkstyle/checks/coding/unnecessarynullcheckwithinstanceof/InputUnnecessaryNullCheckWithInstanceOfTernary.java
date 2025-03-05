/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfTernary {
    
    public String basicTernary(Object obj) {
        return obj != null && obj instanceof String ? ((String) obj) : ""; // violation, 'Unnecessary nullity check'
    }
    
    public String validTernary(Object obj) {
        return obj instanceof String ? ((String) obj) : "";
    }
    
    public String ternaryWithVariable(Object obj) {
        // Using variable in ternary
        boolean check = obj != null && obj instanceof String; // violation, 'Unnecessary nullity check'
        return check ? ((String) obj) : "";
    }
    
    public String simpleTernaryWithExtra(Object obj, boolean flag) {
        return flag && obj != null && obj instanceof String ? ((String) obj) : ""; // violation, 'Unnecessary nullity check'
    }
}