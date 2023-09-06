package org.checkstyle.suppressionxpathfilter.avoidnoargumentsuperconstructorcall;

public class SuppressionXpathRegressionAvoidNoArgumentSuperConstructorCallInnerClass {
    public void test() {
        class Inner {
            Inner() {
                super(); /** warn */
            }
        }
    }
}
