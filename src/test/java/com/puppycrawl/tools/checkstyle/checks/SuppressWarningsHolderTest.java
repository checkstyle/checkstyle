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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SuppressWarningsHolder.class, SuppressWarningsHolderTest.class })
public class SuppressWarningsHolderTest extends BaseCheckTestSupport {

    @Test
    public void testGetRequiredTokens() {
        SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        int[] expected = {TokenTypes.ANNOTATION};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testOnComplexAnnotations() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testCustomAnnotation() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/InputSuppressWarningsHolder.java").getCanonicalPath(), expected);
    }

    @Test
    public void testGetDefaultAlias() {
        assertEquals("somename", SuppressWarningsHolder.getDefaultAlias("SomeName"));
        assertEquals("somename", SuppressWarningsHolder.getDefaultAlias("SomeNameCheck"));
    }

    @Test
    public void testSetAliasListEmpty() {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("");
    }

    @Test
    public void testSetAliasListCorrect() {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("alias=value");
        assertEquals("value", SuppressWarningsHolder.getAlias("alias"));
    }

    @Test
    public void testSetAliasListWrong() {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();

        try {
            holder.setAliasList("SomeAlias");
            fail("Exception expected");
        }
        catch (ConversionException ex) {
            assertEquals("'=' expected in alias list item: SomeAlias", ex.getMessage());
        }

    }

    @Test
    public void testIsSuppressed() throws Exception {
        Class<?> entry = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder$Entry");
        Constructor<?> entryConstr = entry.getDeclaredConstructor(String.class, int.class,
                int.class, int.class, int.class);
        entryConstr.setAccessible(true);

        Object entryInstance = entryConstr.newInstance("MockEntry", 100, 100, 350, 350);

        List<Object> entriesList = new ArrayList<>();
        entriesList.add(entryInstance);

        ThreadLocal<?> threadLocal = mock(ThreadLocal.class);
        PowerMockito.doReturn(entriesList).when(threadLocal, "get");

        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        Field entries = holder.getClass().getDeclaredField("ENTRIES");
        entries.setAccessible(true);
        entries.set(holder, threadLocal);

        assertFalse(SuppressWarningsHolder.isSuppressed("SourceName", 100, 10));
    }

    @Test
    public void testAnnotationInTry() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        final String[] expected = {
            "11: " + getCheckMessage("suppress.warnings.invalid.target"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder2.java"), expected);
    }

    @Test
    public void testEmptyAnnotation() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder3.java"), expected);
    }

    @Test
    public void testGetAllAnnotationValuesWrongArg() throws ReflectiveOperationException {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAllAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        DetailAST lparen = new DetailAST();
        lparen.setType(TokenTypes.LPAREN);

        DetailAST parent = new DetailAST();
        parent.addChild(lparen);
        parent.addChild(methodDef);

        try {
            getAllAnnotationValues.invoke(holder, parent);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Unexpected AST: Method Def[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testGetAnnotationValuesWrongArg() throws ReflectiveOperationException {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        try {
            getAllAnnotationValues.invoke(holder, methodDef);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Expression or annotation array"
                    + " initializer AST expected: Method Def[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testGetAnnotationTargetWrongArg() throws ReflectiveOperationException {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        Method getAnnotationTarget = holder.getClass()
                .getDeclaredMethod("getAnnotationTarget", DetailAST.class);
        getAnnotationTarget.setAccessible(true);

        DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");

        DetailAST parent = new DetailAST();
        parent.setType(TokenTypes.ASSIGN);
        parent.setText("Parent ast");
        parent.addChild(methodDef);
        parent.setLineNo(0);
        parent.setColumnNo(0);

        try {
            getAnnotationTarget.invoke(holder, methodDef);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Unexpected container AST: Parent ast[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testAstWithouChildren() {
        SuppressWarningsHolder holder = new SuppressWarningsHolder();
        DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);

        try {
            holder.visitToken(methodDef);
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Identifier AST expected, but get null.", ex.getMessage());
        }

    }

    @Test
    public void testAnnotationWithFullName() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder4.java"), expected);
    }
}
