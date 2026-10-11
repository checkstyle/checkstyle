package org.checkstyle.suppressionxpathfilter.design.visibilitymodifier;

public class InputXpathVisibilityModifierInner {
    class InnerClass {
        private int field1;

        public int field2; // warn
    }
}
