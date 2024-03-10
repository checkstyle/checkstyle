package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class SuppressionXpathRegressionVisibilityModifierInner {
    class InnerClass {
        private int field1;

        public int field2; // warn
    }
}
