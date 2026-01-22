///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.util.Checks;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;

public final class TestUtil {

    /**
     * The stack size used in {@link TestUtil#getResultWithLimitedResources}.
     * This value should be as small as possible. Some JVM requires this value to be
     * at least 144k.
     *
     * @see <a href="https://www.baeldung.com/jvm-configure-stack-sizes">
     *      Configuring Stack Sizes in the JVM</a>
     */
    private static final int MINIMAL_STACK_SIZE = 147456;

    private TestUtil() {
    }

    /**
     * Verifies that utils class has private constructor and invokes it to satisfy code coverage.
     *
     * @param utilClass class to test for c-tor
     * @return true if constructor is expected.
     */
    public static boolean isUtilsClassHasPrivateConstructor(final Class<?> utilClass)
            throws ReflectiveOperationException {
        final Constructor<?> constructor = utilClass.getDeclaredConstructor();
        final boolean result = Modifier.isPrivate(constructor.getModifiers());
        constructor.setAccessible(true);
        constructor.newInstance();
        return result;
    }

    /**
     * Retrieves the specified field by its name in the class or its direct super.
     *
     * @param clss the class to retrieve the field for
     * @param fieldName the name of the field to retrieve
     * @return the class' field if found
     */
    private static Field getClassDeclaredField(Class<?> clss, String fieldName) {
        return Stream.<Class<?>>iterate(clss, Objects::nonNull, Class::getSuperclass)
            .flatMap(cls -> Arrays.stream(cls.getDeclaredFields()))
            .filter(field -> fieldName.equals(field.getName()))
            .findFirst()
            .map(field -> {
                field.setAccessible(true);
                return field;
            })
            .orElseThrow(() -> {
                return new IllegalStateException(String.format(Locale.ROOT,
                        "Field '%s' not found in '%s'", fieldName, clss.getCanonicalName()));
            });
    }

    /**
     * Retrieves the specified method by its name in the class or its direct super.
     *
     * @param clss the class to retrieve the method for
     * @param methodName the name of the method to retrieve
     * @param parameters the expected number of parameters
     * @return the class' method
     */
    private static Method getClassDeclaredMethod(Class<?> clss,
                                                 String methodName,
                                                 int parameters) {
        final Stream<Method> methods = Stream.<Class<?>>iterate(clss, Class::getSuperclass)
                .flatMap(cls -> Arrays.stream(cls.getDeclaredMethods()))
                .filter(method -> {
                    return methodName.equals(method.getName());
                });

        final Supplier<String> exceptionMessage = () -> {
            return String.format(Locale.ROOT, "Method '%s' with %d parameters not found in '%s'",
                    methodName, parameters, clss.getCanonicalName());
        };

        return getMatchingExecutable(methods, parameters, exceptionMessage);
    }

    /**
     * Retrieves the specified executable from a class.
     *
     * @param <T> the type of executable to search
     * @param execs The stream of executables to search
     * @param parameters the expected number of parameters
     * @param exceptionMessage the exception message to use if executable is not found
     * @return the matching executable
     */
    private static <T extends java.lang.reflect.Executable> T getMatchingExecutable(
            Stream<T> execs, int parameters, Supplier<String> exceptionMessage) {
        return execs.filter(method -> {
            return parameters == method.getParameterCount();
        })
        .findFirst()
        .map(method -> {
            method.setAccessible(true);
            return method;
        })
        .orElseThrow(() -> {
            return new IllegalStateException(exceptionMessage.get());
        });
    }

    /**
     * Checks if stateful field is cleared during {@link AbstractCheck#beginTree} in check.
     *
     * @param check      check object which field is to be verified
     * @param astToVisit ast to pass into check methods
     * @param fieldName  name of the field to be checked
     * @param isClear    function for checking field state
     * @return {@code true} if state of the field is cleared
     */
    public static boolean isStatefulFieldClearedDuringBeginTree(AbstractCheck check,
                                                                DetailAST astToVisit,
                                                                String fieldName,
                                                                Predicate<Object> isClear) {
        check.beginTree(astToVisit);
        check.visitToken(astToVisit);
        check.beginTree(null);
        return isClear.test(getInternalState(check, fieldName, Object.class));
    }

