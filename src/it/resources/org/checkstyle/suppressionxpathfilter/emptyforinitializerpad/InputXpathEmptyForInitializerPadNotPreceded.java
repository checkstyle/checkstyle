package org.checkstyle.suppressionxpathfilter.emptyforinitializerpad;

public class InputXpathEmptyForInitializerPadNotPreceded {
    void method(int bad, int good) {
        for (; bad < 1; bad++ ) {//warn
        }
        for ( ; good < 2; good++ ) {
        }
    }
}
