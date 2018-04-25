package org.checkstyle.suppressionxpathfilter.nestedtrydepth;

public class SuppressionXpathRegressionNestedTryDepth {
    public void test() {
        try {
            try {
                try { //warn

                } catch (Exception e) {}
            } catch (Exception e) {}
        } catch (Exception e) {}
    }
}
