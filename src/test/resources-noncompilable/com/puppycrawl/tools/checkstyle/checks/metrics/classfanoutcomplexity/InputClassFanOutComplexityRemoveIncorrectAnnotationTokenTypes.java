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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

public class InputClassFanOutComplexityRemoveIncorrectAnnotationTokenTypes {

    @VisibleForTesting
    static class ParameterNotInstantiableException extends Exception {
        ParameterNotInstantiableException(Parameter parameter) {
            super("Cannot determine value for parameter " + parameter);
        }
    }

    @VisibleForTesting
    static class ParameterHasNoDistinctValueException extends Exception {
        ParameterHasNoDistinctValueException(Parameter parameter) {
            super("Cannot generate distinct value for parameter " + parameter);
        }
    }

    @VisibleForTesting
    static class FactoryMethodReturnsNullException extends Exception {
        FactoryMethodReturnsNullException(Invokable<?, ?> factory) {
            super(factory + " returns null.");
        }
    }

    static class NullPointerTester {
        public static boolean isPrimitiveOrNullable(com.google.common.reflect.Parameter p) {
            return true;
        }
        public <T> void setDefault(Class<T> type, T value) {}
        public void testConstructors(Class<?> cls, Visibility visibility) {}
        public void testStaticMethods(Class<?> cls, Visibility visibility) {}
        public void testInstanceMethods(Object instance, Visibility visibility) {}
        public Method[] getInstanceMethodsToTest(Class<?> c, Visibility visibility) {
            return null;
        }
    }

    static class SerializableTester {
        public static void reserialize(Object instance) {}
        public static void reserializeAndAssert(Object instance) {}
    }

    static class ArbitraryInstances {
        public static <T> Object get(Class<? super T> rawType) {
            return null;
        }
    }

    static class SerializableDummyProxy extends DummyProxy implements Serializable {
        public <T> T newProxy(TypeToken<T> type) {
            return null;
        }
    }

    static class FreshValueGenerator {
        public Object generateFresh(TypeToken<?> type) {
            return null;
        }
        public void addSampleInstances(Class key, Collection<Object> value) {}
    }

    static class DummyProxy {}

    static class EqualsTester {
        public void addEqualityGroup(Object instance, Object instance1) {}
        public void testEquals() {}
    }

    static class ItemReporter {}

    interface Visibility {
        Visibility PACKAGE_PRIVATE = null;
    }
}
