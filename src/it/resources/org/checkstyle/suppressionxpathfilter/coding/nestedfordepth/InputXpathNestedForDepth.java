/*
NestedForDepth
max = (default)1


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedfordepth;

public class InputXpathNestedForDepth {
    public void test() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                // violation below 'Nested for depth is 2 (max allowed is 1).'
                for (int k = 0; k < 100; k++) {

                }
            }
        }
    }
}
