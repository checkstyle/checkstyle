/*
CovariantEquals


*/

package org.checkstyle.suppressionxpathfilter.coding.covariantequals;

public enum InputXpathCovariantEqualsInEnum {

    EQUALS;

    public boolean equals(InputXpathCovariantEqualsInEnum i) {
    // violation above 'covariant equals without overriding equals(java.lang.Object).'
        return false;
    }

}
