/*
EqualsHashCode


*/

package org.checkstyle.suppressionxpathfilter.coding.equalshashcode;

public class InputXpathEqualsHashCodeNestedCase {
    public static class innerClass {
        public boolean equals(Object obj) { // violation 'without .* of 'hashCode()'.'
            return false;
        }
    }
}

