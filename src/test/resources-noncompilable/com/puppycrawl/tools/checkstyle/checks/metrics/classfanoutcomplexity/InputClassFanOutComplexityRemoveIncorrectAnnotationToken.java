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
package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.throwIfUnchecked;

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
import junit.framework.AssertionFailedError;


/**
 * Tester that runs automated sanity tests for any given class. A typical use case is to test static
 * factory classes like:
 *
 * <pre>
 * interface Book {...}
 * public class Books {
 *   public static Book hardcover(String title) {...}
 *   public static Book paperback(String title) {...}
 * }
 * </pre>
 *
 * <p>And all the created {@code Book} instances can be tested with:
 *
 * <pre>
 * new InputClassFanOutComplexityRemoveIncorrectAnnotationToken()
 *     .forAllPublicStaticMethods(Books.class)
 *     .thatReturn(Book.class)
 *     .testEquals(); // or testNulls(), testSerializable() etc.
 * </pre>
 *
 * @author Ben Yu
 * @since 14.0
 */
@MyAnnotation
@GwtIncompatible
public final class InputClassFanOutComplexityRemoveIncorrectAnnotationToken {
    private static final Ordering<Invokable<?, ?>> BY_METHOD_NAME =
            new Ordering<Invokable<?, ?>>() {
                @Override
                public int compare(Invokable<?, ?> left, Invokable<?, ?> right) {
                    return left.getName().compareTo(right.getName());
                }
            };

    private static final Ordering<Invokable<?, ?>> BY_PARAMETERS =
            new Ordering<Invokable<?, ?>>() {
                @Override
                public int compare(Invokable<?, ?> left, Invokable<?, ?> right) {
                    return Ordering.usingToString().compare(left.getParameters(),
                            right.getParameters());
                }
            };

    private static final Ordering<Invokable<?, ?>> BY_NUMBER_OF_PARAMETERS =
            new Ordering<Invokable<?, ?>>() {
                @Override
                public int compare(Invokable<?, ?> left, Invokable<?, ?> right) {
                    return Ints.compare(left.getParameters().size(), right.getParameters().size());
                }
            };

    private final MutableClassToInstanceMap<Object> defaultValues =
            MutableClassToInstanceMap.create();
    private final ListMultimap<Class<?>, Object> distinctValues = ArrayListMultimap.create();
    private final NullPointerTester nullPointerTester = new NullPointerTester();

    public InputClassFanOutComplexityRemoveIncorrectAnnotationToken() {
        // TODO(benyu): bake these into ArbitraryInstances.
        setDefault(byte.class, (byte) 1);
        setDefault(Byte.class, (byte) 1);
        setDefault(short.class, (short) 1);
        setDefault(Short.class, (short) 1);
        setDefault(int.class, 1);
        setDefault(Integer.class, 1);
        setDefault(long.class, 1L);
        setDefault(Long.class, 1L);
        setDefault(float.class, 1F);
        setDefault(Float.class, 1F);
        setDefault(double.class, 1D);
        setDefault(Double.class, 1D);
        setDefault(Class.class, Class.class);
    }

    private static boolean hashCodeInsensitiveToArgReference(
            Invokable<?, ?> factory, List<Object> args, int i, Object alternateArg)
            throws FactoryMethodReturnsNullException, InvocationTargetException,
            IllegalAccessException {
        List<Object> tentativeArgs = Lists.newArrayList(args);
        tentativeArgs.set(i, alternateArg);
        return createInstance(factory, tentativeArgs).hashCode()
                == createInstance(factory, args).hashCode();
    }

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

