/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnnotationDefault {

    static final int X = 10;

    @interface MyAnnotation {
        int value() default X;
    }

}