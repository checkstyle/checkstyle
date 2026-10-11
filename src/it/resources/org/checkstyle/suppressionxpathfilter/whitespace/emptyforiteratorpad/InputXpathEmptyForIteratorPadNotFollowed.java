package org.checkstyle.suppressionxpathfilter.whitespace.emptyforiteratorpad;

public class InputXpathEmptyForIteratorPadNotFollowed {
    void method(int bad, int good) {
        for (bad = 0; ++bad < 2;) {//warn
        }
        for (good = 0; ++good < 1; ) {
        }
    }
}
