package org.checkstyle.suppressionxpathfilter.coding.nestedtrydepth;

public class InputXpathNestedTryDepthMax {
    public void test() {
        try {
            try {
                try {
                    try {
                        try { // warn

                        } catch (Exception e) {}
                    } catch (Exception e) {}
                } catch (Exception e) {}
            } catch (Exception e) {}
        } catch (Exception e) {}
    }
}
