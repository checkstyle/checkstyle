/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test that interface and annotation fields are NOT checked. */
public class InputGoogleMemberNameInterfaceAnnotation {

    // Interface fields should NOT be checked
    interface MyInterface {
        int FOO_BAR = 1;
        int _bad = 2;
        int gradle_9_5_1 = 3;
    }

    // Annotation fields should NOT be checked
    @interface MyAnnotation {
        String value() default "";
        int Count() default 0;
    }
}
