///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.BASE_PACKAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.CHECK_SUFFIX;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.ModuleLoadOption.SEARCH_REGISTERED_PACKAGES;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.ModuleLoadOption.TRY_IN_ALL_REGISTERED_PACKAGES;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_LOADER_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_PACKAGE_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.PACKAGE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.STRING_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 *
 */
public class PackageObjectFactoryTest {

    private static Locale defaultLocale;
    private final PackageObjectFactory factory = new PackageObjectFactory(
            BASE_PACKAGE, Thread.currentThread().getContextClassLoader());

    @BeforeClass
    public static void setupLocale() {
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }

    @AfterClass
    public static void restoreLocale() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testCtorNullLoaderException1() {
        try {
            final Object test = new PackageObjectFactory(new HashSet<>(), null);
            assertWithMessage("Exception is expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(NULL_LOADER_MESSAGE);
        }
    }

    @Test
    public void testCtorNullLoaderException2() {
        try {
            final Object test = new PackageObjectFactory("test", null);
            assertWithMessage("Exception is expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(NULL_LOADER_MESSAGE);
        }
    }

    @Test
    public void testCtorNullPackageException1() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory(Collections.singleton(null), classLoader);
            assertWithMessage("Exception is expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(NULL_PACKAGE_MESSAGE);
        }
    }

    @Test
    public void testCtorNullPackageException2() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory((String) null, classLoader);
            assertWithMessage("Exception is expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(NULL_PACKAGE_MESSAGE);
        }
    }

