/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Record with one accessor having @Override and another missing it.
 */
public record InputMissingOverrideOnRecordAccessorSomeAnnotated(String name, int age) {

    public String name() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }

    @Override
    public int age() {
        return age;
    }
}
