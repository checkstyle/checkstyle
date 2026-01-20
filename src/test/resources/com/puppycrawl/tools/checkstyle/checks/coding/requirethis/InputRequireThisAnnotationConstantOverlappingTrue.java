/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputRequireThisAnnotationConstantOverlappingTrue {

    final String CONTAINER = "standalone-filecs";

    @MyResource(CONTAINER)
    Object server;

    @TypeAnnotationOT
    private final String warningsType = "boxing";

    @SuppressWarnings(value = warningsType)
    public String method() {
        return Integer.toString(5);
    }

    @TwoParamAnnotationOT(value = "4", dayOfWeek = 1)
    public static final String EMPTY_STRING = "";

    @interface MyResource {
        String value();
    }

    @Target(ElementType.TYPE_USE)
    @interface TypeAnnotationOT {
    }

    @interface TwoParamAnnotationOT {
        String value();

        int dayOfWeek();
    }
}
