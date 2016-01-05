////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ScopeUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(ScopeUtils.class);
    }

    @Test
    public void testInEnumOnRoot() {
        final DetailAST ast = new DetailAST();
        Assert.assertFalse(ScopeUtils.isInEnumBlock(ast));
    }

    @Test
    public void testInEnumBlockInNew() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_NEW);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isInEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockWithEnum() {
        final DetailAST ast0 = new DetailAST();
        ast0.setType(TokenTypes.OBJBLOCK);
        final DetailAST ast1 = new DetailAST();
        ast1.setType(TokenTypes.ENUM_DEF);
        ast0.addChild(ast1);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast1.addChild(ast2);

        Assert.assertTrue(ScopeUtils.isInEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInInterface() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.INTERFACE_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isInEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInAnnotation() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ANNOTATION_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isInEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInClass() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.CLASS_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isInEnumBlock(ast2));
    }

    @Test
    public void testIsOuterMostTypeInterface() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.INTERFACE_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsOuterMostTypeAnnotation() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ANNOTATION_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsOuterMostTypeEnum() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ENUM_DEF);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsLocalVariableDefCatch() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_CATCH);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.PARAMETER_DEF);
        ast.addChild(ast2);

        Assert.assertTrue(ScopeUtils.isLocalVariableDef(ast2));
    }

    @Test
    public void testIsLocalVariableDefUnexpected() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_CATCH);

        Assert.assertFalse(ScopeUtils.isLocalVariableDef(ast));
    }
}
