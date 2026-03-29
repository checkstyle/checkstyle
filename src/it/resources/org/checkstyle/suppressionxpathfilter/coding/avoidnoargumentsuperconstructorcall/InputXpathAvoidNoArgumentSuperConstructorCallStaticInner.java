package org.checkstyle.suppressionxpathfilter.coding.avoidnoargumentsuperconstructorcall;

public class InputXpathAvoidNoArgumentSuperConstructorCallStaticInner {
    static class Nested {
        Nested() {
            super(); // warn
        }
    }
}
