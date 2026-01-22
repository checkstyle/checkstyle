/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Record with accessor missing @Override.
 */
public record InputMissingOverrideOnRecordAccessorViolation(String name, int age) {

    public String name() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }

}
