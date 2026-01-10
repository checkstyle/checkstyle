/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

interface Named {
    String name();
    int age();
}

/**
 * Record implementing interface - accessor should still be flagged.
 */
public record InputMissingOverrideOnRecordAccessorWithImplements(String name) implements Named {

    public String name() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }

    public int age() {
        return 12;
    }
}
