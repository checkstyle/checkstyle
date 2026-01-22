package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

public record InputXpathMissingOverrideOnRecordAccessorNested(String outer) {

    @Override
    public String outer() {
        return outer;
    }

    public record Inner(String value) {

        public String value() { // warn 'method must include @java.lang.Override annotation.'
            return value.trim();
        }
    }
}
