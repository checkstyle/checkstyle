/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationLombok.Getter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationLombok {

    @interface Getter {}

    @Getter
    private String name;

    private int unused; // violation, unused private field
}
