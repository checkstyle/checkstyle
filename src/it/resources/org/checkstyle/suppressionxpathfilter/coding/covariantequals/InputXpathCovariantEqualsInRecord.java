/*
CovariantEquals


*/



package org.checkstyle.suppressionxpathfilter.coding.covariantequals;

public record InputXpathCovariantEqualsInRecord() {

    public boolean equals(String str) {
    // violation above 'covariant equals without overriding equals(java.lang.Object).'
        return str.equals(this);
    }

}
