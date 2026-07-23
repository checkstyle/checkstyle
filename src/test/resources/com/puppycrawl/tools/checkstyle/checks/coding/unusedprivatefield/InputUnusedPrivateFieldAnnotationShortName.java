/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationShortName.MockBean

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationShortName {

    @interface MockBean {}

    @MockBean
    private Object repo; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'
}
