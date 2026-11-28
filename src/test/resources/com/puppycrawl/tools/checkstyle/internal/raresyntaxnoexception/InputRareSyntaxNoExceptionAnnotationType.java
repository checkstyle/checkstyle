package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @interface InputRareSyntaxNoExceptionAnnotationType {
    String value() default "default";

    int count() default 0;

    Class<?> type() default Object.class;

    String[] tags() default {};

    Nested nested() default @Nested("nested");

    Runnable TASK = () -> {
        System.out.println("annotation task");
    };

    @Retention(RetentionPolicy.RUNTIME)
    @interface Nested {
        String value();
    }
}
