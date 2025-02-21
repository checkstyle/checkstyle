/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

public class InputMissingJavadocMethodReceiverParameter {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface Ann {
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
    public void foo(@Ann(Object.class) InputMissingJavadocMethodReceiverParameter this,
            final ByteBuffer buffer) {
        buffer.putInt(1);
    }

}
