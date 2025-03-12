/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfSwitch {
    
    public void basicSwitch(Object obj, int type) {
        switch (type) {
            case 1:
                if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
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
                if (obj != null && obj instanceof Integer) { // violation, 'Unnecessary nullity check'
                    Integer i = (Integer) obj;
                }
                break;
        }
    }
}