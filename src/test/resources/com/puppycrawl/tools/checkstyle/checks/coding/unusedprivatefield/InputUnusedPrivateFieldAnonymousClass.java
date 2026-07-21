/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnonymousClass {

    private final Runnable r = new Runnable() { // violation, unused private field
        @Override
        public void run() {
            // no code
        }
    };
}
