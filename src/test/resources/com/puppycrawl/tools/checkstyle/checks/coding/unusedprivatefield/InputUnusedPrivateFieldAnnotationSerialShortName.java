/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateFieldAnnotationSerialShortName {

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

    private int unused; // violation 'Unused private field'
}
