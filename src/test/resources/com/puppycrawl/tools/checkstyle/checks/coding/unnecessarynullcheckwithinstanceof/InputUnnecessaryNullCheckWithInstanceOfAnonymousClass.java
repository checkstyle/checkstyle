/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfAnonymousClass {
    
    public void basicAnonymousClass(final Object obj) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
                    String str = (String) obj;
                }
            }
        };
    }
    
    interface Validator {
        boolean validate(Object obj);
    }
    
    public void anonymousClassImplementation() {
        Validator v = new Validator() {
            @Override
            public boolean validate(Object obj) {
                return obj != null && obj instanceof String; // violation, 'Unnecessary nullity check'
            }
        };
    }
}