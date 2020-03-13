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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck;
import com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsHolderTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/suppresswarningsholder";
    }

    @AfterEach
    public void cleanUp() {
        // clear cache that may have been set by tests

        new SuppressWarningsHolder().beginTree(null);

        final Map<String, String> map = Whitebox.getInternalState(SuppressWarningsHolder.class,
                "CHECK_ALIAS_MAP");
        map.clear();
    }

    @Test
    public void testGet() {
        final SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        final int[] expected = {TokenTypes.ANNOTATION};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Required token array differs from expected");
        assertArrayEquals(expected, checkObj.getAcceptableTokens(),
                "Required token array differs from expected");
    }

    @Test
    public void testOnComplexAnnotations() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testOnComplexAnnotationsNonConstant() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsHolderNonConstant.java"), expected);
    }

    @Test
    public void testCustomAnnotation() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder5.java"), expected);
    }

    @Test
    public void testAll() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);
        final DefaultConfiguration treeWalker = createModuleConfig(TreeWalker.class);
        final Configuration filter = createModuleConfig(SuppressWarningsFilter.class);
        final DefaultConfiguration violationCheck = createModuleConfig(TypecastParenPadCheck.class);
        violationCheck.addAttribute("option", "space");

        treeWalker.addChild(checkConfig);
        treeWalker.addChild(violationCheck);

        final DefaultConfiguration root = createRootConfig(treeWalker);
        root.addChild(filter);

        final String[] expected = {
            "8:72: "
                    + getCheckMessage(TypecastParenPadCheck.class,
                            AbstractParenPadCheck.MSG_WS_NOT_PRECEDED, ")"),
        };

        verify(root, getPath("InputSuppressWarningsHolder6.java"), expected);
    }

    @Test
    public void testGetDefaultAlias() {
        assertEquals("somename", SuppressWarningsHolder.getDefaultAlias("SomeName"),
                "Default alias differs from expected");
        assertEquals("somename", SuppressWarningsHolder.getDefaultAlias("SomeNameCheck"),
                "Default alias differs from expected");
    }

    @Test
    public void testSetAliasListEmpty() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("");
        assertEquals("", SuppressWarningsHolder.getAlias(""), "Empty alias list should not be set");
    }

    @Test
    public void testSetAliasListCorrect() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("alias=value");
        assertEquals("value", SuppressWarningsHolder.getAlias("alias"),
                "Alias differs from expected");
    }

    @Test
    public void testSetAliasListWrong() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();

        try {
            holder.setAliasList("=SomeAlias");
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("'=' expected in alias list item: =SomeAlias", ex.getMessage(),
                    "Error message is unexpected");
        }
    }

    @Test
    public void testIsSuppressed() throws Exception {
        populateHolder("MockEntry", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 10);

        assertFalse(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedByName() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final AuditEvent event = createAuditEvent("id", 110, 10);
        holder.setAliasList(MemberNameCheck.class.getName() + "=check");

        assertTrue(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedByModuleId() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 350);

        assertTrue(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedAfterEventEnd() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 352);

        assertFalse(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedAfterEventEnd2() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 400, 10);

        assertFalse(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedAfterEventStart() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 100);

        assertTrue(SuppressWarningsHolder.isSuppressed(event), "Event is not suppressed");
    }

    @Test
    public void testIsSuppressedWithAllArgument() throws Exception {
        populateHolder("all", 100, 100, 350, 350);

        final Checker source = new Checker();
        final LocalizedMessage firstMessageForTest =
            new LocalizedMessage(100, 10, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent firstEventForTest =
            new AuditEvent(source, "fileName", firstMessageForTest);
        assertFalse(SuppressWarningsHolder.isSuppressed(firstEventForTest), "Event is suppressed");

        final LocalizedMessage secondMessageForTest =
            new LocalizedMessage(100, 150, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent secondEventForTest =
            new AuditEvent(source, "fileName", secondMessageForTest);
        assertTrue(SuppressWarningsHolder.isSuppressed(secondEventForTest),
                "Event is not suppressed");

        final LocalizedMessage thirdMessageForTest =
            new LocalizedMessage(200, 1, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent thirdEventForTest =
            new AuditEvent(source, "fileName", thirdMessageForTest);
        assertTrue(SuppressWarningsHolder.isSuppressed(thirdEventForTest),
                "Event is not suppressed");
    }

    @Test
    public void testAnnotationInTry() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = {
            "11:14: " + getCheckMessage(SuppressWarningsHolder.MSG_KEY),
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder2.java"), expected);
    }

    @Test
    public void testEmptyAnnotation() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder3.java"), expected);
    }

    @Test
    public void testGetAllAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAllAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        final DetailAstImpl lparen = new DetailAstImpl();
        lparen.setType(TokenTypes.LPAREN);

        final DetailAstImpl parent = new DetailAstImpl();
        parent.addChild(lparen);
        parent.addChild(methodDef);

        try {
            getAllAnnotationValues.invoke(holder, parent);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException,
                    "Error type is unexpected");
            assertEquals("Unexpected AST: Method Def[0x0]", ex.getCause().getMessage(),
                    "Error message is unexpected");
        }
    }

    @Test
    public void testGetAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        try {
            getAllAnnotationValues.invoke(holder, methodDef);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException,
                    "Error type is unexpected");
            assertEquals("Expression or annotation array initializer AST expected: Method Def[0x0]",
                    ex.getCause().getMessage(), "Error message is unexpected");
        }
    }

    @Test
    public void testGetAnnotationTargetWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAnnotationTarget = holder.getClass()
                .getDeclaredMethod("getAnnotationTarget", DetailAST.class);
        getAnnotationTarget.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");

        final DetailAstImpl parent = new DetailAstImpl();
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
            assertTrue(ex.getCause() instanceof IllegalArgumentException,
                    "Error type is unexpected");
            assertEquals("Unexpected container AST: Parent ast[0x0]", ex.getCause().getMessage(),
                    "Error message is unexpected");
        }
    }

    @Test
    public void testAstWithoutChildren() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);

        try {
            holder.visitToken(methodDef);
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Identifier AST expected, but get null.", ex.getMessage(),
                    "Error message is unexpected");
        }
    }

    @Test
    public void testAnnotationWithFullName() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder4.java"), expected);
    }

    @Test
    public void testSuppressWarningsAsAnnotationProperty() throws Exception {
        final Configuration checkConfig = createModuleConfig(SuppressWarningsHolder.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder7.java"), expected);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClearState() throws Exception {
        final SuppressWarningsHolder check = new SuppressWarningsHolder();

        final Optional<DetailAST> annotationDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                    new File(getPath("InputSuppressWarningsHolder.java")),
                    JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.ANNOTATION);

        assertTrue(annotationDef.isPresent(), "Ast should contain ANNOTATION");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, annotationDef.get(),
                "ENTRIES",
                entries -> ((ThreadLocal<List<Object>>) entries).get().isEmpty()),
                "State is not cleared on beginTree");
    }

    private static void populateHolder(String checkName, int firstLine,
                                                         int firstColumn, int lastLine,
                                                         int lastColumn) throws Exception {
        final Class<?> entry = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder$Entry");
        final Constructor<?> entryConstr = entry.getDeclaredConstructor(String.class, int.class,
                int.class, int.class, int.class);
        entryConstr.setAccessible(true);

        final Object entryInstance = entryConstr.newInstance(checkName, firstLine,
                firstColumn, lastLine, lastColumn);

        final ThreadLocal<List<Object>> entries = Whitebox
                .getInternalState(SuppressWarningsHolder.class, "ENTRIES");
        entries.get().add(entryInstance);
    }

    private static AuditEvent createAuditEvent(String moduleId, int line, int column) {
        final Checker source = new Checker();
        final LocalizedMessage message = new LocalizedMessage(line, column, null, null, null,
                moduleId, MemberNameCheck.class, "message");
        return new AuditEvent(source, "filename", message);
    }

}
