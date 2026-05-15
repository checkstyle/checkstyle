/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationShortName.MockBean

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnnotationShortName {

    @interface MockBean {}

    @MockBean
    private Object repo;

    private int unused; // violation, unused private field
}
