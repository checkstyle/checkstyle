/*
AvoidNoArgumentSuperConstructorCall


*/

package org.checkstyle.suppressionxpathfilter.coding.avoidnoargumentsuperconstructorcall;

public class InputXpathAvoidNoArgumentSuperConstructorCallStaticInner {
    static class Nested {
        Nested() {
            super(); // violation 'Unnecessary call to superclass constructor with no arguments.'
        }
    }
}