    @Test
    public void testCtorNullPackageException3() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory(Collections.singleton(null), classLoader,
                    TRY_IN_ALL_REGISTERED_PACKAGES);
            assertWithMessage("Exception is expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(NULL_PACKAGE_MESSAGE);
        }
    }

    @Test
    public void testMakeObjectFromName()
            throws CheckstyleException {
        final Checker checker =
            (Checker) factory.createModule(
                        "com.puppycrawl.tools.checkstyle.Checker");
        assertWithMessage("Checker should not be null when creating module from name")
            .that(checker)
            .isNotNull();
    }

    @Test
    public void testMakeCheckFromName() {
        final String name = "com.puppycrawl.tools.checkstyle.checks.naming.ConstantName";
        try {
            factory.createModule(name);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, factory.getClass(),
                    UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE, name, null);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(exceptionMessage.getMessage());
        }
    }

    @Test
    public void testCreateModuleWithNonExistName() {
        final String[] names = {"NonExistClassOne", "NonExistClassTwo", };
        for (String name : names) {
            try {
                factory.createModule(name);
                assertWithMessage("Exception is expected").fail();
            }
            catch (CheckstyleException ex) {
                final String attemptedNames = BASE_PACKAGE + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + name + CHECK_SUFFIX + STRING_SEPARATOR
                    + BASE_PACKAGE + PACKAGE_SEPARATOR + name + CHECK_SUFFIX;
                final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, factory.getClass(),
                    UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE, name, attemptedNames);
                assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo(exceptionMessage.getMessage());
            }
        }
    }

    @Test
    public void testCreateObjectFromMap() throws Exception {
        final String moduleName = "Foo";
        final String name = moduleName + CHECK_SUFFIX;
        final String packageName = BASE_PACKAGE + ".packageobjectfactory.bar";
        final String fullName = packageName + PACKAGE_SEPARATOR + name;
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory =
                new PackageObjectFactory(packageName, classLoader);
        final Object instance1 = objectFactory.createModule(name);
        assertWithMessage("Invalid canonical name")
            .that(instance1.getClass().getCanonicalName())
            .isEqualTo(fullName);
        final Object instance2 = objectFactory.createModule(moduleName);
        assertWithMessage("Invalid canonical name")
            .that(instance2.getClass().getCanonicalName())
            .isEqualTo(fullName);
    }

    @Test
    public void testCreateStandardModuleObjectFromMap() throws Exception {
        final String moduleName = "TreeWalker";
        final String packageName = BASE_PACKAGE + ".packageobjectfactory.bar";
        final String fullName = BASE_PACKAGE + PACKAGE_SEPARATOR + moduleName;
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory =
                new PackageObjectFactory(packageName, classLoader);
        final Object instance = objectFactory.createModule(moduleName);
        assertWithMessage("Invalid canonical name")
            .that(instance.getClass().getCanonicalName())
            .isEqualTo(fullName);
    }

    @Test
    public void testCreateStandardCheckModuleObjectFromMap() throws Exception {
        final String moduleName = "TypeName";
        final String packageName = BASE_PACKAGE + ".packageobjectfactory.bar";
        final String fullName = BASE_PACKAGE + PACKAGE_SEPARATOR + "checks" + PACKAGE_SEPARATOR
            + "naming" + PACKAGE_SEPARATOR + moduleName + CHECK_SUFFIX;
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory =
                new PackageObjectFactory(packageName, classLoader);
        final Object instance = objectFactory.createModule(moduleName);
        assertWithMessage("Invalid canonical name")
            .that(instance.getClass().getCanonicalName())
            .isEqualTo(fullName);
    }

    @Test
    public void testCreateObjectFromFullModuleNamesWithAmbiguousException() {
        final String barPackage = BASE_PACKAGE + ".packageobjectfactory.bar";
        final String fooPackage = BASE_PACKAGE + ".packageobjectfactory.foo";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory = new PackageObjectFactory(
                new LinkedHashSet<>(Arrays.asList(barPackage, fooPackage)), classLoader);
        final String name = "FooCheck";
        try {
            objectFactory.createModule(name);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final String optionalNames = barPackage + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + fooPackage + PACKAGE_SEPARATOR + name;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, getClass(),
                    AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE, name, optionalNames);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(exceptionMessage.getMessage());
        }
    }

    @Test
    public void testCreateObjectFromFullModuleNamesWithCantInstantiateException() {
        final String package1 = BASE_PACKAGE + ".wrong1";
        final String package2 = BASE_PACKAGE + ".wrong2";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory = new PackageObjectFactory(
                new LinkedHashSet<>(Arrays.asList(package1, package2)), classLoader);
        final String name = "FooCheck";
        final String checkName = name + CHECK_SUFFIX;
        try {
            objectFactory.createModule(name);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final String attemptedNames = package1 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + checkName + STRING_SEPARATOR
                    + package1 + PACKAGE_SEPARATOR + checkName + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + checkName;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, getClass(),
                    UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE, name, attemptedNames);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(exceptionMessage.getMessage());
        }
    }

    @Test
    public void testCreateObjectFromFullModuleNamesWithExceptionByBruteForce() {
        final String package1 = BASE_PACKAGE + ".wrong1";
        final String package2 = BASE_PACKAGE + ".wrong2";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory = new PackageObjectFactory(
                new LinkedHashSet<>(Arrays.asList(package1, package2)), classLoader,
                TRY_IN_ALL_REGISTERED_PACKAGES);
        final String name = "FooCheck";
        final String checkName = name + CHECK_SUFFIX;
        try {
            objectFactory.createModule(name);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final String attemptedNames = package1 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + checkName + STRING_SEPARATOR
                    + package1 + PACKAGE_SEPARATOR + checkName + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + checkName;
            final Violation exceptionMessage = new Violation(1,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, getClass(), null);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(exceptionMessage.getViolation());
        }
    }

    @Test
    public void testCreateObjectByBruteForce() throws Exception {
        final String className = "Checker";
        final Method createModuleByBruteForce = PackageObjectFactory.class.getDeclaredMethod(
                "createModuleByTryInEachPackage", String.class);
        createModuleByBruteForce.setAccessible(true);
        final Checker checker = (Checker) createModuleByBruteForce.invoke(factory, className);
        assertWithMessage("Checker should not be null when creating module from name")
            .that(checker)
            .isNotNull();
    }

    @Test
    public void testCreateCheckByBruteForce() throws Exception {
        final String checkName = "AnnotationLocation";
        final Method createModuleByBruteForce = PackageObjectFactory.class.getDeclaredMethod(
                "createModuleByTryInEachPackage", String.class);
        final PackageObjectFactory packageObjectFactory = new PackageObjectFactory(
            new HashSet<>(Arrays.asList(BASE_PACKAGE, BASE_PACKAGE + ".checks.annotation")),
            Thread.currentThread().getContextClassLoader(), TRY_IN_ALL_REGISTERED_PACKAGES);
        createModuleByBruteForce.setAccessible(true);
        final AnnotationLocationCheck check = (AnnotationLocationCheck) createModuleByBruteForce
                .invoke(packageObjectFactory, checkName);
        assertWithMessage("Check should not be null when creating module from name")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testCreateCheckWithPartialPackageNameByBruteForce() throws Exception {
        final String checkName = "checks.annotation.AnnotationLocation";
        final PackageObjectFactory packageObjectFactory = new PackageObjectFactory(
            new HashSet<>(Collections.singletonList(BASE_PACKAGE)),
            Thread.currentThread().getContextClassLoader(), TRY_IN_ALL_REGISTERED_PACKAGES);
        final AnnotationLocationCheck check = (AnnotationLocationCheck) packageObjectFactory
                .createModule(checkName);
        assertWithMessage("Check should not be null when creating module from name")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testJoinPackageNamesWithClassName() throws Exception {
        final Class<PackageObjectFactory> clazz = PackageObjectFactory.class;
        final Method method =
            clazz.getDeclaredMethod("joinPackageNamesWithClassName", String.class, Set.class);
        method.setAccessible(true);
        final Set<String> packages = Collections.singleton("test");
        final String className = "SomeClass";
        final String actual =
            String.valueOf(method.invoke(PackageObjectFactory.class, className, packages));
        assertWithMessage("Invalid class name")
            .that(actual)
            .isEqualTo("test." + className);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNameToFullModuleNameMap() throws Exception {
        final Set<Class<?>> classes = CheckUtil.getCheckstyleModules();
        final Class<PackageObjectFactory> packageObjectFactoryClass = PackageObjectFactory.class;
        final Field field = packageObjectFactoryClass.getDeclaredField("NAME_TO_FULL_MODULE_NAME");
        field.setAccessible(true);
        final Collection<String> canonicalNames = ((Map<String, String>) field.get(null)).values();

        final Optional<Class<?>> optional1 = classes.stream()
                .filter(clazz -> {
                    return !canonicalNames.contains(clazz.getCanonicalName())
                            && !Definitions.INTERNAL_MODULES.contains(clazz.getName());
                }).findFirst();
        assertWithMessage("Invalid canonical name: %s", optional1)
                .that(optional1.isPresent())
                .isFalse();
        final Optional<String> optional2 = canonicalNames.stream().filter(canonicalName -> {
            return classes.stream().map(Class::getCanonicalName)
                    .noneMatch(clssCanonicalName -> clssCanonicalName.equals(canonicalName));
        }).findFirst();
        assertWithMessage("Invalid class: %s", optional2)
                .that(optional2.isPresent())
                .isFalse();
    }

    @Test
    public void testConstructorFailure() {
        try {
            factory.createModule(FailConstructorFileSet.class.getName());
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unable to instantiate com.puppycrawl.tools.checkstyle."
                    + "PackageObjectFactoryTest$FailConstructorFileSet");
            assertWithMessage("Invalid exception cause class")
                .that(ex.getCause().getClass().getSimpleName())
                .isEqualTo("IllegalAccessException");
        }
    }

    @Test
    public void testGetShortFromFullModuleNames() {
        final String fullName =
                "com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck";

        assertWithMessage("Invalid simple check name")
            .that(PackageObjectFactory.getShortFromFullModuleNames(fullName))
            .isEqualTo("DefaultComesLastCheck");
    }

    @Test
    public void testGetShortFromFullModuleNamesThirdParty() {
        final String fullName =
                "java.util.stream.Collectors";

        assertWithMessage("Invalid simple check name")
            .that(PackageObjectFactory.getShortFromFullModuleNames(fullName))
            .isEqualTo(fullName);
    }

    /**
     * This method is for testing the case of an exception caught inside
     * {@code PackageObjectFactory.generateThirdPartyNameToFullModuleName}, a private method used
     * to initialize private field {@code PackageObjectFactory.thirdPartyNameToFullModuleNames}.
     * Since the method and the field both are private, the {@link TestUtil} is required to ensure
     * that the field is changed. Also, the expected exception should be thrown from the static
     * method {@link ModuleReflectionUtil#isCheckstyleModule}, so {@link Mockito#mockStatic}
     * is required to mock this exception.
     *
     * @throws Exception when the code tested throws an exception
     */
    @Test
    public void testGenerateThirdPartyNameToFullModuleNameWithException() throws Exception {
        final String name = "String";
        final String packageName = "java.lang";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Set<String> packages = Collections.singleton(packageName);
        final PackageObjectFactory objectFactory = new PackageObjectFactory(packages, classLoader,
                TRY_IN_ALL_REGISTERED_PACKAGES);

        try (MockedStatic<ModuleReflectionUtil> utilities =
                     mockStatic(ModuleReflectionUtil.class)) {
            utilities.when(() -> ModuleReflectionUtil.getCheckstyleModules(packages, classLoader))
                    .thenThrow(new IOException("mock exception"));

            final String internalFieldName = "thirdPartyNameToFullModuleNames";
            final Map<String, String> nullMap = TestUtil.getInternalState(objectFactory,
                    internalFieldName);
            assertWithMessage("Expected uninitialized field")
                    .that(nullMap)
                    .isNull();

            final Object instance = objectFactory.createModule(name);
            assertWithMessage("Expected empty string")
                    .that(instance)
                    .isEqualTo("");

            final Map<String, String> emptyMap = TestUtil.getInternalState(objectFactory,
                    internalFieldName);
            assertWithMessage("Expected empty map")
                    .that(emptyMap)
                    .isEmpty();
        }
    }

    @Test
    public void testCreateObjectWithNameContainingPackageSeparator() throws Exception {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final Set<String> packages = Collections.singleton(BASE_PACKAGE);
        final PackageObjectFactory objectFactory =
            new PackageObjectFactory(packages, classLoader, TRY_IN_ALL_REGISTERED_PACKAGES);

        final Object object = objectFactory.createModule(MockClass.class.getName());
        assertWithMessage("Object should be an instance of MockClass")
            .that(object)
            .isInstanceOf(MockClass.class);
    }

    /**
     * This test case is designed to verify the behavior of the PackageObjectFactory's
     * createModule method when it is provided with a fully qualified class name
     * (containing a package separator).
     * It ensures that ModuleReflectionUtil.getCheckstyleModules is not executed in this case.
     */
    @Test
    public void testCreateObjectWithNameContainingPackageSeparatorWithoutSearch() throws Exception {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final Set<String> packages = Collections.singleton(BASE_PACKAGE);
        final PackageObjectFactory objectFactory =
            new PackageObjectFactory(packages, classLoader, TRY_IN_ALL_REGISTERED_PACKAGES);

        try (MockedStatic<ModuleReflectionUtil> utilities =
                     mockStatic(ModuleReflectionUtil.class)) {
            utilities.when(() -> ModuleReflectionUtil.getCheckstyleModules(packages, classLoader))
                    .thenThrow(new IllegalStateException("creation of objects by fully qualified"
                            + " class names should not result in search of modules in classpath"));

            final String fullyQualifiedName = MockClass.class.getName();
            assertWithMessage("class name is not in expected format")
                    .that(fullyQualifiedName).contains(".");
            final Object object = objectFactory.createModule(fullyQualifiedName);
            assertWithMessage("Object should be an instance of MockClass")
                    .that(object)
                    .isInstanceOf(MockClass.class);
        }
    }

    @Test
    public void testCreateModuleWithTryInAllRegisteredPackages() {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final Set<String> packages = Collections.singleton(BASE_PACKAGE);
        final PackageObjectFactory objectFactory =
            new PackageObjectFactory(packages, classLoader, SEARCH_REGISTERED_PACKAGES);
        final CheckstyleException ex = assertThrows(CheckstyleException.class, () -> {
            objectFactory.createModule("PackageObjectFactoryTest$MockClass");
        });

        assertWithMessage("Invalid exception message")
            .that(ex.getMessage())
            .startsWith(
                "Unable to instantiate 'PackageObjectFactoryTest$MockClass' class, it is also "
                    + "not possible to instantiate it as "
                    + "com.puppycrawl.tools.checkstyle.PackageObjectFactoryTest$MockClass, "
                    + "PackageObjectFactoryTest$MockClassCheck, "
                    + "com.puppycrawl.tools.checkstyle.PackageObjectFactoryTest$MockClassCheck"
            );

    }

    @Test
    public void testExceptionMessage() {
        final String barPackage = BASE_PACKAGE + ".packageobjectfactory.bar";
        final String fooPackage = BASE_PACKAGE + ".packageobjectfactory.foo";
        final String zooPackage = BASE_PACKAGE + ".packageobjectfactory.zoo";
        final String abcPackage = BASE_PACKAGE + ".packageobjectfactory.abc";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final PackageObjectFactory objectFactory = new PackageObjectFactory(
                new HashSet<>(Arrays.asList(abcPackage, barPackage,
                        fooPackage, zooPackage)), classLoader);
        final String name = "FooCheck";
        try {
            objectFactory.createModule(name);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final String optionalNames = abcPackage + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + barPackage + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + fooPackage + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + zooPackage + PACKAGE_SEPARATOR + name;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, getClass(),
                    AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE, name, optionalNames);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(exceptionMessage.getMessage());
        }
    }

    private static final class FailConstructorFileSet extends AbstractFileSetCheck {

        private FailConstructorFileSet() {
            throw new IllegalArgumentException("Test");
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // not used
        }

    }

    public static class MockClass {
        // Mock class for testing purposes.
    }
}
