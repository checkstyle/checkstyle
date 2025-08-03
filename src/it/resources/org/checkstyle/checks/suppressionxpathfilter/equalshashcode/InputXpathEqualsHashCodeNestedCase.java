package org.checkstyle.checks.suppressionxpathfilter.equalshashcode;

public class InputXpathEqualsHashCodeNestedCase {
    public static class innerClass {
        public boolean equals(Object obj) { // warn
            return false;
        }
    }
}

