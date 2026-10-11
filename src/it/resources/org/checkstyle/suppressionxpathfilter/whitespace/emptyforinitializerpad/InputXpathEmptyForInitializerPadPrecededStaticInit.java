package org.checkstyle.suppressionxpathfilter.whitespace.emptyforinitializerpad;

public class InputXpathEmptyForInitializerPadPrecededStaticInit {
    static int i;
    static {
        for ( ; i < 1; i++) { // warn
        }
    }
}
