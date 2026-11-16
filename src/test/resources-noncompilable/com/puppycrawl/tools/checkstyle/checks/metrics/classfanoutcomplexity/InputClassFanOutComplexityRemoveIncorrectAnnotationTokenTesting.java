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

import static com.google.common.base.Throwables.throwIfUnchecked;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import com.tngtech.archunit.thirdparty.com.google.common.annotations.GwtIncompatible;

@MyAnnotation
@GwtIncompatible
public class InputClassFanOutComplexityRemoveIncorrectAnnotationTokenTesting {
    private final NullPointerTester nullPointerTester = new NullPointerTester();

    public void testNulls(Class<?> cls) {
        try {
            doTestNulls(cls, Visibility.PACKAGE_PRIVATE);
        } catch (Exception e) {
            throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }

    void doTestNulls(Class<?> cls, Visibility visibility)
            throws ParameterNotInstantiableException, IllegalAccessException,
            InvocationTargetException, FactoryMethodReturnsNullException {
        if (!Modifier.isAbstract(cls.getModifiers())) {
            nullPointerTester.testConstructors(cls, visibility);
        }
        nullPointerTester.testStaticMethods(cls, visibility);
        if (hasInstanceMethodToTestNulls(cls, visibility)) {
            Object instance = instantiate(cls);
            if (instance != null) {
                nullPointerTester.testInstanceMethods(instance, visibility);
            }
        }
    }

    private boolean hasInstanceMethodToTestNulls(Class<?> c, Visibility visibility) {
        for (Method method : nullPointerTester.getInstanceMethodsToTest(c, visibility)) {
            for (com.google.common.reflect.Parameter param : Invokable.from(method)
                    .getParameters()) {
                if (!NullPointerTester.isPrimitiveOrNullable(param)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void testEquals(Class<?> cls) {
        try {
            doTestEquals(cls);
        } catch (Exception e) {
            throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }

    void doTestEquals(Class<?> cls)
            throws ParameterNotInstantiableException, ParameterHasNoDistinctValueException,
            IllegalAccessException, InvocationTargetException, FactoryMethodReturnsNullException {
        List<? extends Invokable<?, ?>> factories = Lists.newArrayList();
        for (Invokable<?, ?> factory : factories) {
            testEqualsUsing(factory);
        }
    }

    <T> @Nullable T instantiate(Class<T> cls)
            throws ParameterNotInstantiableException, IllegalAccessException,
            InvocationTargetException, FactoryMethodReturnsNullException {
        return null;
    }

    static class ParameterNotInstantiableException extends Exception {}
    static class ParameterHasNoDistinctValueException extends Exception {}
    static class FactoryMethodReturnsNullException extends Exception {}
    static class NullPointerTester {
        public static boolean isPrimitiveOrNullable(com.google.common.reflect.Parameter p) {
            return true;
        }
        public void testConstructors(Class<?> cls, Visibility v) {}
        public void testStaticMethods(Class<?> cls, Visibility v) {}
        public void testInstanceMethods(Object inst, Visibility v) {}
        public Method[] getInstanceMethodsToTest(Class<?> c, Visibility v) { return null; }
    }
    interface Visibility {
        Visibility PACKAGE_PRIVATE = null;
    }
}
