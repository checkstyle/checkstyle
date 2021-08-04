/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis; // ok

public @interface InputRequireThisAnnotationInterface {
    String DEFAULT_VALUE = "DEFAULT_VALUE";

    String value() default DEFAULT_VALUE;

    String[] results() default {};
}
