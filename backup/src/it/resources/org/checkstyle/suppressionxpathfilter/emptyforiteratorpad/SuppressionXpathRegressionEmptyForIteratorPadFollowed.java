package org.checkstyle.suppressionxpathfilter.emptyforiteratorpad;

public class SuppressionXpathRegressionEmptyForIteratorPadFollowed {
    void method(int bad, int good) {
        for (bad = 0; ++bad < 1; ) {//warn
        }
        for (good = 0; ++good < 2;) {
        }
    }
}
