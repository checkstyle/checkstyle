/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationCanonicalNameUsage.Inject
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationCanonicalNameUsage {

    @interface Inject {}

    @InputUnusedPrivateFieldAnnotationCanonicalNameUsage.Inject
    private Object service; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'
}
