package org.checkstyle.suppressionxpathfilter.unnecessarynullcheckwithinstanceof;

public class InputXpathUnnecessaryNullCheckWithInstanceOfAnonymous {
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Object obj = "test";
            if (obj != null && obj instanceof String) { // warn
                String s = (String) obj;
            }
        }
    };
}
