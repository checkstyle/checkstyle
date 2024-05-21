//non-compiled with javac: Compilable with Java17

package org.checkstyle.suppressionxpathfilter.covariantequals;

public record InputXpathCovariantEqualsInRecord() {

    public boolean equals(String str) { // warn
        return str.equals(this);
    }

}
