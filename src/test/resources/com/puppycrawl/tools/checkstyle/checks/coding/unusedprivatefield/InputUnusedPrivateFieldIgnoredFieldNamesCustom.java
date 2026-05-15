/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = serialVersionUID, LOG

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldIgnoredFieldNamesCustom {

    private static final long serialVersionUID = 1L; // ok, as suppressed by property

    private static final long LOG = 1L; // ok, as suppressed by property

    private int unused; // violation 'Unused private field'

    private int used; // ok, private field is used

    public int getUsed() {
        return used;
    }
}
