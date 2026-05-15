/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateFieldAnonymousClass {
    private Runnable runnable = new Runnable() {
        private int unused; // violation 'Unused private field'

        @Override
        public void run() {
        }
    };

    public void execute() {
        runnable.run();
    }

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

}
