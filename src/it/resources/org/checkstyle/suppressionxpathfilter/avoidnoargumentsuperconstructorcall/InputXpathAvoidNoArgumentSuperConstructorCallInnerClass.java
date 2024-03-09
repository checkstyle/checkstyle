package org.checkstyle.suppressionxpathfilter.avoidnoargumentsuperconstructorcall;

public class InputXpathAvoidNoArgumentSuperConstructorCallInnerClass {
    public void test() {
        class Inner {
            Inner() {
                super(); /** warn */
            }
        }
    }
}
