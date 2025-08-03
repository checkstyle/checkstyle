package org.checkstyle.checks.suppressionxpathfilter.covariantequals;

public enum InputXpathCovariantEqualsInEnum {

    EQUALS;

    public boolean equals(InputXpathCovariantEqualsInEnum i) { // warn
        return false;
    }

}
