/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test that interface and annotation fields are NOT checked. */
public class InputGoogleNonConstantFieldNameInterfaceAnnotation {

    interface MyInterface {
        int FOO_BAR = 1;
        int _bad = 2;
        int gradle_9_5_1 = 3;
    }

    @interface MyAnnotation {
        String value() default "";
        int Count() default 0;
    }
}
