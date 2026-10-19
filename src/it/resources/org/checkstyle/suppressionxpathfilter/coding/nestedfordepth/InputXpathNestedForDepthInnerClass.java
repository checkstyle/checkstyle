/*
NestedForDepth
max = (default)1


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedfordepth;

public class InputXpathNestedForDepthInnerClass {
    class Inner {
        public void test() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    // violation below 'Nested for depth is 2 (max allowed is 1).'
                    for (int k = 0; k < 10; k++) {
                    }
                }
            }
        }
    }
}
