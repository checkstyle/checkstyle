/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/** Interface for testing. */
interface Named {
    /** Gets the name. */
    String name();
}

/**
 * Record implementing interface - tests that record accessors are NOT skipped
 * even with javaFiveCompatibility=true when the record implements an interface.
 */
public record InputMissingOverrideRecordImplementsJava5(String name) implements Named {
    /**
     * {@inheritDoc}
     */
    public String name() {
    // violation above, 'Must include @java.lang.Override annotation when'
    // '@inheritDoc' Javadoc tag exists.'
        return name;
    }

}
