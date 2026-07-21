/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnnotationOverlappingTrue {
    String value = "field";
    String Outer = "field";

    @interface Info {
        int value();
    }

    @interface Outer {
        @interface Inner {
            int value();
        }
    }

    void method1(String value) {
        @Info(value = 1)
        int x = 0;

        // violation below 'Reference to instance variable 'value' needs "this.".'
        value = value;
    }

    void method2(String Outer) {
        @Outer.Inner(value = 1)
        int x = 0;

        // violation below 'Reference to instance variable 'Outer' needs "this.".'
        Outer = Outer;
    }
}
