////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_LOADER_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.NULL_PACKAGE_MESSAGE;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.PACKAGE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.STRING_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.internal.CheckUtil;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 *
 * @author Rick Giles
 */
public class PackageObjectFactoryTest {

    private final PackageObjectFactory factory = new PackageObjectFactory(
            BASE_PACKAGE, Thread.currentThread().getContextClassLoader());

    @Test
    public void testCtorNullLoaderException1() {
        try {
            new PackageObjectFactory(new HashSet<>(), null);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_LOADER_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void testCtorNullLoaderException2() {
        try {
            new PackageObjectFactory("test", null);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_LOADER_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void testCtorNullPackageException1() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            new PackageObjectFactory(Collections.singleton(null), classLoader);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_PACKAGE_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void testCtorNullPackageException2() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            new PackageObjectFactory((String) null, classLoader);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals(NULL_PACKAGE_MESSAGE, ex.getMessage());
        }
    }

    @Test
    public void testMakeObjectFromName()
            throws CheckstyleException {
        final Checker checker =
            (Checker) factory.createModule(
                        "com.puppycrawl.tools.checkstyle.Checker");
        assertNotNull(checker);
    }

    @Test
    public void testMakeCheckFromName()
            throws CheckstyleException {
        final ConstantNameCheck check =
                (ConstantNameCheck) factory.createModule(
                        "com.puppycrawl.tools.checkstyle.checks.naming.ConstantName");
        assertNotNull(check);
    }

    @Test
    public void testCreateModuleWithNonExistName() throws CheckstyleException {
        testCreateModuleWithNonExistName("NonExistClassOne");
        testCreateModuleWithNonExistName("NonExistClassTwo");
    }

    private void testCreateModuleWithNonExistName(String name) {
        try {
            factory.createModule(name);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final String attemptedNames = BASE_PACKAGE + PACKAGE_SEPARATOR + name + STRING_SEPARATOR
                    + name + CHECK_SUFFIX + STRING_SEPARATOR
                    + BASE_PACKAGE + PACKAGE_SEPARATOR + name + CHECK_SUFFIX;
            final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, factory.getClass(), null);
            assertEquals(exceptionMessage.getMessage(), ex.getMessage());
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
        assertEquals(fullName, instance1.getClass().getCanonicalName());
        final Object instance2 = objectFactory.createModule(moduleName);
        assertEquals(fullName, instance2.getClass().getCanonicalName());
    }

    @Test
    public void testCreateObjectFromFullModuleNamesWithException() throws Exception {
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
            final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE,
                    new String[] {name, optionalNames}, null, getClass(), null);
            assertEquals(exceptionMessage.getMessage(), ex.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGenerateThirdPartyNameToFullModuleNameWithException() throws Exception {
        final URLClassLoader classLoader = mock(URLClassLoader.class);
        when(classLoader.getURLs()).thenThrow(IOException.class);
        final Method method = factory.getClass().getDeclaredMethod(
                "generateThirdPartyNameToFullModuleName", ClassLoader.class);
        method.setAccessible(true);
        final int size = ((Map<String, String>) method.invoke(factory, classLoader)).size();
        assertEquals(0, size);
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
        assertEquals("test." + className, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNameToFullModuleNameMap() throws Exception {
        final Set<Class<?>> classes = CheckUtil.getCheckstyleModules();
        final Class<PackageObjectFactory> packageObjectFactoryClass = PackageObjectFactory.class;
        final Field field = packageObjectFactoryClass.getDeclaredField("NAME_TO_FULL_MODULE_NAME");
        field.setAccessible(true);
        final Collection<String> canonicalNames = ((Map<String, String>) field.get(null)).values();
        assertFalse(classes.stream()
                .anyMatch(clazz -> !canonicalNames.contains(clazz.getCanonicalName())));
    }

    @Test
    public void testConstructorFailure() {
        try {
            factory.createModule(FailConstructorFileSet.class.getName());
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to instantiate com.puppycrawl.tools.checkstyle."
                    + "PackageObjectFactoryTest$FailConstructorFileSet", ex.getMessage());
            assertEquals("IllegalArgumentException", ex.getCause().getCause().getClass()
                    .getSimpleName());
        }
    }

    private static final class FailConstructorFileSet extends AbstractFileSetCheck {
        private FailConstructorFileSet() {
            throw new IllegalArgumentException("Test");
        }

        @Override
        protected void processFiltered(File file, List<String> lines) {
            // not used
        }
    }
}
