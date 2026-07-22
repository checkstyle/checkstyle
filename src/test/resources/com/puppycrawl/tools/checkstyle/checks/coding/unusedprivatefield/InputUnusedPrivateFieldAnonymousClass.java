/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnonymousClass {
    private Runnable runnable = new Runnable() {
        private int unused; // violation, 'Unused private field'

        @Override
        public void run() {
        }
    };

    public void execute() {
        runnable.run();
    }
}
