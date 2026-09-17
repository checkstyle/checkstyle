/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = InputUnusedPrivateFieldAnnotationClassLevel.Getter
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

@interface Getter {
}

@Getter
public class InputUnusedPrivateFieldAnnotationClassLevel {

    private String host; // ok, on class there is annotation

    private int unused; // ok, on class there is annotation

    private static final long serialVersionUID =  1434589190483306227L;

}
