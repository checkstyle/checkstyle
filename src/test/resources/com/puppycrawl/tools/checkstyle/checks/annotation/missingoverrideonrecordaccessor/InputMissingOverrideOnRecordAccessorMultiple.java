/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Record with multiple components, all missing @Override.
 */
public record InputMissingOverrideOnRecordAccessorMultiple(String name, int age, boolean active) {

    public String name() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }

    public int age() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return age;
    }

    public boolean active() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return active;
    }
}
