/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Record with method that has parameters.
 */
public record InputMissingOverrideOnRecordAccessorWithParams(String name) {

    public String name(String prefix) {
        return prefix + name;
    }
}
