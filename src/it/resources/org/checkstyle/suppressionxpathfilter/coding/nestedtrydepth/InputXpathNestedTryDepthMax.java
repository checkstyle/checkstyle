/*
NestedTryDepth
max = 3


*/

package org.checkstyle.suppressionxpathfilter.coding.nestedtrydepth;

public class InputXpathNestedTryDepthMax {
    public void test() {
        try {
            try {
                try {
                    try {
                        // violation below 'Nested try depth is 4 (max allowed is 3).'
                        try {

                        } catch (Exception e) {}
                    } catch (Exception e) {}
                } catch (Exception e) {}
            } catch (Exception e) {}
        } catch (Exception e) {}
    }
}
