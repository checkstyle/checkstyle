package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisAnonymousClass {
    private Runnable runnable = new Runnable() {
        private int age = 23;

        @Override
        public void run() {
            age = 24; //warn
        }
    };
}
