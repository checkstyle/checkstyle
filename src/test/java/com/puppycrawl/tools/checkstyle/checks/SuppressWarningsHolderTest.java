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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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

    @Test
    public void testGet() {
        final SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        final int[] expected = {TokenTypes.ANNOTATION};
        assertArrayEquals("Required token array differs from expected",
                expected, checkObj.getRequiredTokens());
        assertArrayEquals("Required token array differs from expected",
                expected, checkObj.getAcceptableTokens());
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
        assertEquals("Default alias differs from expected",
                "somename", SuppressWarningsHolder.getDefaultAlias("SomeName"));
        assertEquals("Default alias differs from expected",
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

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

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

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsHolder4.java"), expected);
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

        assertTrue("Ast should contain ANNOTATION", annotationDef.isPresent());
        assertTrue("State is not cleared on beginTree",
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, annotationDef.get(),
                "ENTRIES",
                entries -> ((ThreadLocal<List<Object>>) entries).get().isEmpty()));
    }

}
