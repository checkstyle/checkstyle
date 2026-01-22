/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Outer record with nested record.
 */
public record InputMissingOverrideOnRecordAccessorNested(String outer) {

    @Override
    public String outer() {
        return outer.toUpperCase();
    }

    public record Inner(String inner) {

        public String inner() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
            return inner.toLowerCase();
        }
    }
}
