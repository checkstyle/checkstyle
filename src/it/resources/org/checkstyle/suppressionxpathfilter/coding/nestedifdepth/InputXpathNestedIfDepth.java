/*
NestedIfDepth
max = (default)1


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedifdepth;

public class InputXpathNestedIfDepth {
    public void test() {
        int a = 1;
        int b = 2;
        int c = 3;
        if (a > b) {
            if (c > b) {
                if (c > a) { // violation 'Nested if-else depth is 2 (max allowed is 1)'

                }
            }
        }
    }
}
