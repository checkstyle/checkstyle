/*
NestedForDepth
max = 2


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedfordepth;

public class InputXpathNestedForDepthMax {
    public void test() {
        for (int i1 = 0; i1 < 10; i1++) {
            for (int i2 = 0; i2 < 10; i2++) {
                for (int i3 = 0; i3 < 10; i3++) {
                    // violation below 'Nested for depth is 3 (max allowed is 2).'
                    for (int i4 = 0; i4 < 10; i4++) {
                    }
                }
            }
        }
    }
}
