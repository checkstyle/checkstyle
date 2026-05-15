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
    private int age; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'
}
