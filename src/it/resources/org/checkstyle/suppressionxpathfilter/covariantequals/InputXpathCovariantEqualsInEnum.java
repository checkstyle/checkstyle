package org.checkstyle.suppressionxpathfilter.covariantequals;

public enum InputXpathCovariantEqualsInEnum {

    EQUALS;

    public boolean equals(InputXpathCovariantEqualsInEnum i) { // warn
        return false;
    }

}
