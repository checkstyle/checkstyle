/*
MissingOverrideOnRecordAccessor


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

public record InputXpathMissingOverrideOnRecordAccessorSimple(String name) {

// violation below 'Record component accessor method must include @java.lang.Override annotation.'
    public String name() {
        return name.toUpperCase();
    }

}
