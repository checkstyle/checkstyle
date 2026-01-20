/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputRequireThisAnnotationConstantOverlappingFalse {

    final String CONTAINER = "standalone-filecs";

    @MyResourceF(CONTAINER) // violation 'Reference to instance variable 'CONTAINER' needs "this.".'
    Object server;

    @TypeAnnotationOF
    private final String warningsType = "boxing";

    // violation below 'Reference to instance variable 'warningsType' needs "this.".'
    @SuppressWarnings(value = warningsType)
    public String method() {
        return Integer.toString(5);
    }

    @TwoParamAnnotationOF(value = "4", dayOfWeek = 1)
    public static final String EMPTY_STRING = "";

    @interface MyResourceF {
        String value();
    }

    @Target(ElementType.TYPE_USE)
    @interface TypeAnnotationOF {
    }

    @interface TwoParamAnnotationOF {
        String value();

        int dayOfWeek();
    }
}
