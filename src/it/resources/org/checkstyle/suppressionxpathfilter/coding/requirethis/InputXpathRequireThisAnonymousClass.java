/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisAnonymousClass {
    private Runnable runnable = new Runnable() {
        private int age = 23;

        @Override
        public void run() {
            age = 24; // violation 'Reference to instance variable 'age' needs "this.".'
        }
    };
}
