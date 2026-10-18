/*
AvoidNoArgumentSuperConstructorCall


*/

package org.checkstyle.suppressionxpathfilter.coding.avoidnoargumentsuperconstructorcall;

public class InputXpathAvoidNoArgumentSuperConstructorCallInnerClass {
    public void test() {
        class Inner {
            Inner() {
                super();
                // violation above 'Unnecessary call to superclass constructor with no arguments.'
            }
        }
    }
}
