/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Generic record.
 */
public record InputMissingOverrideOnRecordAccessorGeneric<T>(T value) {
// violation below, 'Record component accessor method must include @java.lang.Override annotation.'
    public T value() {
        return value;
    }
}
