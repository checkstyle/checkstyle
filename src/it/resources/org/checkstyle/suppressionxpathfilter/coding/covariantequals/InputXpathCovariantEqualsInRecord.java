// Java17

package org.checkstyle.suppressionxpathfilter.coding.covariantequals;

public record InputXpathCovariantEqualsInRecord() {

    public boolean equals(String str) { // warn
        return str.equals(this);
    }

}
