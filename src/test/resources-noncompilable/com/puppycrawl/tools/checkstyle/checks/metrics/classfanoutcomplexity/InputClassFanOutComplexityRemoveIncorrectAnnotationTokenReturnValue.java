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

import java.util.Set;

import org.junit.Assert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.tngtech.archunit.thirdparty.com.google.common.annotations.GwtIncompatible;
import junit.framework.AssertionFailedError;

@MyAnnotation
@GwtIncompatible
public final class InputClassFanOutComplexityRemoveIncorrectAnnotationTokenReturnValue {
    private final Set<String> packagesToTest = Sets.newHashSet();
    private final Class<?> declaringClass = null;
    private final ImmutableList<Invokable<?, ?>> factories = null;
    private final String factoryMethodsDescription = "";
    private Class<?> returnTypeToTest = Object.class;

    public InputClassFanOutComplexityRemoveIncorrectAnnotationTokenReturnValue thatReturn(
            Class<?> returnType) {
        returnTypeToTest = returnType;
        return this;
    }

    public InputClassFanOutComplexityRemoveIncorrectAnnotationTokenReturnValue testNulls()
            throws Exception {
        for (Invokable<?, ?> factory : getFactoriesToTest()) {
            Object instance = instantiate(factory);
            if (instance != null && packagesToTest.contains(Reflection
                    .getPackageName(instance.getClass()))) {
                try {
                    NullPointerTester.testAllPublicInstanceMethods(instance);
                } catch (AssertionError e) {
                    AssertionError error = new AssertionFailedError("Null check failed");
                    error.initCause(e);
                    throw error;
                }
            }
        }
        return this;
    }

    public InputClassFanOutComplexityRemoveIncorrectAnnotationTokenReturnValue testEquals()
            throws Exception {
        for (Invokable<?, ?> factory : getFactoriesToTest()) {
            try {
                testEqualsUsing(factory);
            } catch (FactoryMethodReturnsNullException e) {
                // If the factory returns null, we just skip it.
            }
        }
        return this;
    }

    public InputClassFanOutComplexityRemoveIncorrectAnnotationTokenReturnValue testSerializable()
            throws Exception {
        for (Invokable<?, ?> factory : getFactoriesToTest()) {
            Object instance = instantiate(factory);
            if (instance != null) {
                try {
                    SerializableTester.reserialize(instance);
                } catch (RuntimeException e) {
                    throw new AssertionFailedError("Serialization failed");
                }
            }
        }
        return this;
    }

    private ImmutableList<Invokable<?, ?>> getFactoriesToTest() {
        ImmutableList.Builder<Invokable<?, ?>> builder = ImmutableList.builder();
        for (Invokable<?, ?> factory : factories) {
            if (returnTypeToTest.isAssignableFrom(factory.getReturnType().getRawType())) {
                builder.add(factory);
            }
        }
        return builder.build();
    }

    private Object instantiate(Invokable<?, ?> f) { return null; }
    private void testEqualsUsing(Invokable<?, ?> f) throws FactoryMethodReturnsNullException {}

    static class FactoryMethodReturnsNullException extends Exception {}
    static class NullPointerTester {
        public static void testAllPublicInstanceMethods(Object i) {}
    }
    static class SerializableTester {
        public static void reserialize(Object i) {}
    }
}