    /**
     * Checks if stateful field is cleared during {@link AbstractAutomaticBean}'s finishLocalSetup.
     *
     * @param filter filter object which field is to be verified
     * @param event event to pass into filter methods
     * @param fieldName name of the field to be checked
     * @param isClear function for checking field state
     * @return {@code true} if state of the field is cleared
     * @throws Exception if there was an error.
     */
    public static boolean isStatefulFieldClearedDuringLocalSetup(
            TreeWalkerFilter filter, TreeWalkerAuditEvent event,
            String fieldName, Predicate<Object> isClear) throws Exception {
        filter.accept(event);
        invokeVoidMethod(filter, "finishLocalSetup");
        final Field resultField = getClassDeclaredField(filter.getClass(), fieldName);
        return isClear.test(resultField.get(filter));
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     *
     * @return the default PackageObjectFactory.
     */
    public static PackageObjectFactory getPackageObjectFactory() throws CheckstyleException {
        final ClassLoader cl = TestUtil.class.getClassLoader();
        final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
        return new PackageObjectFactory(packageNames, cl);
    }

    /**
     * Finds node of specified type among root children, siblings, siblings children
     * on any deep level.
     *
     * @param root      DetailAST
     * @param predicate predicate
     * @return {@link Optional} of {@link DetailAST} node which matches the predicate.
     */
    public static Optional<DetailAST> findTokenInAstByPredicate(DetailAST root,
                                                                Predicate<DetailAST> predicate) {
        DetailAST curNode = root;
        while (!predicate.test(curNode)) {
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }

            if (curNode == toVisit || curNode == root.getParent()) {
                curNode = null;
                break;
            }

            curNode = toVisit;
        }
        return Optional.ofNullable(curNode);
    }

    /**
     * Returns the JDK version as a number that is easy to compare.
     *
     * <p>
     * For JDK "1.8" it will be 8; for JDK "11" it will be 11.
     * </p>
     *
     * @return JDK version as integer
     */
    public static int getJdkVersion() {
        String version = System.getProperty("java.specification.version");
        if (version.startsWith("1.")) {
            version = version.substring(2);
        }
        return Integer.parseInt(version);
    }

    /**
     * Adjusts the expected number of flushes for tests that call {@link OutputStream#close} method.
     *
     * <p>
     * After <a href="https://bugs.openjdk.java.net/browse/JDK-8220477">JDK-8220477</a>
     * there is one additional flush from {@code sun.nio.cs.StreamEncoder#implClose}.
     * </p>
     *
     * @param flushCount flush count to adjust
     * @return adjusted flush count
     */
    public static int adjustFlushCountForOutputStreamClose(int flushCount) {
        int result = flushCount;
        if (getJdkVersion() >= 13) {
            ++result;
        }
        return result;
    }

    /**
     * Runs a given task with limited stack size and time duration, then
     * returns the result. See AbstractModuleTestSupport#verifyWithLimitedResources
     * for an example of how to use this method when task does not return a result, i.e.
     * the given method's return type is {@code void}.
     *
     * @param callable the task to execute
     * @param <V> return type of task - {@code Void} if task does not return result
     * @return result
     * @throws Exception if getting result fails
     */
    public static <V> V getResultWithLimitedResources(Callable<V> callable) throws Exception {
        final FutureTask<V> futureTask = new FutureTask<>(callable);
        final Thread thread = new Thread(null, futureTask,
                "LimitedStackSizeThread", MINIMAL_STACK_SIZE);
        thread.start();
        return futureTask.get(10, TimeUnit.SECONDS);
    }

    /**
     * Reads the value of a field using reflection. This method will traverse the
     * super class hierarchy until a field with name {@code fieldName} is found.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     */
    public static <T> T getInternalState(Object instance, String fieldName, Class<T> clazz) {
        try {
            final Field field = getClassDeclaredField(instance.getClass(), fieldName);
            return clazz.cast(field.get(instance));
        }
        catch (ReflectiveOperationException exc) {
            final String message = String.format(Locale.ROOT,
                    "Failed to get field '%s' for instance of class '%s'",
                    fieldName, instance.getClass().getSimpleName());
            throw new IllegalStateException(message, exc);
        }
    }

