/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationClassLevel.Getter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

@interface Getter {
}

@Getter
public class InputUnusedPrivateFieldAnnotationClassLevel {

    private String host;

    private int unused;
}
