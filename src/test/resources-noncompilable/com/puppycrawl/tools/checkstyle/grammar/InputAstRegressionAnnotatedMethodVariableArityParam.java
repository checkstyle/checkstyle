// non-compiled with eclipse: Annotation types do not specify explicit target element
package com.puppycrawl.tools.checkstyle.grammar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import jakarta.annotation.Nullable;

import org.junit.Assert;

import com.google.common.reflect.Invokable;

public class InputAstRegressionAnnotatedMethodVariableArityParam {
    void varargLong(@I String @L [] @K [] @J ... vararg2) { }
    @SuppressWarnings("unused")
    void withUpperBound(List<? extends int[][]> list) {}
    private static <T> @Nullable T invoke(Invokable<?, ? extends T> factory, List<?> args)
                throws InvocationTargetException, IllegalAccessException {
            return null;
        }
}
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface I {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface J {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface K {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface L {}
