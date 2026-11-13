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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import com.tngtech.archunit.thirdparty.com.google.common.annotations.GwtIncompatible;

@MyAnnotation
@GwtIncompatible
public class InputClassFanOutComplexityRemoveIncorrectAnnotationTokenValues {
    private final MutableClassToInstanceMap<Object> defaultValues =
            MutableClassToInstanceMap.create();
    private final ListMultimap<Class<?>, Object> distinctValues = null;

    private void testEqualsUsing(final Invokable<?, ?> factory)
            throws ParameterNotInstantiableException, ParameterHasNoDistinctValueException,
            IllegalAccessException, InvocationTargetException, FactoryMethodReturnsNullException {
        ImmutableList<com.google.common.reflect.Parameter> params = factory.getParameters();
        List<FreshValueGenerator> argGenerators = Lists.newArrayListWithCapacity(params.size());
        List<Object> args = Lists.newArrayListWithCapacity(params.size());
        for (com.google.common.reflect.Parameter param : params) {
            FreshValueGenerator generator = newFreshValueGenerator();
            argGenerators.add(generator);
            args.add(generateDummyArg(null, generator));
        }
        EqualsTester tester = new EqualsTester();
        tester.addEqualityGroup(null, null);
    }

    private List<Object> generateEqualFactoryArguments(
            Invokable<?, ?> factory, List<Parameter> params, List<Object> args)
            throws ParameterNotInstantiableException, FactoryMethodReturnsNullException,
            InvocationTargetException, IllegalAccessException {
        List<Object> equalArgs = Lists.newArrayList(args);
        return equalArgs;
    }

    private FreshValueGenerator newFreshValueGenerator() {
        FreshValueGenerator generator = new FreshValueGenerator();
        for (Map.Entry<Class<?>, Collection<Object>> entry : distinctValues.asMap().entrySet()) {
            generator.addSampleInstances(entry.getKey(), entry.getValue());
        }
        return generator;
    }

    private List<Object> getDummyArguments(Invokable<?, ?> invokable)
            throws ParameterNotInstantiableException {
        List<Object> args = Lists.newArrayList();
        for (com.google.common.reflect.Parameter param : invokable.getParameters()) {
            args.add(getDummyValue(param.getType()));
        }
        return args;
    }

    private <T> T getDummyValue(TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        T defaultValue = (T) defaultValues.getInstance(rawType);
        if (defaultValue != null) {
            return defaultValue;
        }
        ArbitraryInstances.get(rawType);
        return new SerializableDummyProxy().newProxy(type);
    }

    private Object generateDummyArg(Parameter p, FreshValueGenerator g) { return null; }

    static class ParameterNotInstantiableException extends Exception {}
    static class ParameterHasNoDistinctValueException extends Exception {}
    static class FactoryMethodReturnsNullException extends Exception {}
    static class ArbitraryInstances {
        public static <T> Object get(Class<? super T> r) {
            return null;
        }
    }
    static class SerializableDummyProxy {
        public <T> T newProxy(TypeToken<T> t) {
            return null;
        }
    }
    static class FreshValueGenerator {
        public void addSampleInstances(Class k, Collection<Object> v) {}
    }
    static class EqualsTester {
        public void addEqualityGroup(Object a, Object b) {}
    }
}
