/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationSecondOfMultiple.Setter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationSecondOfMultiple {

    @interface Getter {}
    @interface Setter {}

    @Getter
    @Setter
    private int age;

    private int unused; // violation, unused private field
}
