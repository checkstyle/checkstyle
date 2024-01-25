package org.checkstyle.suppressionxpathfilter.equalshashcode;

public class SuppressionXpathRegressionEqualsHashCodeNestedCase {
    public boolean equals(Object obj) { // warn
        return false;
    }
    public static class innerClass {
        public int hashCode() { // No problem, as
            return 0;
        }
        public boolean equals(Object obj) {
            return false;
        }
    }
}