    /**
     * Helper method for casting collection type Map.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Map<String, String> getInternalStateMap(Object instance, String fieldName) {
        return getInternalState(instance, fieldName, Map.class);
    }

    /**
     * Helper method for casting collection type Map.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Map<Integer, List<TextBlock>> getInternalStateMapIntegerList(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, Map.class);
    }

    /**
     * Helper method for casting collection type List.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static List<AuditListener> getInternalStateListAuditListener(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, List.class);
    }

    /**
     * Helper method for casting collection type List.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static List<Pattern> getInternalStateListPattern(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, List.class);
    }

    /**
     * Helper method for casting collection type List.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static List<Comparable<Object>> getInternalStateListComparable(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, List.class);
    }

    /**
     * Helper method for casting to Collection.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Collection<Checks> getInternalStateCollectionChecks(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, Collection.class);
    }

    /**
     * Helper method for casting to collection type Set.
     *
     * @param instance the instance to read
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Set<TreeWalkerFilter> getInternalStateSetTreeWalkerFilter(
            Object instance, String fieldName) {
        return getInternalState(instance, fieldName, Set.class);
    }

    /**
     * Reads the value of a static field using reflection. This method will traverse the
     * super class hierarchy until a field with name {@code fieldName} is found.
     *
     * @param clss the class of the field
     * @param fieldName the name of the field
     * @param clazz the expected type of the field value, used for type-safe casting
     * @throws RuntimeException if the field  can't be read
     */
    public static <T> T getInternalStaticState(Class<?> clss, String fieldName, Class<T> clazz) {
        try {
            final Field field = getClassDeclaredField(clss, fieldName);
            return clazz.cast(field.get(null));
        }
        catch (ReflectiveOperationException exc) {
            final String message = String.format(Locale.ROOT,
                    "Failed to get static field '%s' for class '%s'",
                    fieldName, clss);
            throw new IllegalStateException(message, exc);
        }
    }

    /**
     * Helper method for casting to collection type Map.
     *
     * @param clss the class of the field
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Map<String, String> getInternalStaticStateMap(Class<?> clss, String fieldName) {
        return getInternalStaticState(clss, fieldName, Map.class);
    }

    /**
     * Helper method for casting to collection type Map.
     *
     * @param clss the class of the field
     * @param fieldName the name of the field
     * @throws RuntimeException if the field  can't be read
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static ThreadLocal<List<Object>> getInternalStaticStateThreadLocal(
            Class<?> clss, String fieldName) {
        return getInternalStaticState(clss, fieldName, ThreadLocal.class);
    }

    /**
     * Writes the value of a field using reflection. This method will traverse the
     * super class hierarchy until a field with name {@code fieldName} is found.
     *
     * @param instance the instance whose field to modify
     * @param fieldName the name of the field
     * @param value the new value of the field
     * @throws RuntimeException if the field  can't be changed
     */
    public static void setInternalState(Object instance, String fieldName, Object value) {
        try {
            final Field field = getClassDeclaredField(instance.getClass(), fieldName);
            field.set(instance, value);
        }
        catch (ReflectiveOperationException exc) {
            final String message = String.format(Locale.ROOT,
                    "Failed to set field '%s' for instance of class '%s'",
                    fieldName, instance.getClass().getSimpleName());
            throw new IllegalStateException(message, exc);
        }
    }

    /**
     * Invokes a private method for an instance.
     *
     * @param instance the instance whose method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param resultClazz used for cast of result
     * @param arguments the optional arguments
     * @param <T> the type of the result
     * @return the method's result
     * @throws ReflectiveOperationException if the method invocation failed
     */
    public static <T> T invokeMethod(Object instance, String methodToExecute,
                                     Class<T> resultClazz, Object... arguments)
            throws ReflectiveOperationException {
        final Class<?> ownerClass = instance.getClass();
        final Method method = getClassDeclaredMethod(ownerClass, methodToExecute, arguments.length);
        return resultClazz.cast(method.invoke(instance, arguments));
    }

    /**
     * Helper method to invoke private method for an instance with cast to Object.
     *
     * @param instance the instance whose method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param arguments the optional arguments
     * @throws ReflectiveOperationException if the method invocation failed
     */
    public static void invokeVoidMethod(Object instance,
                                        String methodToExecute, Object... arguments)
            throws ReflectiveOperationException {
        invokeMethod(instance, methodToExecute, Object.class, arguments);
    }

