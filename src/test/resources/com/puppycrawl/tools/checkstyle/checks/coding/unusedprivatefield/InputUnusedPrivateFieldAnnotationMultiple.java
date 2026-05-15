/*
UnusedPrivateField
ignoreAnnotationCanonicalNames= Getter,InputUnusedPrivateFieldAnnotationMultiple.Setter
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationMultiple {

    @interface Getter {}
    @interface Setter {}

    @Getter
    private String firstName; // ok, as suppressed by property

    @Setter
    private String lastName; // ok, as suppressed by property

    @Getter
    @Setter
    private int age; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'
}
