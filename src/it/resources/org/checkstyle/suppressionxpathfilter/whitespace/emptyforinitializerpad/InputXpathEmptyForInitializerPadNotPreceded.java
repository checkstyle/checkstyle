/*
EmptyForInitializerPad
option = space


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptyforinitializerpad;

public class InputXpathEmptyForInitializerPadNotPreceded {
    void method(int bad, int good) {
        for (; bad < 1; bad++ ) { // violation "';' is not preceded with whitespace."
        }
        for ( ; good < 2; good++ ) {
        }
    }
}