    /**
     * Helper method to invoke private method for an instance with cast to Set.
     *
     * @param instance the instance whose method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param arguments the optional arguments
     * @return the method's result
     * @throws ReflectiveOperationException if the method invocation failed
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static Set<String> invokeMethodSet(Object instance, String methodToExecute,
                                       Object... arguments) throws ReflectiveOperationException {
        return (Set<String>) invokeMethod(instance, methodToExecute, Set.class, arguments);
    }

    /**
     * Invokes a static private method for a class.
     *
     * @param ownerClass the class whose static method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param resultClass used for cast of result
     * @param arguments the optional arguments
     * @param <T> the type of the result
     * @return the method's result
     * @throws ReflectiveOperationException if the method invocation failed
     */
    public static <T> T invokeStaticMethod(Class<?> ownerClass,
            String methodToExecute, Class<T> resultClass, Object... arguments)
            throws ReflectiveOperationException {
        final Method method = getClassDeclaredMethod(ownerClass, methodToExecute, arguments.length);
        return resultClass.cast(method.invoke(null, arguments));
    }

    /**
     * Helper method to invoke static private method for an instance with cast to Object.
     *
     * @param ownerClass the class whose static method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param arguments the optional arguments
     * @throws ReflectiveOperationException if the method invocation failed
     */
    public static void invokeVoidStaticMethod(Class<?> ownerClass,
                                              String methodToExecute, Object... arguments)
            throws ReflectiveOperationException {
        invokeStaticMethod(ownerClass, methodToExecute, Object.class, arguments);
    }

    /**
     * Helper method to invoke static private method for an instance with cast to List.
     *
     * @param ownerClass the class whose static method to invoke
     * @param methodToExecute the name of the method to invoke
     * @param arguments the optional arguments
     * @return the method's result
     * @throws ReflectiveOperationException if the method invocation failed
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static List<File> invokeStaticMethodList(Class<?> ownerClass,
                                                    String methodToExecute, Object... arguments)
            throws ReflectiveOperationException {
        return (List<File>) invokeStaticMethod(ownerClass, methodToExecute, List.class, arguments);
    }

    /**
     * Instantiates an object of the given class with the given arguments,
     * even if the constructor is private.
     *
     * @param clss The class to instantiate
     * @param arguments The arguments to pass to the constructor
     * @param <T> the type of the object to instantiate
     * @return  The instantiated object
     * @throws ReflectiveOperationException if the constructor invocation failed
     */
    @SuppressWarnings("unchecked")
    public static <T> T instantiate(Class<T> clss, Object... arguments)
            throws ReflectiveOperationException {

        final Stream<Constructor<T>> ctors =
                Arrays.stream(clss.getDeclaredConstructors()).map(Constructor.class::cast);

        final Supplier<String> exceptionMessage = () -> {
            return String.format(Locale.ROOT, "Constructor with %d parameters not found in '%s'",
                    arguments.length, clss.getCanonicalName());
        };

        final Constructor<T> constructor =
                getMatchingExecutable(ctors, arguments.length, exceptionMessage);
        constructor.setAccessible(true);

        return constructor.newInstance(arguments);
    }

    /**
     * Returns the inner class type by its name.
     *
     * @param declaringClass the class in which the inner class is declared
     * @param name the unqualified name (simple name) of the inner class
     * @return the inner class type
     * @throws ClassNotFoundException if the class not found
     * @noinspection unchecked
     * @noinspectionreason unchecked - unchecked cast is ok on test code
     */
    public static <T> Class<T> getInnerClassType(Class<?> declaringClass, String name)
            throws ClassNotFoundException {
        return (Class<T>) Class.forName(declaringClass.getName() + "$" + name);
    }

    /**
     * Executes the provided executable and expects it to throw an exception of the specified type.
     *
     * @param expectedType the class of the expected exception type.
     * @param executable the executable to be executed
     * @param message the message to be used in case of assertion failure.
     * @return the expected exception thrown by the executable.
     */
    public static <T extends Throwable> T getExpectedThrowable(Class<T> expectedType,
                                                               Executable executable,
                                                               String message) {
        return assertThrows(expectedType, executable, message);
    }

    /**
     *  Executes the provided executable and expects it to throw an exception of the specified type.
     *
     * @param expectedType the class of the expected exception type.
     * @param executable the executable to be executed
     * @return the expected exception thrown by the executable.
     */
    public static <T extends Throwable> T getExpectedThrowable(Class<T> expectedType,
                                                               Executable executable) {
        return assertThrows(expectedType, executable);
    }

}
