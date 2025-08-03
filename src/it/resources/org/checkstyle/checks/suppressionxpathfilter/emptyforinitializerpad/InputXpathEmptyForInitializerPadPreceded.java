package org.checkstyle.checks.suppressionxpathfilter.emptyforinitializerpad;

public class InputXpathEmptyForInitializerPadPreceded {
    void method(int bad, int good) {
        for ( ; bad < 1; bad++ ) {//warn
        }
        for (; good < 2; good++ ) {
        }
    }
}
