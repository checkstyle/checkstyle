////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.BASE_PACKAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.CHECK_SUFFIX;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.ModuleLoadOption.TRY_IN_ALL_REGISTERED_PACKAGES;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_LOADER_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_PACKAGE_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.PACKAGE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.STRING_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 *
 */
public class PackageObjectFactoryTest {

    private final PackageObjectFactory factory = new PackageObjectFactory(
            BASE_PACKAGE, Thread.currentThread().getContextClassLoader());

    @Test
    public void testCtorNullLoaderException1() {
        try {
            final Object test = new PackageObjectFactory(new HashSet<>(), null);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_LOADER_MESSAGE, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testCtorNullLoaderException2() {
        try {
            final Object test = new PackageObjectFactory("test", null);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_LOADER_MESSAGE, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testCtorNullPackageException1() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory(Collections.singleton(null), classLoader);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_PACKAGE_MESSAGE, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testCtorNullPackageException2() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory((String) null, classLoader);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_PACKAGE_MESSAGE, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testCtorNullPackageException3() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Object test = new PackageObjectFactory(Collections.singleton(null), classLoader,
                    TRY_IN_ALL_REGISTERED_PACKAGES);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_PACKAGE_MESSAGE, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testMakeObjectFromName()
            throws CheckstyleException {
        final Checker checker =
            (Checker) factory.createModule(
                        "com.puppycrawl.tools.checkstyle.Checker");
        assertNotNull(checker, "Checker should not be null when creating module from name");
    }

    @Test
    public void testMakeCheckFromName() {
        final String name = "com.puppycrawl.tools.checkstyle.checks.naming.ConstantName";
        try {
            factory.createModule(name);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final LocalizedMessage exceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, null}, null, factory.getClass(), null);
            assertEquals(exceptionMessage.getMessage(), ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testCreateModuleWithNonExistName() {
        final String[] names = {"NonExistClassOne", "NonExistClassTwo", };
        for (String name : names) {
            try {
                factory.createModule(name);
                fail("Exception is expected");
            }
            catch (CheckstyleException ex) {
                final String attemptedNames = BASE_PACKAGE + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + name + CHECK_SUFFIX + STRING_SEPARATOR
                    + BASE_PACKAGE + PACKAGE_SEPARATOR + name + CHECK_SUFFIX;
                final LocalizedMessage exceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, factory.getClass(), null);
                assertEquals(exceptionMessage.getMessage(), ex.getMessage(),
                        "Invalid exception message");
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
        assertEquals(fullName, instance1.getClass().getCanonicalName(), "Invalid canonical name");
        final Object instance2 = objectFactory.createModule(moduleName);
        assertEquals(fullName, instance2.getClass().getCanonicalName(), "Invalid canonical name");
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
        assertEquals(fullName, instance.getClass().getCanonicalName(), "Invalid canonical name");
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
        assertEquals(fullName, instance.getClass().getCanonicalName(), "Invalid canonical name");
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
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final String optionalNames = barPackage + PACKAGE_SEPARATOR + name
                    + STRING_SEPARATOR + fooPackage + PACKAGE_SEPARATOR + name;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE,
                    new String[] {name, optionalNames}, null, getClass(), null);
            assertEquals(
                    exceptionMessage.getMessage(), ex.getMessage(), "Invalid exception message");
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
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final String attemptedNames = package1 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + checkName + STRING_SEPARATOR
                    + package1 + PACKAGE_SEPARATOR + checkName + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + checkName;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, getClass(), null);
            assertEquals(
                    exceptionMessage.getMessage(), ex.getMessage(), "Invalid exception message");
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
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final String attemptedNames = package1 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + checkName + STRING_SEPARATOR
                    + package1 + PACKAGE_SEPARATOR + checkName + STRING_SEPARATOR
                    + package2 + PACKAGE_SEPARATOR + checkName;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, getClass(), null);
            assertEquals(
                    exceptionMessage.getMessage(), ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testCreateObjectByBruteForce() throws Exception {
        final String className = "Checker";
        final Method createModuleByBruteForce = PackageObjectFactory.class.getDeclaredMethod(
                "createModuleByTryInEachPackage", String.class);
        createModuleByBruteForce.setAccessible(true);
        final Checker checker = (Checker) createModuleByBruteForce.invoke(factory, className);
        assertNotNull(checker, "Checker should not be null when creating module from name");
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
        assertNotNull(check, "Check should not be null when creating module from name");
    }

    @Test
    public void testCreateCheckWithPartialPackageNameByBruteForce() throws Exception {
        final String checkName = "checks.annotation.AnnotationLocation";
        final PackageObjectFactory packageObjectFactory = new PackageObjectFactory(
            new HashSet<>(Collections.singletonList(BASE_PACKAGE)),
            Thread.currentThread().getContextClassLoader(), TRY_IN_ALL_REGISTERED_PACKAGES);
        final AnnotationLocationCheck check = (AnnotationLocationCheck) packageObjectFactory
                .createModule(checkName);
        assertNotNull(check, "Check should not be null when creating module from name");
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
        assertEquals("test." + className, actual, "Invalid class name");
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
                .filter(clazz -> !canonicalNames.contains(clazz.getCanonicalName())).findFirst();
        if (optional1.isPresent()) {
            fail("Invalid canonical name: " + optional1.get());
        }
        final Optional<String> optional2 = canonicalNames.stream().filter(canonicalName -> {
            return classes.stream().map(Class::getCanonicalName)
                    .noneMatch(clssCanonicalName -> clssCanonicalName.equals(canonicalName));
        }).findFirst();
        if (optional2.isPresent()) {
            fail("Invalid class: " + optional2.get());
        }
    }

    @Test
    public void testConstructorFailure() {
        try {
            factory.createModule(FailConstructorFileSet.class.getName());
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to instantiate com.puppycrawl.tools.checkstyle."
                    + "PackageObjectFactoryTest$FailConstructorFileSet", ex.getMessage(),
                    "Invalid exception message");
            assertEquals("IllegalAccessException", ex.getCause().getClass()
                    .getSimpleName(), "Invalid exception cause class");
        }
    }

    @Test
    public void testGetShortFromFullModuleNames() {
        final String fullName =
                "com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck";

        assertEquals("DefaultComesLastCheck",
                PackageObjectFactory.getShortFromFullModuleNames(fullName),
                "Invalid simple check name");
    }

    @Test
    public void testGetShortFromFullModuleNamesThirdParty() {
        final String fullName =
                "java.util.stream.Collectors";

        assertEquals(fullName, PackageObjectFactory.getShortFromFullModuleNames(fullName),
                "Invalid simple check name");
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

}
