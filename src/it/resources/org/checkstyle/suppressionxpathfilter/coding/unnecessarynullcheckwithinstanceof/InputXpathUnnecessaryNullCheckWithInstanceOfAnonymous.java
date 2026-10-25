/*
UnnecessaryNullCheckWithInstanceOf


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarynullcheckwithinstanceof;

public class InputXpathUnnecessaryNullCheckWithInstanceOfAnonymous {
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Object obj = "test";
            if (obj != null && obj instanceof String) {
            // violation above 'Unnecessary nullity check with instanceof operator'
                String s = (String) obj;
            }
        }
    };
}
