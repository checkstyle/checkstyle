/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationNotIgnored {

    @interface Inject {}

    @Inject
    private Object service; // violation 'Unused private field'

    private int unused; // violation 'Unused private field'
}
