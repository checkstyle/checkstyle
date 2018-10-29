////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClassResolver.class, ClassResolverTest.class })
public class ClassResolverTest {

    @Test
    public void testResolveInPackage() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), "java.util", imports);
        assertNotNull("Class should be resolved", classResolver.resolve("List", ""));
        try {
            classResolver.resolve("NoSuchClass", "");
            fail("ClassNotFoundException is expected");
        }
        catch (ClassNotFoundException ex) {
            // exception is expected
            assertEquals("Invalid exception message", "NoSuchClass", ex.getMessage());
        }
    }

    @Test
    public void testResolveMatchingExplicitImport() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        imports.add("java.text.ChoiceFormat");
        imports.add("no.such.package.ChoiceFormat");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), null, imports);
        assertNotNull("Class should be resolved", classResolver.resolve("ChoiceFormat", ""));
    }

    @Test
    public void testResolveByStarImports() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        imports.add("no.such.package.*");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), null, imports);
        assertNotNull("Class should be resolved", classResolver.resolve("StringBuffer", ""));
    }

    @Test
    public void testExistedImportCantBeResolved() {
        final Set<String> imports = new HashSet<>();
        imports.add("java.applet.someClass");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "", imports);

        try {
            classResolver.resolve("someClass", "");
            fail("ClassNotFoundException is expected");
        }
        catch (ClassNotFoundException ex) {
            // exception is expected
            assertEquals("Invalid exception message", "someClass", ex.getMessage());
        }
    }

    @Test
    public void testResolveInnerClass() throws Exception {
        final Set<String> imports = new HashSet<>();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "java.util", imports);

        final Class<?> entry = classResolver.resolve("Entry", "Map");
        assertEquals("Invalid resolve result", "java.util.Map$Entry", entry.getName());
    }

    @Test
    public void testResolveInnerClassWithEmptyPackage() {
        final Set<String> imports = new HashSet<>();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "", imports);

        try {
            classResolver.resolve("Entry", "Map");
            fail("ClassNotFoundException is expected");
        }
        catch (ClassNotFoundException ex) {
            // exception is expected
            assertEquals("Invalid exception message", "Entry", ex.getMessage());
        }
    }

    @Test
    public void testResolveQualifiedNameFails() throws Exception {
        final Set<String> imports = new HashSet<>();
        imports.add("java.applet.someClass");

        final ClassResolver classResolver = PowerMockito.spy(new ClassResolver(Thread
                .currentThread().getContextClassLoader(), "", imports));

        PowerMockito.doThrow(new ClassNotFoundException("expected exception"))
                .when(classResolver, "safeLoad", any());
        PowerMockito.doReturn(true).when(classResolver, "isLoadable", any());

        try {
            classResolver.resolve("someClass", "");
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // exception is expected
            final String expected = "expected exception";
            assertTrue("Invalid exception cause, should be: ClassNotFoundException",
                    ex.getCause() instanceof ClassNotFoundException);
            assertTrue("Invalid exception message, should end with: " + expected,
                    ex.getMessage().endsWith(expected));
        }
    }

    /**
     * This test exists to prevent any possible regression and let of
     * https://github.com/checkstyle/checkstyle/issues/1192 to be persistent
     * event is not very obvious.
     *
     * @throws Exception when smth is not expected
     */
    @Test
    public void testIsLoadableWithNoClassDefFoundError() throws Exception {
        final Set<String> imports = new HashSet<>();
        imports.add("java.applet.someClass");

        final ClassResolver classResolver = PowerMockito.spy(new ClassResolver(Thread
                .currentThread().getContextClassLoader(), "", imports));

        PowerMockito.doThrow(new NoClassDefFoundError("expected exception"))
                .when(classResolver, "safeLoad", any());

        final boolean result = classResolver.isLoadable("someClass");
        assertFalse("result should be false", result);
    }

}
