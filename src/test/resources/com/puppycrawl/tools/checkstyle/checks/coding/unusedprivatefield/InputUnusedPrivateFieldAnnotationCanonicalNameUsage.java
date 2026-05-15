/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationCanonicalNameUsage.Inject

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationCanonicalNameUsage {

    @interface Inject {}

    @InputUnusedPrivateFieldAnnotationCanonicalNameUsage.Inject
    private Object service;

    private int unused; // violation, 'Unused private field'
}
