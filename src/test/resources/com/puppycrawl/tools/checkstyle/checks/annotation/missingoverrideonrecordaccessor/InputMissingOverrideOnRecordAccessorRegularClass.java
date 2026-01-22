/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Regular class (not a record).
 */
public class InputMissingOverrideOnRecordAccessorRegularClass {
    private String name;

    public String name() {
        return name;
    }
}
