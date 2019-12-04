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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ClassResolverTest {

    @Test
    public void testResolveInPackage() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), "java.util", imports);
        assertNotNull(classResolver.resolve("List", ""), "Class should be resolved");
        try {
            classResolver.resolve("NoSuchClass", "");
            fail("ClassNotFoundException is expected");
        }
        catch (ClassNotFoundException ex) {
            // exception is expected
            assertEquals("NoSuchClass", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testResolveMatchingExplicitImport() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        imports.add("java.text.ChoiceFormat");
        imports.add("no.such.package.ChoiceFormat");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), null, imports);
        assertNotNull(classResolver.resolve("ChoiceFormat", ""), "Class should be resolved");
    }

    @Test
    public void testResolveByStarImports() throws ClassNotFoundException {
        final Set<String> imports = new HashSet<>();
        imports.add("no.such.package.*");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), null, imports);
        assertNotNull(classResolver.resolve("StringBuffer", ""), "Class should be resolved");
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
            assertEquals("someClass", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testResolveInnerClass() throws Exception {
        final Set<String> imports = new HashSet<>();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "java.util", imports);

        final Class<?> entry = classResolver.resolve("Entry", "Map");
        assertEquals("java.util.Map$Entry", entry.getName(), "Invalid resolve result");
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
            assertEquals("Entry", ex.getMessage(), "Invalid exception message");
        }
    }

}
