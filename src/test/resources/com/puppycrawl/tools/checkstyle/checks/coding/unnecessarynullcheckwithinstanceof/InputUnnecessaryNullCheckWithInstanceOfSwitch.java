/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfSwitch {
    public void basicSwitch(Object obj, int type) {
        switch (type) {
            case 1:
                // violation below, 'Unnecessary nullity check'
                if (obj != null && obj instanceof String) {
                    String str = (String) obj;
                }
                break;
            case 2:
                if (obj instanceof Integer) {
                    Integer i = (Integer) obj;
                }
                break;
            default:
                break;
        }
    }
    public void switchWithDefault(Object obj, int type) {
        switch (type) {
            case 1:
                if (obj instanceof String) {
                    String str = (String) obj;
                }
                break;
            default:
                // violation below, 'Unnecessary nullity check'
                if (obj != null && obj instanceof Integer) {
                    Integer i = (Integer) obj;
                }
                break;
        }
    }
}
