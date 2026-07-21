/*
UnusedPrivateField
ignoreAnnotationCanonicalNames= Getter,InputUnusedPrivateFieldAnnotationMultiple.Setter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationMultiple {

    @interface Getter {}
    @interface Setter {}

    @Getter
    private String firstName;

    @Setter
    private String lastName;

    @Getter
    @Setter
    private int age;

    private int unused; // violation, unused private field
}
