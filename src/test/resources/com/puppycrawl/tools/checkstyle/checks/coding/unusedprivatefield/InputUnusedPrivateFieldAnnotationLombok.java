/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationLombok.Getter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationLombok {

    @interface Getter {}

    @Getter
    private String name; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'
}
