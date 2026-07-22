/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationNotIgnored {

    @interface Inject {}

    @Inject
    private Object service; // violation, unused private field

    private int unused; // violation, unused private field
}
