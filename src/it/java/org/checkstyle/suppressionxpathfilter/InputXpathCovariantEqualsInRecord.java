package org.checkstyle.suppressionxpathfilter;

public record InputXpathCovariantEqualsInRecord() {

    public boolean equals(String str) { // warn
        return str.equals(this);
    }

}
