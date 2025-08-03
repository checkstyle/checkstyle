// Java17

package org.checkstyle.checks.suppressionxpathfilter.covariantequals;

public record InputXpathCovariantEqualsInRecord() {

    public boolean equals(String str) { // warn
        return str.equals(this);
    }

}
