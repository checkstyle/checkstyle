package org.checkstyle.suppressionxpathfilter.equalshashcode;

public class SuppressionXpathRegressionEqualsHashCodeNestedCase {
    public static class innerClass {
        public boolean equals(Object obj) { // warn
            return false;
        }
    }
}

