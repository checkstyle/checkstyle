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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.internal.CheckUtil;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 * @author Rick Giles
 */
public class PackageObjectFactoryTest {

    private final PackageObjectFactory factory = new PackageObjectFactory(
        new HashSet<>(), Thread.currentThread().getContextClassLoader());

    @Test
    public void testCtorException() {
        try {
            new PackageObjectFactory(new HashSet<>(), null);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("moduleClassLoader must not be null", ex.getMessage());
        }
    }

    @Test
    public void testCtorException2() {
        try {
            new PackageObjectFactory("test", null);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("moduleClassLoader must not be null", ex.getMessage());
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
    public void testCreateObjectWithIgnoringProblems() throws Exception {
        final Method createObjectWithIgnoringProblemsMethod =
                PackageObjectFactory.class.getDeclaredMethod(
                        "createObjectWithIgnoringProblems", String.class, Set.class);
        createObjectWithIgnoringProblemsMethod.setAccessible(true);
        final Set<String> secondAttempt = new HashSet<>();
        secondAttempt.add("com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck");
        final ConstantNameCheck check = (ConstantNameCheck) createObjectWithIgnoringProblemsMethod
                .invoke(factory, "ConstantName", secondAttempt);
        assertNotNull(check);
    }

    @Test
    public void testJoinPackageNamesWhichContainNullWithClassName() throws Exception {
        final Class<PackageObjectFactory> clazz = PackageObjectFactory.class;
        final Method method =
            clazz.getDeclaredMethod("joinPackageNamesWithClassName", String.class, Set.class);
        method.setAccessible(true);
        final Set<String> packages = Collections.singleton(null);
        final String className = "SomeClass";
        final String actual =
            String.valueOf(method.invoke(PackageObjectFactory.class, className, packages));
        assertEquals(className, actual);
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
            assertEquals("Unable to instatiate com.puppycrawl.tools.checkstyle."
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
