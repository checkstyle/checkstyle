////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

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

}
