package org.checkstyle.suppressionxpathfilter.coding.nestedtrydepth;

public class InputXpathNestedTryDepthInnerClass {
    class Inner {
        public void test() {
            try {
                try {
                    try { // warn

                    } catch (Exception e) {}
                } catch (Exception e) {}
            } catch (Exception e) {}
        }
    }
}
