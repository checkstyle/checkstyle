/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Record with accessor having @Override.
 */
public record InputMissingOverrideOnRecordAccessorValid(String name) {

    @Override
    public String name() {
        return name.toUpperCase();
    }

}
