/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

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
    private int anonUsed;

    Runnable r = new Runnable() {
        public void run() {
            System.out.println(anonUsed);
        }
    };

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

}