    /**
     * Factories with the least number of parameters are listed first.
     */
    private static <T> ImmutableList<Invokable<?, ? extends T>> getFactories(TypeToken<T> type) {
        List<Invokable<?, ? extends T>> factories = Lists.newArrayList();
        for (Method method : type.getRawType().getDeclaredMethods()) {
            Invokable<?, ?> invokable = type.method(method);
            if (!invokable.isPrivate()
                    && !invokable.isSynthetic()
                    && invokable.isStatic()
                    && type.isSupertypeOf(invokable.getReturnType())) {
                @SuppressWarnings("unchecked") // guarded by isAssignableFrom()
                Invokable<?, ? extends T> factory = (Invokable<?, ? extends T>) invokable;
                factories.add(factory);
            }
        }
        if (!Modifier.isAbstract(type.getRawType().getModifiers())) {
            for (Constructor<?> constructor : type.getRawType().getDeclaredConstructors()) {
                Invokable<T, T> invokable = type.constructor(constructor);
                if (!invokable.isPrivate() && !invokable.isSynthetic()) {
                    factories.add(invokable);
                }
            }
        }
        for (Invokable<?, ?> factory : factories) {
            factory.setAccessible(true);
        }
        // Sorts methods/constructors with least number of parameters first since it's likely
        // easier to
        // fill dummy parameter values for them. Ties are broken by name then by the string
        // form of the
        // parameter list.
        return BY_NUMBER_OF_PARAMETERS
                .compound(BY_METHOD_NAME)
                .compound(BY_PARAMETERS)
                .immutableSortedCopy(factories);
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
            Assert.assertTrue(
                    factory + " returns null but it's not annotated with @Nullable",
                    isNullable(null));
        }
        return returnValue;
    }

    /**
     * Sets the default value for {@code type}. The default value isn't used in testing {@link
     * Object#equals} because more than one sample instances are needed for testing inequality.
     * To set
     * distinct values for equality testing, use {@link #setDistinctValues} instead.
     */
    public <T> InputClassFanOutComplexityRemoveIncorrectAnnotationToken setDefault(Class<T> type,
                                                                                   T value) {
        nullPointerTester.setDefault(type, value);
        defaultValues.putInstance(type, value);
        return this;
    }

    /**
     * Sets distinct values for {@code type}, so that when a class {@code Foo} is tested for {@link
     * Object#equals} and {@link Object#hashCode}, and its construction requires a
     * parameter of {@code
     * type}, the distinct values of {@code type} can be passed as parameters to create {@code Foo}
     * instances that are unequal.
     *
     * <p>Calling {@code setDistinctValues(type, v1, v2)} also sets the default value for
     * {@code type}
     * that's used for {@link #testNulls}.
     *
     * <p>Only necessary for types where
     * {@link InputClassFanOutComplexityRemoveIncorrectAnnotationToken} doesn't
     * already know how to create
     * distinct values.
     *
     * @return this tester instance
     * @since 17.0
     */
    public <T> InputClassFanOutComplexityRemoveIncorrectAnnotationToken
    setDistinctValues(Class<T> type, T value1, T value2) {
        checkNotNull(type);
        checkNotNull(value1);
        checkNotNull(value2);
        checkArgument(!Objects.equal(value1, value2), "Duplicate value provided.");
        distinctValues.replaceValues(type, ImmutableList.of(value1, value2));
        setDefault(type, value1);
        return this;
    }

    /**
     * Tests that {@code cls} properly checks null on all constructor and method parameters that
     * aren't annotated nullable (according to the rules of {@link NullPointerTester}). In details:
     *
     * <ul>
     *   <li>All non-private static methods are checked such that passing null for any parameter
     *       that's not annotated nullable should throw {@link NullPointerException}.
     *   <li>If there is any non-private constructor or non-private static factory method
     *   declared by
     *       {@code cls}, all non-private instance methods will be checked too using the instance
     *       created by invoking the constructor or static factory method.
     *   <li>If there is any non-private constructor or non-private static factory method
     *   declared by
     *       {@code cls}:
     *       <ul>
     *         <li>Test will fail if default value for a parameter cannot be determined.
     *         <li>Test will fail if the factory method returns null so testing instance methods is
     *             impossible.
     *         <li>Test will fail if the constructor or factory method throws exception.
     *       </ul>
     *   <li>If there is no non-private constructor or non-private static factory method declared by
     *       {@code cls}, instance methods are skipped for nulls test.
     *   <li>Nulls test is not performed on method return values unless the method is a non-private
     *       static factory method whose return type is {@code cls} or {@code cls}'s subtype.
     * </ul>
     */
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
            InvocationTargetException,
            FactoryMethodReturnsNullException {
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

    /**
     * Tests the {@link Object#equals} and {@link Object#hashCode} of {@code cls}. In details:
     *
     * <ul>
     *   <li>The non-private constructor or non-private static factory method with the most
     *   parameters
     *       is used to construct the sample instances. In case of tie, the candidate constructors
     *       or
     *       factories are tried one after another until one can be used to construct sample
     *       instances.
     *   <li>For the constructor or static factory method used to construct instances, it's checked
     *       that when equal parameters are passed, the result instance should also be equal; and
     *       vice
     *       versa.
     *   <li>If a non-private constructor or non-private static factory method exists:
     *       <ul>
     *         <li>Test will fail if default value for a parameter cannot be determined.
     *         <li>Test will fail if the factory method returns null so testing instance methods is
     *             impossible.
     *         <li>Test will fail if the constructor or factory method throws exception.
     *       </ul>
     *   <li>If there is no non-private constructor or non-private static factory method declared by
     *       {@code cls}, no test is performed.
     *   <li>Equality test is not performed on method return values unless the method is a
     *   non-private
     *       static factory method whose return type is {@code cls} or {@code cls}'s subtype.
     *   <li>Inequality check is not performed against state mutation methods such as {@link
     *       List#add}, or functional update methods such as {@link
     *       com.google.common.base.Joiner#skipNulls}.
     * </ul>
     *
     * <p>Note that constructors taking a builder object cannot be tested effectively because
     * semantics of builder can be arbitrarily complex. Still, a factory class can be created in the
     * test to facilitate equality testing. For example:
     *
     * <pre>
     * public class FooTest {
     *
     *   private static class FooFactoryForTest {
     *     public static Foo create(String a, String b, int c, boolean d) {
     *       return Foo.builder()
     *           .setA(a)
     *           .setB(b)
     *           .setC(c)
     *           .setD(d)
     *           .build();
     *     }
     *   }
     *
     *   public void testEquals() {
     *     new InputClassFanOutComplexityRemoveIncorrectAnnotationToken()
     *       .forAllPublicStaticMethods(FooFactoryForTest.class)
     *       .thatReturn(Foo.class)
     *       .testEquals();
     *   }
     * }
     * </pre>
     *
     * <p>It will test that Foo objects created by the {@code create(a, b, c, d)} factory
     * method with
     * equal parameters are equal and vice versa, thus indirectly tests the builder equality.
     */
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
        if (cls.isEnum()) {
            return;
        }
        List<? extends Invokable<?, ?>> factories = Lists.reverse(getFactories(TypeToken.of(cls)));
        if (factories.isEmpty()) {
            return;
        }
        int numberOfParameters = factories.get(0).getParameters().size();
        List<ParameterNotInstantiableException> paramErrors = Lists.newArrayList();
        List<ParameterHasNoDistinctValueException> distinctValueErrors = Lists.newArrayList();
        List<InvocationTargetException> instantiationExceptions = Lists.newArrayList();
        List<FactoryMethodReturnsNullException> nullErrors = Lists.newArrayList();
        // Try factories with the greatest number of parameters.
        for (Invokable<?, ?> factory : factories) {
            if (factory.getParameters().size() == numberOfParameters) {
                try {
                    testEqualsUsing(factory);
                    return;
                } catch (ParameterNotInstantiableException e) {
                    paramErrors.add(e);
                } catch (ParameterHasNoDistinctValueException e) {
                    distinctValueErrors.add(e);
                } catch (InvocationTargetException e) {
                    instantiationExceptions.add(e);
                } catch (FactoryMethodReturnsNullException e) {
                    nullErrors.add(e);
                }
            }
        }
        throwFirst(paramErrors);
        throwFirst(distinctValueErrors);
        throwFirst(instantiationExceptions);
        throwFirst(nullErrors);
    }

    /**
     * Instantiates {@code cls} by invoking one of its non-private constructors or non-private
     * static
     * factory methods with the parameters automatically provided using dummy values.
     *
     * @return The instantiated instance, or {@code null} if the class has no non-private
     * constructor
     * or factory method to be constructed.
     */
    <T> @Nullable T instantiate(Class<T> cls)
            throws ParameterNotInstantiableException, IllegalAccessException,
            InvocationTargetException,
            FactoryMethodReturnsNullException {
        if (cls.isEnum()) {
            T[] constants = cls.getEnumConstants();
            if (constants.length > 0) {
                return constants[0];
            }
            else {
                return null;
            }
        }
        TypeToken<T> type = TypeToken.of(cls);
        List<ParameterNotInstantiableException> paramErrors = Lists.newArrayList();
        List<InvocationTargetException> instantiationExceptions = Lists.newArrayList();
        List<FactoryMethodReturnsNullException> nullErrors = Lists.newArrayList();
        for (Invokable<?, ? extends T> factory : getFactories(type)) {
            T instance;
            try {
                instance = instantiate(factory);
            } catch (ParameterNotInstantiableException e) {
                paramErrors.add(e);
                continue;
            } catch (InvocationTargetException e) {
                instantiationExceptions.add(e);
                continue;
            }
            if (instance == null) {
                nullErrors.add(new FactoryMethodReturnsNullException(factory));
            }
            else {
                return instance;
            }
        }
        throwFirst(paramErrors);
        throwFirst(instantiationExceptions);
        throwFirst(nullErrors);
        return null;
    }

    /**
     * Instantiates using {@code factory}. If {@code factory} is annotated nullable and
     * returns null,
     * null will be returned.
     *
     * @throws ParameterNotInstantiableException if the static methods cannot be invoked because the
     *                                           default value of a parameter cannot be determined.
     * @throws IllegalAccessException            if the class isn't public or is nested inside a
     * non-public
     *                                           class, preventing its methods from being
     *                                           accessible.
     * @throws InvocationTargetException         if a static method threw exception.
     */
    private <T> @Nullable T instantiate(Invokable<?, ? extends T> factory)
            throws ParameterNotInstantiableException, InvocationTargetException,
            IllegalAccessException {
        return invoke(factory, getDummyArguments(factory));
    }

    /**
     * Returns an object responsible for performing sanity tests against the return values of all
     * public static methods declared by {@code cls}, excluding superclasses.
     */
    public FactoryMethodReturnValueTester forAllPublicStaticMethods(Class<?> cls) {
        ImmutableList.Builder<Invokable<?, ?>> builder = ImmutableList.builder();
        for (Method method : cls.getDeclaredMethods()) {
            Invokable<?, ?> invokable = Invokable.from(method);
            invokable.setAccessible(true);
            if (invokable.isPublic() && invokable.isStatic() && !invokable.isSynthetic()) {
                builder.add(invokable);
            }
        }
        return new FactoryMethodReturnValueTester(cls, builder.build(), "public static methods");
    }

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
        Object instance = createInstance(factory, args);
        List<Object> equalArgs = generateEqualFactoryArguments(factory, null, args);
        // Each group is a List of items, each item has a list of factory args.
        final List<List<List<Object>>> argGroups = Lists.newArrayList();
        argGroups.add(ImmutableList.of(args, equalArgs));
        EqualsTester tester =
                new EqualsTester();
        tester.addEqualityGroup(instance, createInstance(factory, equalArgs));
        for (int i = 0; i < params.size(); i++) {
            List<Object> newArgs = Lists.newArrayList(args);
            Object newArg = argGenerators.get(i).generateFresh(params.get(i).getType());

            if (newArg == null || Objects.equal(args.get(i), newArg)) {
                if (params.get(i).getType().getRawType().isEnum()) {
                    continue; // Nothing better we can do if it's single-value enum
                }
                throw new ParameterHasNoDistinctValueException(null);
            }
            newArgs.set(i, newArg);
            tester.addEqualityGroup(createInstance(factory, newArgs));
            argGroups.add(ImmutableList.of(newArgs));
        }
        tester.testEquals();
    }

    /**
     * Returns dummy factory arguments that are equal to {@code args} but may be different
     * instances,
     * to be used to construct a second instance of the same equality group.
     */
    private List<Object> generateEqualFactoryArguments(
            Invokable<?, ?> factory, List<Parameter> params, List<Object> args)
            throws ParameterNotInstantiableException, FactoryMethodReturnsNullException,
            InvocationTargetException, IllegalAccessException {
        List<Object> equalArgs = Lists.newArrayList(args);
        for (int i = 0; i < args.size(); i++) {
            Parameter param = params.get(i);
            Object arg = args.get(i);
            // Use new fresh value generator because 'args' were populated with new fresh generator
            // each.
            // Two newFreshValueGenerator() instances should normally generate equal value sequence.
            Object shouldBeEqualArg = generateDummyArg(param, newFreshValueGenerator());
            if (arg != shouldBeEqualArg
                    && Objects.equal(arg, shouldBeEqualArg)
                    && hashCodeInsensitiveToArgReference(factory, args, i, shouldBeEqualArg)
                    && hashCodeInsensitiveToArgReference(
                    factory, args, i, generateDummyArg(param, newFreshValueGenerator()))) {
                // If the implementation uses identityHashCode(), referential equality is
                // probably intended. So no point in using an equal-but-different factory argument.
                // We check twice to avoid confusion caused by accidental hash collision.
                equalArgs.set(i, shouldBeEqualArg);
            }
        }
        return equalArgs;
    }

    // distinctValues is a type-safe class-values mapping, but we don't have a type-safe data
    // structure to hold the mappings.
    @SuppressWarnings({"unchecked", "rawtypes"})
    private FreshValueGenerator newFreshValueGenerator() {
        FreshValueGenerator generator =
                new FreshValueGenerator() {
                    Object interfaceMethodCalled(Class<?> interfaceType, Method method) {
                        return getDummyValue(TypeToken.of(interfaceType).method(method)
                                .getReturnType());
                    }
                };
        for (Map.Entry<Class<?>, Collection<Object>> entry : distinctValues.asMap().entrySet()) {
            generator.addSampleInstances(entry.getKey(), entry.getValue());
        }
        return generator;
    }

    private List<Object> getDummyArguments(Invokable<?, ?> invokable)
            throws ParameterNotInstantiableException {
        List<Object> args = Lists.newArrayList();
        for (com.google.common.reflect.Parameter param : invokable.getParameters()) {
            if (isNullable(null)) {
                args.add(null);
                continue;
            }
            Object defaultValue = getDummyValue(param.getType());
            if (defaultValue == null) {
                throw new ParameterNotInstantiableException(null);
            }
            args.add(defaultValue);
        }
        return args;
    }

    private <T> T getDummyValue(TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        @SuppressWarnings("unchecked") // Assume all default values are generics safe.
        T defaultValue = (T) defaultValues.getInstance(rawType);
        if (defaultValue != null) {
            return defaultValue;
        }
        @SuppressWarnings("unchecked") // ArbitraryInstances always returns generics-safe dummies.
        T value = (T) ArbitraryInstances.get(rawType);
        if (value != null) {
            return value;
        }
        if (rawType.isInterface()) {
            return new SerializableDummyProxy(this).newProxy(type);
        }
        return null;
    }

    /**
     * Thrown if the test tries to invoke a constructor or static factory method but failed because
     * the dummy value of a constructor or method parameter is unknown.
     */
    @VisibleForTesting
    static class ParameterNotInstantiableException extends Exception {
        public ParameterNotInstantiableException(Parameter parameter) {
            super(
                    "Cannot determine value for parameter "
                            + parameter
                            + " of "
                            + parameter.getName());
        }
    }

    /**
     * Thrown if the test fails to generate two distinct non-null values of a constructor or factory
     * parameter in order to test {@link Object#equals} and {@link Object#hashCode} of the declaring
     * class.
     */
    @VisibleForTesting
    static class ParameterHasNoDistinctValueException extends Exception {
        ParameterHasNoDistinctValueException(Parameter parameter) {
            super(
                    "Cannot generate distinct value for parameter "
                            + parameter
                            + " of "
                            + parameter);
        }
    }

    /**
     * Thrown if the test tries to invoke a static factory method to test instance methods but the
     * factory returned null.
     */
    @VisibleForTesting
    static class FactoryMethodReturnsNullException extends Exception {
        public FactoryMethodReturnsNullException(Invokable<?, ?> factory) {
            super(factory + " returns null and cannot be used to test instance methods.");
        }
    }

    static class NullPointerTester {

        public static boolean isPrimitiveOrNullable(com.google.common.reflect.Parameter param) {
            return true;
        }

        public <T> void setDefault(Class<T> type, T value) {
        }

        public void testConstructors(Class<?> cls, Visibility visibility) {
        }

        public void testStaticMethods(Class<?> cls, Visibility visibility) {
        }

        public void testInstanceMethods(Object instance, Visibility visibility) {
        }

        public Method[] getInstanceMethodsToTest(Class<?> c, Visibility visibility) {
            return null;
        }

        public void testAllPublicInstanceMethods(Object instance) {
        }
    }

    static class SerializableTester {
        public static void reserialize(Object instance) {
        }

        public static void reserializeAndAssert(Object instance) {
        }
    }

    static class ArbitraryInstances {
        public static <T> Object get(Class<? super T> rawType) {
            return null;
        }
    }

    /**
     * Runs sanity tests against return values of static factory methods declared by a class.
     */
    public final class FactoryMethodReturnValueTester {
        private final Set<String> packagesToTest = Sets.newHashSet();
        private final Class<?> declaringClass;
        private final ImmutableList<Invokable<?, ?>> factories;
        private final String factoryMethodsDescription;
        private Class<?> returnTypeToTest = Object.class;

        private FactoryMethodReturnValueTester(
                Class<?> declaringClass,
                ImmutableList<Invokable<?, ?>> factories,
                String factoryMethodsDescription) {
            this.declaringClass = declaringClass;
            this.factories = factories;
            this.factoryMethodsDescription = factoryMethodsDescription;
            packagesToTest.add(Reflection.getPackageName(declaringClass));
        }

        /**
         * Specifies that only the methods that are declared to return {@code returnType} or its
         * subtype
         * are tested.
         *
         * @return this tester object
         */
        public FactoryMethodReturnValueTester thatReturn(Class<?> returnType) {
            returnTypeToTest = returnType;
            return this;
        }

        /**
         * Tests null checks against the instance methods of the return values, if any.
         *
         * <p>Test fails if default value cannot be determined for a constructor or factory
         * method
         * parameter, or if the constructor or factory method throws exception.
         *
         * @return this tester
         */
        public FactoryMethodReturnValueTester testNulls() throws Exception {
            for (Invokable<?, ?> factory : getFactoriesToTest()) {
                Object instance = instantiate(factory);
                if (instance != null
                        && packagesToTest.contains(Reflection
                        .getPackageName(instance.getClass()))) {
                    try {
                        nullPointerTester.testAllPublicInstanceMethods(instance);
                    } catch (AssertionError e) {
                        AssertionError error =
                                new AssertionFailedError("Null check failed on return value of "
                                        + factory);
                        error.initCause(e);
                        throw error;
                    }
                }
            }
            return this;
        }

        /**
         * Tests {@link Object#equals} and {@link Object#hashCode} against the return values of the
         * static methods, by asserting that when equal parameters are passed to the same static
         * method,
         * the return value should also be equal; and vice versa.
         *
         * <p>Test fails if default value cannot be determined for a constructor or factory method
         * parameter, or if the constructor or factory method throws exception.
         *
         * @return this tester
         */
        public FactoryMethodReturnValueTester testEquals() throws Exception {
            for (Invokable<?, ?> factory : getFactoriesToTest()) {
                try {
                    testEqualsUsing(factory);
                } catch (FactoryMethodReturnsNullException e) {
                    // If the factory returns null, we just skip it.
                }
            }
            return this;
        }

        /**
         * Runs serialization test on the return values of the static methods.
         *
         * <p>Test fails if default value cannot be determined for a constructor or factory method
         * parameter, or if the constructor or factory method throws exception.
         *
         * @return this tester
         */
        public FactoryMethodReturnValueTester testSerializable() throws Exception {
            for (Invokable<?, ?> factory : getFactoriesToTest()) {
                Object instance = instantiate(factory);
                if (instance != null) {
                    try {
                        SerializableTester.reserialize(instance);
                    } catch (RuntimeException e) {
                        AssertionError error =
                                new AssertionFailedError("Serialization failed " +
                                        "on return value of " + factory);
                        error.initCause(e.getCause());
                        throw error;
                    }
                }
            }
            return this;
        }

        /**
         * Runs equals and serialization test on the return values.
         *
         * <p>Test fails if default value cannot be determined for a constructor or factory method
         * parameter, or if the constructor or factory method throws exception.
         *
         * @return this tester
         */
        public FactoryMethodReturnValueTester testEqualsAndSerializable() throws Exception {
            for (Invokable<?, ?> factory : getFactoriesToTest()) {
                try {
                    testEqualsUsing(factory);
                } catch (FactoryMethodReturnsNullException e) {
                    // If the factory returns null, we just skip it.
                }
                Object instance = instantiate(factory);
                if (instance != null) {
                    try {
                        SerializableTester.reserializeAndAssert(instance);
                    } catch (RuntimeException e) {
                        AssertionError error =
                                new AssertionFailedError("Serialization " +
                                        "failed on return value of " + factory);
                        error.initCause(e.getCause());
                        throw error;
                    } catch (AssertionFailedError e) {
                        AssertionError error =
                                new AssertionFailedError(
                                        "Return value of " + factory + " reserialized " +
                                                "to an unequal value");
                        error.initCause(e);
                        throw error;
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
            ImmutableList<Invokable<?, ?>> factoriesToTest = builder.build();
            Assert.assertFalse(
                    "No "
                            + factoryMethodsDescription
                            + " that return "
                            + returnTypeToTest.getName()
                            + " or subtype are found in "
                            + declaringClass
                            + ".",
                    factoriesToTest.isEmpty());
            return factoriesToTest;
        }
    }

    private final class SerializableDummyProxy extends DummyProxy implements Serializable {

        private final transient InputClassFanOutComplexityRemoveIncorrectAnnotationToken tester;

        SerializableDummyProxy(InputClassFanOutComplexityRemoveIncorrectAnnotationToken tester) {
            this.tester = new InputClassFanOutComplexityRemoveIncorrectAnnotationToken();
        }

        <R> R dummyReturnValue(TypeToken<R> returnType) {
            return tester.getDummyValue(returnType);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof SerializableDummyProxy;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        public <T> T newProxy(TypeToken<T> type) {
            return null;
        }
    }

    class FreshValueGenerator {
        public Object generateFresh(TypeToken<?> type) {
            return null;
        }

        public void addSampleInstances(Class key, Collection<Object> value) {
        }
    }

    class DummyProxy {
    }

    class EqualsTester {
        public void addEqualityGroup(Object instance, Object instance1) {
        }

        public <T> void addEqualityGroup(T instance) {
        }

        public void testEquals() {
        }
    }

    class ItemReporter {
    }

    interface Visibility {

        Visibility PACKAGE_PRIVATE = null;

    }

}

