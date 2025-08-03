package org.checkstyle.checks.suppressionxpathfilter.visibilitymodifier;

public class InputXpathVisibilityModifierInner {
    class InnerClass {
        private int field1;

        public int field2; // warn
    }
}
