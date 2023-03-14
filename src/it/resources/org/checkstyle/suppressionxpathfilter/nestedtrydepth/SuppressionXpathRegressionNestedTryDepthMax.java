package org.checkstyle.suppressionxpathfilter.nestedtrydepth;

public class SuppressionXpathRegressionNestedTryDepthMax {
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
