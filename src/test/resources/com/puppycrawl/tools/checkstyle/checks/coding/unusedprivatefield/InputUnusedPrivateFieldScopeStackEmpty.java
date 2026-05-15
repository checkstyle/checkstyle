/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldScopeStackEmpty {

    private int used; // ok, used below

    private final Object obj = new Object() { // violation, 'Unused private field'
        int localField = used; // violation, 'Unused private field'
    };

    static class Nested {

        private int value; // violation, 'Unused private field'

        private final Runnable r = new Runnable() { // violation, 'Unused private field'
            @Override
            public void run() {
                // no use of value
            }
        };
    }
}
