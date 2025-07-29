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
    public int evaluateObjects(Object a, Object b) {
        return a != null && a instanceof Integer // violation, 'Unnecessary nullity check'
                ? (b != null && b instanceof Double ? 100 : -100) : 0;
                // violation above, 'Unnecessary nullity check'
    }

    public Object chainedTernary(Object obj) {
        // violation below, 'Unnecessary nullity check'
        return obj != null && obj instanceof Integer ? (Integer) obj * 2
                : obj != null && obj instanceof Double ? (Double) obj / 2
                : "Unknown";
                // violation 2 lines above 'Unnecessary nullity check'
    }

    public boolean complexLogical(Object a, Object b, boolean flag) {
        // 2 violations 3 lines below:
        //                        'Unnecessary nullity check'
        //                        'Unnecessary nullity check'
        return flag && a != null && a instanceof String && (b != null && b instanceof Number);
    }

    public int lambdaStyleCheck() {
        java.util.function.Function<Object, Integer> func = (obj) ->
                // violation below, 'Unnecessary nullity check'
                obj != null && obj instanceof String ? ((String) obj).length() : -1;
        return func.apply("Hello");
    }
}
