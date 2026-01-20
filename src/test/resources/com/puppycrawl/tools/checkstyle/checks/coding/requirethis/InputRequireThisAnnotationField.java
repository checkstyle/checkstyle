/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public @interface InputRequireThisAnnotationField {

    Object A_CONSTANT_VALUE = new Object();

    int NUMBER = 10;

    class InnerClass {

        void test() {
            System.out.println(A_CONSTANT_VALUE);
            System.out.println(NUMBER);
        }
    }
}
