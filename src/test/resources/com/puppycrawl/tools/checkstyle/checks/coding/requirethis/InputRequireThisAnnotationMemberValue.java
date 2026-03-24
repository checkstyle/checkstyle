/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

@InputRequireThisAnnotationMemberValue.MyAnno(value = field)
public class InputRequireThisAnnotationMemberValue {
    int field = 0;

    @interface MyAnno {
        int value() default 0;
    }
}

