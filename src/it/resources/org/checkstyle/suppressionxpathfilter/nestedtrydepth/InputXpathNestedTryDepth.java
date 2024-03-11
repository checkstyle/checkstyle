package org.checkstyle.suppressionxpathfilter.nestedtrydepth;

public class InputXpathNestedTryDepth {
    public void test() {
        try {
            try {
                try { //warn

                } catch (Exception e) {}
            } catch (Exception e) {}
        } catch (Exception e) {}
    }
}
