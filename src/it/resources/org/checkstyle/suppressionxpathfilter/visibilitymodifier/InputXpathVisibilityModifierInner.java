package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class InputXpathVisibilityModifierInner {
    class InnerClass {
        private int field1;

        public int field2; // warn
    }
}
