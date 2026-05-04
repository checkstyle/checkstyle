/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputRequireThisAnnotationQualified {

    final String CONTAINER = "c";

    @MyAnnotation(CONTAINER)
    // violation above 'Reference to instance variable 'CONTAINER' needs "this.".'
    void method() {}

    @java.lang.Deprecated
    void method2() {}

    @interface MyAnnotation {
        String value();
    }

    @Target(ElementType.TYPE_USE)
    @interface Other {}
}
