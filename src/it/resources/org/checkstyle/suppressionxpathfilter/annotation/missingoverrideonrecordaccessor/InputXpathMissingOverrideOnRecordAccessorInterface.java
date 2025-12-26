package org.checkstyle.suppressionxpathfilter.annotation.missingoverrideonrecordaccessor;

interface Named {
    String name();
}

public record InputXpathMissingOverrideOnRecordAccessorInterface(String name) implements Named {

    public String name() { // warn 'method must include @java.lang.Override annotation.'
        return name.toUpperCase();
    }
}
