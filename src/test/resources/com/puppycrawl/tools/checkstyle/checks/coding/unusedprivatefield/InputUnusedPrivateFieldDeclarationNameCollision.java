/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationShortName.MockBean
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldDeclarationNameCollision {

    private int myClass; // violation 'Unused private field'
    class myClass { }

    private int myInterface; // violation 'Unused private field'
    interface myInterface { }

    private int myEnum; // violation 'Unused private field'
    enum myEnum { }

    private int myRecord; // violation 'Unused private field'
    record myRecord() { }

    private int myAnnotation; // violation 'Unused private field'
    @interface myAnnotation { }
}
