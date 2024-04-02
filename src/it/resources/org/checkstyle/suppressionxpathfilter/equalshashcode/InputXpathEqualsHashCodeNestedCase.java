package org.checkstyle.suppressionxpathfilter.equalshashcode;

public class InputXpathEqualsHashCodeNestedCase {
    public static class innerClass {
        public boolean equals(Object obj) { // warn
            return false;
        }
    }
}

