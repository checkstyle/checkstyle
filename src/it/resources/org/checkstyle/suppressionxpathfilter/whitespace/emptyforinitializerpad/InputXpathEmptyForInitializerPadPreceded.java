/*
EmptyForInitializerPad
option = (default)nospace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptyforinitializerpad;

public class InputXpathEmptyForInitializerPadPreceded {
    void method(int bad, int good) {
        for ( ; bad < 1; bad++ ) { // violation "';' is preceded with whitespace."
        }
        for (; good < 2; good++ ) {
        }
    }
}
