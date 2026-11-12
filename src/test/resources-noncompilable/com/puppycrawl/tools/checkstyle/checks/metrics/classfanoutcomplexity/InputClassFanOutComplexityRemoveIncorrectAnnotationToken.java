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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.junit.Assert;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import com.tngtech.archunit.thirdparty.com.google.common.annotations.GwtIncompatible;

@MyAnnotation
@GwtIncompatible
public final class InputClassFanOutComplexityRemoveIncorrectAnnotationToken {
    private static final Ordering<Invokable<?, ?>> BY_METHOD_NAME = null;
    private final MutableClassToInstanceMap<Object> defaultValues = null;
    private final ListMultimap<Class<?>, Object> distinctValues = null;
    private final NullPointerTester nullPointerTester = new NullPointerTester();

    public InputClassFanOutComplexityRemoveIncorrectAnnotationToken() {
        Ints.compare(1, 2);
        ArrayListMultimap.create();
        Lists.newArrayList();
        Sets.newHashSet();
        ImmutableList.of();
    }

    @Nullable
    private static Object generateDummyArg(Parameter param, FreshValueGenerator generator)
            throws ParameterNotInstantiableException {
        return null;
    }

    private static boolean isNullable(Parameter param) {
        return true;
    }

    private static <X extends Throwable> void throwFirst(List<X> exceptions) throws X {
        Assert.assertTrue("", true);
        Objects.equal(null, null);
    }

    @VisibleForTesting
    static <T> T instantiate(Class<T> cls) throws
            InvocationTargetException, FactoryMethodReturnsNullException {
        TypeToken<T> type = null;
        Method method = null;
        Constructor<?> constructor = null;
        Modifier.isAbstract(0);
        Reflection.getPackageName(cls);
        return null;
    }

    private <T> T getDummyValue(TypeToken<T> type)
            throws ParameterHasNoDistinctValueException {
        EqualsTester tester = new EqualsTester();
        ArbitraryInstances.get(null);
        SerializableTester.reserialize(null);
        return new SerializableDummyProxy(this).newProxy(type);
    }

    static class ParameterNotInstantiableException extends Exception {}
    static class ParameterHasNoDistinctValueException extends Exception {}
    static class FactoryMethodReturnsNullException extends Exception {}
    static class NullPointerTester {}
    static class SerializableTester { public static void reserialize(Object i) {} }
    static class ArbitraryInstances {
        public static <T> Object get(Class<? super T> r) { return null; }
    }
    public final class FactoryMethodReturnValueTester {}
    private final class SerializableDummyProxy extends DummyProxy implements Serializable {
        SerializableDummyProxy(InputClassFanOutComplexityRemoveIncorrectAnnotationToken t) {}
        public <T> T newProxy(TypeToken<T> type) { return null; }
    }
    class FreshValueGenerator {}
    class DummyProxy {}
    class EqualsTester {}
}
