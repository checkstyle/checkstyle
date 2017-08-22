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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SuppressWarningsHolder.class, SuppressWarningsHolderTest.class })
public class SuppressWarningsHolderTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/misc/suppresswarnings";
    }

    @Test
    public void testGetRequiredTokens() {
        final SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        final int[] expected = {TokenTypes.ANNOTATION};
        assertArrayEquals("Required token array differs from expected",
                expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testOnComplexAnnotations() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testCustomAnnotation() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder5.java"), expected);
    }

    @Test
    public void testGetDefaultAlias() {
        assertEquals("Diffault alias differs from expected",
                "somename", SuppressWarningsHolder.getDefaultAlias("SomeName"));
        assertEquals("Diffault alias differs from expected",
                "somename", SuppressWarningsHolder.getDefaultAlias("SomeNameCheck"));
    }

    @Test
    public void testSetAliasListEmpty() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("");
        assertEquals("Empty alias list should not be set", "",
            SuppressWarningsHolder.getAlias(""));
    }

    @Test
    public void testSetAliasListCorrect() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("alias=value");
        assertEquals("Alias differs from expected",
                "value", SuppressWarningsHolder.getAlias("alias"));
    }

    @Test
    public void testSetAliasListWrong() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();

        try {
            holder.setAliasList("=SomeAlias");
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Error message is unexpected",
                    "'=' expected in alias list item: =SomeAlias", ex.getMessage());
        }

    }

    @Test
    public void testIsSuppressed() throws Exception {
        createHolder("MockEntry", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 10);

        assertFalse("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedByName() throws Exception {
        final SuppressWarningsHolder holder = createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("id", 110, 10);
        holder.setAliasList(MemberNameCheck.class.getName() + "=check");

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedByModuleId() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 350);

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedAfterEventEnd() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 352);

        assertFalse("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedAfterEventStart() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 100);

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedWithAllArgument() throws Exception {
        createHolder("all", 100, 100, 350, 350);

        final Checker source = new Checker();
        final LocalizedMessage firstMessageForTest =
            new LocalizedMessage(100, 10, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent firstEventForTest =
            new AuditEvent(source, "fileName", firstMessageForTest);
        assertFalse("Event is suppressed",
                SuppressWarningsHolder.isSuppressed(firstEventForTest));

        final LocalizedMessage secondMessageForTest =
            new LocalizedMessage(100, 150, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent secondEventForTest =
            new AuditEvent(source, "fileName", secondMessageForTest);
        assertTrue("Event is not suppressed",
                SuppressWarningsHolder.isSuppressed(secondEventForTest));

        final LocalizedMessage thirdMessageForTest =
            new LocalizedMessage(200, 1, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent thirdEventForTest =
            new AuditEvent(source, "fileName", thirdMessageForTest);
        assertTrue("Event is not suppressed",
                SuppressWarningsHolder.isSuppressed(thirdEventForTest));
    }

    @Test
    public void testAnnotationInTry() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = {
            "11: " + getCheckMessage(SuppressWarningsHolder.MSG_KEY),
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder2.java"), expected);
    }

    @Test
    public void testEmptyAnnotation() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder3.java"), expected);
    }

    @Test
    public void testGetAllAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAllAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        final DetailAST lparen = new DetailAST();
        lparen.setType(TokenTypes.LPAREN);

        final DetailAST parent = new DetailAST();
        parent.addChild(lparen);
        parent.addChild(methodDef);

        try {
            getAllAnnotationValues.invoke(holder, parent);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Error type is unexpected",
                    ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Error message is unexpected",
                    "Unexpected AST: Method Def[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testGetAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        try {
            getAllAnnotationValues.invoke(holder, methodDef);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Error type is unexpected",
                    ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Error message is unexpected",
                    "Expression or annotation array"
                    + " initializer AST expected: Method Def[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testGetAnnotationTargetWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAnnotationTarget = holder.getClass()
                .getDeclaredMethod("getAnnotationTarget", DetailAST.class);
        getAnnotationTarget.setAccessible(true);

        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");

        final DetailAST parent = new DetailAST();
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
            assertTrue("Error type is unexpected",
                    ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Error message is unexpected",
                    "Unexpected container AST: Parent ast[0x0]", ex.getCause().getMessage());
        }
    }

    @Test
    public void testAstWithoutChildren() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);

        try {
            holder.visitToken(methodDef);
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Error message is unexpected",
                    "Identifier AST expected, but get null.", ex.getMessage());
        }

    }

    @Test
    public void testAnnotationWithFullName() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder4.java"), expected);
    }

    private static SuppressWarningsHolder createHolder(String checkName, int firstLine,
                                                       int firstColumn, int lastLine,
                                                       int lastColumn) throws Exception {
        final Class<?> entry = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder$Entry");
        final Constructor<?> entryConstr = entry.getDeclaredConstructor(String.class, int.class,
                int.class, int.class, int.class);
        entryConstr.setAccessible(true);

        final Object entryInstance = entryConstr.newInstance(checkName, firstLine,
                firstColumn, lastLine, lastColumn);

        final List<Object> entriesList = new ArrayList<>();
        entriesList.add(entryInstance);

        final ThreadLocal<?> threadLocal = mock(ThreadLocal.class);
        PowerMockito.doReturn(entriesList).when(threadLocal, "get");

        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Field entries = holder.getClass().getDeclaredField("ENTRIES");
        entries.setAccessible(true);
        entries.set(holder, threadLocal);
        return holder;
    }

    private static AuditEvent createAuditEvent(String moduleId, int line, int column) {
        final Checker source = new Checker();
        final LocalizedMessage message = new LocalizedMessage(line, column, null, null, null,
                moduleId, MemberNameCheck.class, "message");
        return new AuditEvent(source, "filename", message);
    }
}
