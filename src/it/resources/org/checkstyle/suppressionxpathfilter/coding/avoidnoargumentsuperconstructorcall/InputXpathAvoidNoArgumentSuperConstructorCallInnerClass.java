package org.checkstyle.suppressionxpathfilter.coding.avoidnoargumentsuperconstructorcall;

public class InputXpathAvoidNoArgumentSuperConstructorCallInnerClass {
    public void test() {
        class Inner {
            Inner() {
                super(); /** warn */
            }
        }
    }
}
