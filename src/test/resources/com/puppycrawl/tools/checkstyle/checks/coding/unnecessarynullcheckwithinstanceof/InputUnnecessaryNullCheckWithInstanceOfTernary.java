/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfTernary {
    public String basicTernary(Object obj) {
        // violation below, 'Unnecessary nullity check'
        return obj != null && obj instanceof String ? ((String) obj) : "";
    }
    public String validTernary(Object obj) {
        return obj instanceof String ? ((String) obj) : "";
    }
    public String ternaryWithVariable(Object obj) {
        // violation below, 'Unnecessary nullity check'
        boolean check = obj != null && obj instanceof String;
        return check ? ((String) obj) : "";
    }
    public String simpleTernaryWithExtra(Object obj, boolean flag) {
        // violation below, 'Unnecessary nullity check'
        return flag && obj != null && obj instanceof String ? ((String) obj) : "";
    }
}
