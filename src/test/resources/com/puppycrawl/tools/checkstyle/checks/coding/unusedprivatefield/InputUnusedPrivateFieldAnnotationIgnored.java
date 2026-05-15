/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationIgnored.Inject

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationIgnored {

    @interface Inject {}

    @Inject
    private Object service;

    private int unused; // violation, unused private field

    interface Processor {
        void execute();
    }

    enum Status {
        ACTIVE, INACTIVE
    }
}
