/*
NestedIfDepth
max = 3


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedifdepth;

public class InputXpathNestedIfDepthMax {
    public void test() {
        int a = 1;
        int b = 2;
        int c = 3;
        if (a > b) {
            if (c > b) {
                if (c > a) {
                    if (1 == 2) {
                        if (2 == 3) { // violation 'Nested if-else depth is 4 (max allowed is 3)'

                        }
                    }
                }
            }
        }
    }
}
