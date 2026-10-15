/*
EqualsHashCode


*/

package org.checkstyle.suppressionxpathfilter.coding.equalshashcode;

public class InputXpathEqualsHashCodeEqualsOnly {
    public boolean equals(Object obj) { // violation 'without .* of 'hashCode()'.'
        return false;
    }
}
