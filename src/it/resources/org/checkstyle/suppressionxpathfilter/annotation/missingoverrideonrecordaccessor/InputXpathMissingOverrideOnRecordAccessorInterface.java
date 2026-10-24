/*
MissingOverrideOnRecordAccessor


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

interface Named {
    String name();
}

public record InputXpathMissingOverrideOnRecordAccessorInterface(String name) implements Named {

// violation below 'Record component accessor method must include @java.lang.Override annotation.'
    public String name() {
        return name.toUpperCase();
    }
}
