/*
ClassFanOutComplexity
max = 24
excludedClasses = (default)ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte, \
                  Character, Class, Collection, Deprecated, Deque, Double, DoubleStream, \
                  EnumSet, Exception, Float, FunctionalInterface, HashMap, HashSet, \
                  IllegalArgumentException, IllegalStateException, IndexOutOfBoundsException, \
                  IntStream, Integer, LinkedHashMap, LinkedHashSet, LinkedList, List, Long, \
                  LongStream, Map, NullPointerException, Object, Optional, OptionalDouble, \
                  OptionalInt, OptionalLong, Override, Queue, RuntimeException, SafeVarargs, \
                  SecurityException, Set, Short, SortedMap, SortedSet, Stream, String, \
                  StringBuffer, StringBuilder, SuppressWarnings, Throwable, TreeMap, TreeSet, \
                  UnsupportedOperationException, Void, boolean, byte, char, double, float, \
                  int, long, short, var, void
excludeClassesRegexps = (default)^$
excludedPackages = (default)

*/
// non-compiled with eclipse: Annotation types do not specify explicit target element
// non-compiled with javac: contains specially crafted set of imports that requires classpath
package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;

import javax.annotation.Nullable;

import org.junit.Assert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import com.tngtech.archunit.thirdparty.com.google.common.annotations.GwtIncompatible;

@MyAnnotation
@GwtIncompatible
public final class InputClassFanOutComplexityRemoveIncorrectAnnotationToken {
    @Nullable
    private static Object generateDummyArg(Parameter param, FreshValueGenerator generator)
            throws ParameterNotInstantiableException {
        if (isNullable(param)) {
            return null;
        }
        Object arg = generator.generateFresh(null);
        if (arg == null) {
            throw new ParameterNotInstantiableException(param);
        }
        return arg;
    }

    private static boolean isNullable(Parameter param) {
        return true;
    }

    private static <X extends Throwable> void throwFirst(List<X> exceptions) throws X {
        if (!exceptions.isEmpty()) {
            throw exceptions.get(0);
        }
    }

    private static <T> ImmutableList<Invokable<?, ? extends T>> getFactories(TypeToken<T> type) {
        List<Invokable<?, ? extends T>> factories = Lists.newArrayList();
        for (Method method : type.getRawType().getDeclaredMethods()) {
            Invokable<?, ?> invokable = type.method(method);
            if (!invokable.isPrivate() && !invokable.isSynthetic() && invokable.isStatic()
                    && type.isSupertypeOf(invokable.getReturnType())) {
                @SuppressWarnings("unchecked")
                Invokable<?, ? extends T> factory = (Invokable<?, ? extends T>) invokable;
                factories.add(factory);
            }
        }
        if (!Modifier.isAbstract(type.getRawType().getModifiers())) {
            for (Constructor<?> constructor : type.getRawType().getDeclaredConstructors()) {
                factories.add(type.constructor(constructor));
            }
        }
        return ImmutableList.copyOf(factories);
    }

    private static <T> T createInstance(Invokable<?, ? extends T> factory, List<?> args)
            throws FactoryMethodReturnsNullException, InvocationTargetException,
            IllegalAccessException {
        T instance = invoke(factory, args);
        if (instance == null) {
            throw new FactoryMethodReturnsNullException(factory);
        }
        return instance;
    }

    private static <T> @Nullable T invoke(Invokable<?, ? extends T> factory, List<?> args)
            throws InvocationTargetException, IllegalAccessException {
        T returnValue = factory.invoke(null, args.toArray());
        if (returnValue == null) {
            Assert.assertTrue(factory + " returns null", isNullable(null));
        }
        return returnValue;
    }

    static class ParameterNotInstantiableException extends Exception {
        ParameterNotInstantiableException(Parameter p) {}
    }

    static class FactoryMethodReturnsNullException extends Exception {
        FactoryMethodReturnsNullException(Invokable<?, ?> f) {}
    }

    class FreshValueGenerator {
        public Object generateFresh(TypeToken<?> type) {
            return null;
        }
    }
}
