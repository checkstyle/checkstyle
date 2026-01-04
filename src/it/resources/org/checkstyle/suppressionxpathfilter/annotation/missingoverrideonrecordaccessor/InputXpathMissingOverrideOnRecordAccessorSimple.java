package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

public record InputXpathMissingOverrideOnRecordAccessorSimple(String name) {

    public String name() { // warn 'method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }

}
