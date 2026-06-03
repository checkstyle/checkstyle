/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnnotationQualifiedDot {

    final String Outer = "field";
    final String value = "field";

    @interface Outer {
        @interface Inner {
            String value();
        }
    }

    @interface Another {
        String value();
    }

    @Outer.Inner(value = "test")
    void method1() {}

    // violation below 'Reference to instance variable 'value' needs "this.".'
    @Another(value = value)
    void method2() {}
}
