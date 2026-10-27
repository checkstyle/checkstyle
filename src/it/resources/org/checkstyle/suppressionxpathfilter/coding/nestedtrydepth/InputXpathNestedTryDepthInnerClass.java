/*
NestedTryDepth
max = (default)1


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedtrydepth;

public class InputXpathNestedTryDepthInnerClass {
    class Inner {
        public void test() {
            try {
                try {
                    // violation below 'Nested try depth is 2 (max allowed is 1).'
                    try {

                    } catch (Exception e) {}
                } catch (Exception e) {}
            } catch (Exception e) {}
        }
    }
}
