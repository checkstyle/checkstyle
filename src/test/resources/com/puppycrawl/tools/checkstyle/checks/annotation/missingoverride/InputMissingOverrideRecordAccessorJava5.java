/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/** some javadoc. */
public record InputMissingOverrideRecordAccessorJava5(String name) {
    /**
     * {@inheritDoc}
     */
    public String name() {
    // violation above, 'Must include @java.lang.Override annotation when '@inheritDoc'
        return name;
    }
}
