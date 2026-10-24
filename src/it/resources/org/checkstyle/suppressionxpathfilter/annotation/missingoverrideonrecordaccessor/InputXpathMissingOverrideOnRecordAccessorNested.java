/*
MissingOverrideOnRecordAccessor


*/

package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

public record InputXpathMissingOverrideOnRecordAccessorNested(String outer) {

    @Override
    public String outer() {
        return outer;
    }

    public record Inner(String value) {

// violation below 'Record component accessor method must include @java.lang.Override annotation.'
        public String value() {
            return value.trim();
        }
    }
}
