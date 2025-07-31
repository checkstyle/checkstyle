// non-compiled with eclipse: Annotation types  do not specify explicit target element
package com.puppycrawl.tools.checkstyle.api.fullident;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Nullable;

import org.junit.Assert;

import com.google.common.reflect.Invokable;

public class InputFullIdentReturnNoAnnotation {
    private static <T> @Nullable T invoke(Invokable<?, ? extends T> factory, List<?> args)
                throws InvocationTargetException, IllegalAccessException {
            T returnValue = factory.invoke(null, args.toArray());
            if (returnValue == null) {
                Assert.assertTrue(
                        Boolean.parseBoolean(factory
                                + " returns null but it's not annotated with @Nullable"));
            }
            return returnValue;
        }
}
