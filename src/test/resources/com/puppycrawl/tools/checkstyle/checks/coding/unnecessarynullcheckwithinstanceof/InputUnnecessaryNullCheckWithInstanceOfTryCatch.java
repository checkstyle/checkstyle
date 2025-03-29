/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfTryCatch {
    public void basicTryCatch(Object obj) {
        try {
            if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
                String str = (String) obj;
            }
        } catch (Exception e) {}
    }
    public void catchBlockCheck(Object obj) {

        try {
            String str = (String) obj;
        }
        catch (Exception e) {
            // violation below, 'Unnecessary nullity check'
            if (e != null && e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
        }
    }
    public void finallyBlockCheck(Object obj) {
        try {
            String str = (String) obj;
        } finally {
            // violation below, 'Unnecessary nullity check'
            if (obj != null && obj instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) obj).close();
                } catch (Exception ignored) {

                }
            }
        }
    }
}
