/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationIgnored.Inject

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationIgnored {

    @interface Inject {}

    @Inject
    private Object service; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'

    interface Processor {
        void execute();
    }

    enum Status {
        ACTIVE, INACTIVE
    }
}
