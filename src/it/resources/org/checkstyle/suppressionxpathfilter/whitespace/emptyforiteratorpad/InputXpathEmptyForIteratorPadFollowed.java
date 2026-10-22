/*
EmptyForIteratorPad
option = (default)nospace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptyforiteratorpad;

public class InputXpathEmptyForIteratorPadFollowed {
    void method(int bad, int good) {
        for (bad = 0; ++bad < 1; ) { // violation "';' is followed by whitespace."
        }
        for (good = 0; ++good < 2;) {
        }
    }
}
