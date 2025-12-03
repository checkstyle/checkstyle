/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public @interface InputRequireThisAnnotationInterfaceInnerClass {

    Object CONSTANT = new Object();

    String value() default "";

    final class InnerClass {

        public void instanceMethod() {
            final Object var = CONSTANT; // no violation
        }
    }
}
