/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationClassLevel.Getter

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

@interface Getter {
}

@Getter
public class InputUnusedPrivateFieldAnnotationClassLevel {

    private String host; // ok, on class there is annotation

    private int unused; // ok, on class there is annotation
}
