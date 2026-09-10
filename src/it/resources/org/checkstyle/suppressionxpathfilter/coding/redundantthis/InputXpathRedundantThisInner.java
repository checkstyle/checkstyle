package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisInner {
    class Inner {
        private String name;

        public String getName() {
            return this.name; // warn
        }
    }
}
