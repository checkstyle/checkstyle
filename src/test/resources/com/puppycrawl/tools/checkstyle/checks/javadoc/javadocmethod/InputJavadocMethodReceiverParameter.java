/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

public class InputJavadocMethodReceiverParameter {

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
    public void foo(@Ann(Object.class) InputJavadocMethodReceiverParameter this,
            final ByteBuffer buffer) {
        buffer.putInt(1);
    }

}
