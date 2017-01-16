package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

public class InputJavadocReceiverParameter {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface UnknownInitialization {
        /**
         * A dummy annotation to check Java 8's receiver parameter handling.
         *
         * @return a class
         */
        Class<?> value() default Object.class;
    }

    /**
     * Function to check handling of Java 8's receiver parameter.
     *
     * @param buffer dummy argument
     */
    public void foo(@UnknownInitialization(InputJavadocReceiverParameter.class) InputJavadocReceiverParameter this,
            final ByteBuffer buffer) {
        buffer.putInt(1);
    }

}
