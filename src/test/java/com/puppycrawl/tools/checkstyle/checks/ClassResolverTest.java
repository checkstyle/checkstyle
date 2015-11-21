////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Sets;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClassResolver.class, ClassResolverTest.class })
public class ClassResolverTest {
    @Test
    public void testMisc() throws ClassNotFoundException {
        final Set<String> imports = Sets.newHashSet();
        imports.add("java.io.File");
        imports.add("nothing.will.match.*");
        imports.add("java.applet.*");
        final ClassResolver classResolver =
            new ClassResolver(Thread.currentThread().getContextClassLoader(),
                null, imports);
        assertNotNull(classResolver);
        try {
            classResolver.resolve("who.will.win.the.world.cup", "");
            fail("Should not resolve class");
        }
        catch (ClassNotFoundException ex) {
            // expected
        }
        classResolver.resolve("java.lang.String", "");
        classResolver.resolve("StringBuffer", "");
        classResolver.resolve("AppletContext", "");

        try {
            classResolver.resolve("ChoiceFormat", "");
            fail();
        }
        catch (ClassNotFoundException ex) {
            // expected
        }

        imports.add("java.text.ChoiceFormat");
        final ClassResolver newClassResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), null, imports);
        newClassResolver.resolve("ChoiceFormat", "");

        final ClassResolver javaUtilClassResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(), "java.util", imports);
        javaUtilClassResolver.resolve("List", "");
        try {
            javaUtilClassResolver.resolve("two.nil.england", "");
            fail();
        }
        catch (ClassNotFoundException ex) {
            // expected
        }
    }

    @Test
    public void testExistedImportCantBeResolved() throws Exception {
        final Set<String> imports = Sets.newHashSet();
        imports.add("java.applet.someClass");
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "", imports);

        try {
            classResolver.resolve("someClass", "");
            fail("Exception expected");
        }
        catch (ClassNotFoundException ex) {
            // expected
            assertEquals("someClass", ex.getMessage());
        }
    }

    @Test
    public void testResolveInnerClass() throws Exception {
        final Set<String> imports = Sets.newHashSet();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "java.util", imports);

        final Class<?> entry = classResolver.resolve("Entry", "Map");
        assertEquals("java.util.Map$Entry", entry.getName());
    }

    @Test
    public void testResolveInnerClassWithEmptyPackage() throws Exception {
        final Set<String> imports = Sets.newHashSet();
        final ClassResolver classResolver = new ClassResolver(
                Thread.currentThread().getContextClassLoader(),
                "", imports);

        try {
            classResolver.resolve("Entry", "Map");
        }
        catch (ClassNotFoundException ex) {
            assertEquals("Entry", ex.getMessage());
        }
    }

    @Test
    public void testResolveQualifiedNameFails() throws Exception {
        final Set<String> imports = Sets.newHashSet();
        imports.add("java.applet.someClass");

        final ClassResolver classResolver = PowerMockito.spy(new ClassResolver(Thread
                .currentThread().getContextClassLoader(), "", imports));

        PowerMockito.doThrow(new ClassNotFoundException("expected exception"))
                .when(classResolver, "safeLoad", anyObject());
        PowerMockito.doReturn(true).when(classResolver, "isLoadable", anyObject());

        try {
            classResolver.resolve("someClass", "");
            fail("Exception expected");
        }
        catch (IllegalStateException ex) {
            // expected
            assertTrue(ex.getCause() instanceof ClassNotFoundException);
            assertTrue(ex.getMessage().endsWith("expected exception"));
        }
    }
}
